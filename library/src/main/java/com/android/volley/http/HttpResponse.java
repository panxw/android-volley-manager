package com.android.volley.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpResponse {
	public static final int SC_MOVED_PERMANENTLY = 301;
	public static final int SC_MOVED_TEMPORARILY = 302;
	public static final int SC_NOT_MODIFIED = 304;
	public static final int SC_FORBIDDEN = 403;
	public static final int SC_UNAUTHORIZED = 401;
	public static final int SC_OK = 200;

	private int responseCode;
	private String responseMessage;
	private HttpEntity entityFromConnection;
	private Map<String, String> httpHeaders = new HashMap<String, String>();

	public HttpResponse(int responseCode, String responseMessage) {
		this.setResponseCode(responseCode);
		this.setResponseMessage(responseMessage);
	}

	public void setEntity(HttpEntity entityFromConnection) {
		this.entityFromConnection = entityFromConnection;
	}

	public void addHeader(String key, String value) {
		httpHeaders.put(key, value);
	}

	public Map<String, String>  getAllHeaders() {
		return httpHeaders;
	}

	public HttpEntity getEntity() {
		return entityFromConnection;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public void checkGzip() throws IOException{
		if(httpHeaders != null) {
			String acceptEncoding = httpHeaders.get("Content-Encoding");
			if(acceptEncoding != null && acceptEncoding.contains("gzip")) {
				if(entityFromConnection != null) {
					InputStream is = entityFromConnection.getContent();
					if(is != null) {
						entityFromConnection.setContent(new GZIPInputStream(is));
					}
				}
			}
		}
	}

}
