package com.android.volley.manager;

import com.android.volley.NetworkResponse;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

/**
 * ByteArrayLoadController implements Volley Listener & ErrorListener
 * 
 * @author panxw
 * 
 */
public class ByteArrayLoadController extends AbsLoadController implements
		Listener<NetworkResponse>, ErrorListener {

	private LoadListener mOnLoadListener;

	private int mAction = 0;

	public ByteArrayLoadController(LoadListener requestListener, int actionId) {
		this.mOnLoadListener = requestListener;
		this.mAction = actionId;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		String errorMsg = "Server Response Error";
		int errorCode=-1;
		if(error!=null){
			NetworkResponse networkResponse = error.networkResponse;
			if(networkResponse!=null){
				errorCode = networkResponse.statusCode;
			}
			if (error.getMessage() != null) {
				errorMsg = error.getMessage();
			}
		}
		this.mOnLoadListener.onError(errorCode, errorMsg, getOriginUrl(), this.mAction);
	}

	@Override
	public void onResponse(NetworkResponse response) {
		this.mOnLoadListener.onSuccess(response.data, response.headers,
				getOriginUrl(), this.mAction);
	}
}