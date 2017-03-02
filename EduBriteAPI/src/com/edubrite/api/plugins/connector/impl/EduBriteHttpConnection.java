package com.edubrite.api.plugins.connector.impl;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.edubrite.api.plugins.common.OltPublicKey;
import com.edubrite.api.plugins.common.OltSymmetricKeyHandler;
import com.edubrite.api.plugins.common.PluginConfig;
import com.edubrite.api.plugins.common.PluginConfigManager;
import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.ApiCall;
import com.edubrite.api.plugins.connector.ApiStatisticsManager;
import com.edubrite.api.plugins.connector.CommunicationError;
import com.edubrite.api.plugins.connector.EduBriteConnection;
import com.edubrite.api.plugins.vo.User;

public class EduBriteHttpConnection implements EduBriteConnection {
	private static final Logger log = Logger.getLogger(EduBriteHttpConnection.class.getName());
	
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String SESSION_ID = "SESSION_ID";
	private static final String SESSION_INFO = "SESSION_INFO";
	private static final String REAL_UNAME = "REAL_UNAME";
	private static final String CLI_PUB_KEY = "CLI_PUB_KEY";
	private static final String SER_PUB_KEY = "SER_PUB_KEY";
	private static final String CLI_SEC_KEY = "CLI_SEC_KEY";
	private static final String SER_SEC_KEY = "SER_SEC_KEY";
	
	private final String host;
	private final int port;

	private final String userName;
	private final String password;
	private AtomicBoolean connected = new AtomicBoolean(false);
	private String sessionId;
	private String sessionInfo;
	private OltPublicKey serverPublicKey;
	private Key serverSecretKey;
	private static ThreadLocal<CommunicationError> error = new ThreadLocal<CommunicationError>();
	//CommunicationError.NO_ERROR;
	private static ThreadLocal<Exception> exception = new ThreadLocal<Exception>();

	private HttpClient client = new HttpClient();
	
	public EduBriteHttpConnection(String url, String userName, String password) {
		URL urlO = null;
		try {
			urlO = new URL(url.trim());
		} catch (Exception e) {
			error.set(CommunicationError.BAD_URL);
		}
		if (urlO != null) {
			host = urlO.getHost();
			port = urlO.getPort();
		} else {
			host = null;
			port = 0;
		}
		if(userName != null){
			this.userName = userName.trim();
		}else{
			this.userName = null;
		}
		if(password != null){
			this.password = password.trim();
		}else{
			this.password = null;
		}
		
		handleProxy();
	}

