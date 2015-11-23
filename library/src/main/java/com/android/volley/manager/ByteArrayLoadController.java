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
		String errorMsg = null;
		if (error.getMessage() != null) {
			errorMsg = error.getMessage();
		} else {
			try {
				errorMsg = "Server Response Error ("
						+ error.networkResponse.statusCode + ")";
			} catch (Exception e) {
				errorMsg = "Server Response Error";
			}
		}
		this.mOnLoadListener.onError(errorMsg, getOriginUrl(), this.mAction);
	}

	@Override
	public void onResponse(NetworkResponse response) {
		this.mOnLoadListener.onSuccess(response.data, response.headers,
				getOriginUrl(), this.mAction);
	}
}