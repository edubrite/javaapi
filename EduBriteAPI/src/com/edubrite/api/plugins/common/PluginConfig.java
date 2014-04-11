package com.edubrite.api.plugins.common;

import java.io.Serializable;
import java.security.Key;
import java.util.Properties;

@SuppressWarnings("serial")
public class PluginConfig implements Serializable{
	private String url ;//= "http://site1.edubrite.com:8080";
	private String userName;// = "user2";
	private String password;// = "password";
	
	private String privateKeyStr;
	private String publicKeyStr;
	private String secretKey64;
	private boolean enableEncryption;
	
	private boolean enableProxy;
	
	private transient OltPrivateKey privateKey;
	private transient OltPublicKey publicKey;
	private transient Key secretKey;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		if(url != null){
			this.url = url.trim();
		}else{
			this.url = null;
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		if(userName != null){
			this.userName = userName.trim();
		}else{
			this.userName = null;
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(password != null){
			this.password = password;
		}else{
			this.password = null;
		}
	}
	public boolean isEnableProxy() {
		return enableProxy;
	}
	public void setEnableProxy(boolean enableProxy) {
		this.enableProxy = enableProxy;
	}
	public PluginConfig(String url, String userName, String password) {
		super();
		this.url = url;
		this.userName = userName;
		this.password = password;
	}
	public PluginConfig() {
		super();
	}
	
	public String toString(){
		return url+","+userName+","+password;
	}
	public OltPublicKey getPublicKey() {
		if(publicKey == null && !StringUtils.isBlankNull(publicKeyStr)){
			publicKey = new OltPublicKey(publicKeyStr);
		}
		return publicKey;
	}
	public OltPrivateKey getPrivateKey() {
		if(privateKey == null && !StringUtils.isBlankNull(privateKeyStr)){
			privateKey = new OltPrivateKey(privateKeyStr);
		}
		return privateKey;
	}
	
	public void setKeys(Pair<OltPublicKey, OltPrivateKey> keys){
		if(keys == null){
			publicKey = null;
			privateKey = null;
			privateKeyStr = null;
			publicKeyStr = null;
		}else{
			publicKey = keys.getFirst();
			privateKey = keys.getSecond();
			privateKeyStr = privateKey.getEncoded();
			publicKeyStr = publicKey.getEncoded();
		}
	}
	
	public String getSecretKey64() {
		return secretKey64;
	}
	public void setSecretKey64(String secretKey64) {
		this.secretKey64 = secretKey64;
		if(StringUtils.isBlankNull(secretKey64)){
			this.secretKey = null;
		}
	}
	
	public boolean isEnableEncryption() {
		return enableEncryption;
	}
	public void setEnableEncryption(boolean enableEncryption) {
		this.enableEncryption = enableEncryption;
	}
	public boolean isEncryptionKeysGenerated(){
		return (getSecretKey() != null && getPublicKey() != null && getPrivateKey() != null);
	}
	
	public Key getSecretKey(){
		if(secretKey != null){
			return secretKey;
		}
		if(!StringUtils.isBlankNull(secretKey64)){
			secretKey = OltSymmetricKeyHandler.parseSecretKeyFromBase64String(secretKey64);
		}
		return secretKey;
	}
	
	public PluginConfig(Properties props){
		this.url = props.getProperty("url").trim();
		this.userName = props.getProperty("userName").trim();
		this.password = props.getProperty("password").trim();
	}
}