	private void handleProxy() {
		PluginConfig config = PluginConfigManager.getConfig();
		log.debug("Checking proxy enabled ");
		if(config.isEnableProxy()){
			log.debug("Checking proxy is enabled in config");
			//http.proxyHost, http.proxyPort
			String proxyHost = System.getProperty("http.proxyHost");
			String proxyPortStr = System.getProperty("http.proxyPort");
			//System.out.println("proxyHost = "+proxyHost);
			log.debug("Checking proxy - Host: ["+proxyHost+"], Port:["+proxyPortStr+"]");
			int proxyPort = 80;
			if(!StringUtils.isBlankNull(proxyHost)){
				if(!StringUtils.isBlankNull(proxyPortStr)){
					try{
						proxyPort = Integer.parseInt(proxyPortStr.trim());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				log.debug("Setting proxy - Host: ["+proxyHost+"], Port:["+proxyPortStr+"]");
				//System.out.println("proxyHost set = "+proxyHost);
				client.getHostConfiguration().setProxy(proxyHost.trim(), proxyPort);
				
				String proxyUsername = System.getProperty("http.proxyUser");
				String proxyPassword  = System.getProperty("http.proxyPassword");
				log.debug("Checking proxy - proxyUsername: ["+proxyUsername+"], proxyPassword:["+proxyPassword+"]");
				
				if(!StringUtils.isBlankNull(proxyUsername) && !StringUtils.isBlankNull(proxyPassword)){
					log.debug("Setting proxy - proxyUsername: ["+proxyUsername+"], proxyPassword:["+proxyPassword+"]");
					client.getState().setProxyCredentials(new AuthScope(proxyHost.trim(), proxyPort), 
						new UsernamePasswordCredentials(proxyUsername.trim(), proxyPassword.trim()));
				}
			}else{
				log.debug("Checking proxy enabled - Not enabled");
			}
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getSessionInfo() {
		return sessionInfo;
	}

	private String getPortStr() {
		if (port > 0 && port != 80) {
			return ":" + port;
		} else {
			return "";
		}
	}

	public void disconnect(){
		try{
			sessionId = null;
			sessionInfo = null;
			connected.set(false);
			error.set(CommunicationError.NO_ERROR);
			exception.set(null);
			serverPublicKey = null;
			serverSecretKey = null;
		}catch(Exception e){
			
		}
	}
	
	private String getSelfPublicKey(){
		PluginConfig config = PluginConfigManager.getConfig();
		if(!config.isEnableEncryption()){
			return null;
		}
		if(config.getPublicKey() != null){
			return config.getPublicKey().getEncoded();
		}else{
			throw new RuntimeException("Keys not generated");
		}
	}
	
	
	private Key getSelfSecretKey(){
		PluginConfig config = PluginConfigManager.getConfig();
		if(!config.isEnableEncryption()){
			return null;
		}
		if(config.getSecretKey() != null){
			return config.getSecretKey();
		}else{
			throw new RuntimeException("Keys not generated");
		}
	}
	
	/*
	 * sign using server's public key
	 */
	private String getSelfSecretKeySigned64(){
		try{
			PluginConfig config = PluginConfigManager.getConfig();
			if(!config.isEnableEncryption()){
				return null;
			}
			if(serverPublicKey == null){
				throw new RuntimeException("Public key not sent by server");
			}
			if(config.getSecretKey() != null){
				return OltSymmetricKeyHandler.encryptSecretKeyAsBase64(config.getSecretKey(), serverPublicKey);
			}else{
				throw new RuntimeException("Keys not generated");
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
			error.set(CommunicationError.ENC_DEC_ERROR);
			exception.set(e);
		}
		return null;
	}
	
	public boolean connect() {
		if(connected.get()){
			return true;
		}
		return connectInternal();
	}
	
	public String getEduBriteConnectUrl(String action){
		String url = "http://" + host + getPortStr()
				+ "/oltpublish/site/"+action;
		return url;
	}
	
	private synchronized boolean connectInternal(){
		PluginConfig config = PluginConfigManager.getConfig();
		String url = "http://" + host + getPortStr()
				+ "/oltpublish/site/signin.do";
		
		log.debug("**** Connect :"+url+", "+userName+", "+password);
		HttpMethod method = null;
		HttpMethod method0 = null;
		connected.set(false);
		sessionId = null;
		sessionInfo = null;
		try {
			int code = 0;
			
			url = "http://" + host + getPortStr() + "/oltpublish/site/home.do";
			
			method0 = new GetMethod(url);
			code = client.executeMethod(method0);
			log.debug("2nd code = "+code);
			
			
			method = new GetMethod(url);
			List<NameValuePair> nvpList = new ArrayList<NameValuePair>(3);
			nvpList.add(new NameValuePair(USERNAME, userName));
			nvpList.add(new NameValuePair(PASSWORD, password));
			method.setQueryString(nvpList.toArray(new NameValuePair[nvpList.size()]));
			if(config.isEnableEncryption()){
				/*
				 * Send self public key
				 */
				method.addRequestHeader(CLI_PUB_KEY, getSelfPublicKey());
			}
			code = client.executeMethod(method);
			log.debug("**** Connect 1 :"+code);
			if (code == 200) {
				Cookie[] cookies = client.getState().getCookies();
				if (cookies != null && cookies.length > 0) {
					log.debug("**** Connect 1.1 :"+cookies.length);
					for (Cookie c : cookies) {
						log.debug("**** Connect 1.2 :"+c.getName());
						if (c.getName().equals(SESSION_ID)) {
							sessionId = c.getValue();
						} else if (c.getName().equals(SESSION_INFO)) {
							sessionInfo = c.getValue();
						} 
					}
				}
				/*
				 * read the headers
				 */
				
				if (method.getResponseHeader(SER_PUB_KEY) != null) {
					String encStr = method.getResponseHeader(SER_PUB_KEY).getValue();
					log.debug("**** Connect 1.2 : SER_PUB_KEY = "+encStr);
					if(!StringUtils.isBlankNull(encStr)){
						serverPublicKey = new OltPublicKey(encStr);
						log.debug("**** Connect 1.2 : SER_PUB_KEY parsed");
					}
				}else{
					log.debug("**** Connect 1.2 : SER_PUB_KEY not set ");
				}
				if (method.getResponseHeader(SER_SEC_KEY) != null) {
					String str64 = method.getResponseHeader(SER_SEC_KEY).getValue();
					log.debug("**** Connect 1.2 : SER_SEC_KEY = "+str64);
					if(!StringUtils.isBlankNull(str64)){
						serverSecretKey = OltSymmetricKeyHandler.decryptSecretKeyFromEncryptedBase64(str64, config.getPrivateKey());
						log.debug("**** Connect 1.2 : SER_SEC_KEY parsed");
					}
				}else{
					log.debug("**** Connect 1.2 : SER_SEC_KEY not set ");
				}
				
				log.debug("SessionId = "+sessionId+", sessionInfo="+sessionInfo+", serverSecretKey="+serverSecretKey);
				
				if (StringUtils.isBlankNull(sessionId)
						|| StringUtils.isBlankNull(sessionInfo)) {
					error.set(CommunicationError.UNKNOWN_ERROR);
					exception.set(null);
				} else if(config.isEnableEncryption() && (serverSecretKey == null || serverPublicKey == null)){
					error.set(CommunicationError.UNKNOWN_ERROR);
					exception.set(null);
				} else if(!config.isEnableEncryption() && (serverSecretKey != null || serverPublicKey != null)){
					error.set(CommunicationError.UNKNOWN_ERROR);
					exception.set(null);
				}else{
					error.set(CommunicationError.NO_ERROR);
					exception.set(null);
					connected.set(true);
				}
				
			} else {
				error.set(CommunicationError.BAD_USERNAME_PASSWORD);
				exception.set(null);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			exception.set(e);
			error.set(CommunicationError.BAD_URL);
		}finally{
			if(method != null){
				method.releaseConnection();
			}
			if(method0 != null){
				method0.releaseConnection();
			}
		}
		return connected.get();
	}

	private CommunicationError getError(){
		return error.get() == null ? CommunicationError.NO_ERROR : error.get();
	}
	
	
	private String getEncryptedBase64DataForSending(String plainData){
		try{
			PluginConfig config = PluginConfigManager.getConfig();
			if(!config.isEnableEncryption()){
				return plainData;
			}
			if(config.getSecretKey() == null){
				throw new RuntimeException("Keys not generated");
			}
			
			return OltSymmetricKeyHandler.getEncryptedAndBase64Data(plainData, config.getSecretKey());
		}catch(Exception e){
			log.error(e.getMessage(), e);
			error.set(CommunicationError.ENC_DEC_ERROR);
			exception.set(e);
		}
		return null;
	}
	
	private String getApiUrl(String uri){
		return "http://" + host + getPortStr() + "/oltpublish/site/"
		+ uri;
	}
	
	public String invokeApi(String uri, Map<String, String> parameters) {
		if (!connected.get()) {
			throw new IllegalStateException(getError().name(), exception.get());
		}
		
		long t1 = System.currentTimeMillis();
		PluginConfig config = PluginConfigManager.getConfig();
		ApiCall call = new ApiCall(uri, parameters!= null?parameters.get("dispatch"):null);
		String url = getApiUrl(uri);
		PostMethod method = null;
		String apiResponseStr = null;
		try {
			HttpState initialState = new HttpState();
			Cookie sessionIdCookie = new Cookie(host, SESSION_ID, sessionId, "/", null, false);
			
			Cookie sessionInfoCookie = new Cookie(host, SESSION_INFO, sessionInfo, "/", null, false);
			log.debug("sessionIdCookie = "+sessionId);
			log.debug("sessionInfoCookie = "+sessionInfo);
			
			initialState.addCookie(sessionIdCookie);
			initialState.addCookie(sessionInfoCookie);
			
			client.setState(initialState);
			handleProxy();
			method = new PostMethod(url);
			method.getParams().setContentCharset("utf-8");
			User user = PluginConfigManager.getInstance().getApplicationUser();
			String realName = null;
			if(user != null){
				String uname = user.getName();
				String email = user.getEmail();
				String flname = user.getFullName();
				realName = uname+"|"+(email != null?email:"")+"|"+(flname!=null?flname:"");
			}
			
			if(config.isEnableEncryption()){
				log.debug("Encryption enabled...");
				/*
				 * send self secret key
				 */
				Key selfSecretKey = config.getSecretKey();
				log.debug("Self Secret key got...");
				log.debug("Server public key is null .. "+(serverPublicKey == null));
				
				method.addRequestHeader(CLI_SEC_KEY, 
						OltSymmetricKeyHandler.encryptSecretKeyAsBase64(selfSecretKey, serverPublicKey));
				
				//encrypt username using self secret key
				if(realName != null){
					method.addRequestHeader(REAL_UNAME, getEncryptedBase64DataForSending(realName));
				}
			}else{
				method.addRequestHeader(REAL_UNAME, realName);
			}
			if(parameters == null || parameters.isEmpty()){
				parameters = new HashMap<String, String>();
			}
			
			if (parameters != null && parameters.size() > 0) {
				List<NameValuePair> nvpList = new ArrayList<NameValuePair>(parameters.size());
				for (Map.Entry<String, String> entry : parameters.entrySet()) {
					nvpList.add(new NameValuePair(entry.getKey(), entry.getValue()));
				}
				method.setRequestBody( nvpList.toArray(new NameValuePair[nvpList.size()]) );
			}
			int code = client.executeMethod(method);
			boolean errorFlag = true;
			if (code == 200) {
				/*
				 * if HTTP request was successful and the business action successfully 
				 * completed.
				 */
				errorFlag = false;
			}else{
				/*
				 * To check this condition :
				 * if HTTP request was successful but the business action request failed, 
				 * because in that case, code will not be 200 and the response should 
				 * be xml. 
				 * And if HTTP request failed, then the response will not be api 
				 * response xml.
				 */
				String response = method.getResponseBodyAsString();
				if (response != null) {
					//check if the root node of the response is "response"
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(new ByteArrayInputStream(response.getBytes()));
					doc.getDocumentElement().normalize();
					if ("response".equalsIgnoreCase(doc.getDocumentElement().getNodeName())) {
						errorFlag = false;
					}
				}
			}	
			if(!errorFlag){
				error.set(CommunicationError.NO_ERROR);
				exception.set(null);
				apiResponseStr = method.getResponseBodyAsString();
				log.debug("API Response - "+apiResponseStr);
				if(config.isEnableEncryption() && !StringUtils.isBlankNull(apiResponseStr)){
					//decrypt using server's secret key
					apiResponseStr = OltSymmetricKeyHandler.getDecryptedDataFromBase64AndEncrypted(apiResponseStr, serverSecretKey);
					log.debug("API Decrypted Response - "+apiResponseStr);
				}
			} else {
				error.set(CommunicationError.UNKNOWN_ERROR);
				exception.set(null);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			exception.set(e);
			error.set(CommunicationError.BAD_URL);
		}finally{
			if(method != null){
				try{method.releaseConnection();}catch(Exception e){}
			}
			try{
				ApiStatisticsManager.getInstance().addStats(call, (int)(System.currentTimeMillis()-t1), 
					getError()!=null && getError()!=CommunicationError.NO_ERROR, apiResponseStr,
					error.get(), exception.get());
			}catch(Exception e){}
		}
		return apiResponseStr;
	}

	public boolean isConnected() {
		return connected.get();
	}

	public CommunicationError getLastError() {
		return getError();
	}

	public Exception getLastException() {
		return exception.get();
	}

	public void release() {
		if (client != null && connected.get()) {

		}
	}
	
}