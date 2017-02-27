package com.edubrite.api.plugins.common;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import com.edubrite.api.plugins.staticdata.ResponseType;

public class ResponseParser {
	public static String parseResponseByType(ResponseType type, String response) {
		if (type != null) {
			try {
				switch (type) {
				case XML:
					return parseXMLResponse(response);
				case JSON:
					return parseJSONResponse(response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * Parses the XML and converts it into equivalent JSON string 
	 * @param response xml response
	 * @return json response
	 * @throws Exception
	 */
	private static String parseJSONResponse(String response) throws Exception {
		if (!StringUtils.isBlankNull(response)) {
			JSONObject xmlJSONObj = XML.toJSONObject(response);
			String jsonPrettyPrintString = xmlJSONObj.toString(4);
			return jsonPrettyPrintString;
		}
		return response;
	}

	/**
	 * Formats xml string to pretty print it 
	 * @param response xml response
	 * @return formatted response
	 * @throws Exception
	 */
	private static String parseXMLResponse(String response) throws Exception {
		if (!StringUtils.isBlankNull(response)) {
			try {
				final InputSource src = new InputSource(new StringReader(response));
				final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src)
						.getDocumentElement();
				final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
				final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
				final LSSerializer writer = impl.createLSSerializer();

				writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
				writer.getDomConfig().setParameter("xml-declaration", false);

				return writer.writeToString(document);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return response;
	}
}
