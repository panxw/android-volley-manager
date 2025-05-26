package com.android.volley.manager;

import java.util.Map;

/**
 * LoadListener special for ByteArrayLoadControler
 * 
 * @author panxw
 * 
 */
public interface LoadListener {
	
	void onStart();

	void onSuccess(byte[] data, Map<String, String> headers, String url, int actionId);

	void onError(String errorMsg, String url, int actionId);
}
