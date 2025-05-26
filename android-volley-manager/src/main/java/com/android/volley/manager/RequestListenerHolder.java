package com.android.volley.manager;

import android.app.Activity;

import com.android.volley.manager.RequestManager.RequestListener;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * RequestListener Holder to avoid memory leak!
 * 
 * @author panxw
 */
public class RequestListenerHolder implements LoadListener {

	private static final String CHARSET_UTF_8 = "UTF-8";

	private WeakReference<RequestListener> mRequestListenerRef;

	private RequestListener mRequestListener;

	public RequestListenerHolder(RequestListener requestListener) {
		if (requestListener instanceof Activity) {
			this.mRequestListenerRef = new WeakReference<RequestListener>(
					requestListener);
		} else {
			this.mRequestListener = requestListener;
		}
	}

	@Override
	public void onStart() {
		if (mRequestListenerRef != null) {
			RequestListener requestListener = mRequestListenerRef.get();
			if (requestListener != null) {
				requestListener.onRequest();
				return;
			}
		}

		if (this.mRequestListener != null) {
			this.mRequestListener.onRequest();
		}
	}

	@Override
	public void onSuccess(byte[] data, Map<String, String> headers, String url,
			int actionId) {
		String parsed = null;
		try {
			parsed = new String(data, CHARSET_UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (mRequestListenerRef != null) {
			RequestListener requestListener = mRequestListenerRef.get();
			if (requestListener != null) {
				requestListener.onSuccess(parsed, headers, url, actionId);
				return;
			}
		}

		if (this.mRequestListener != null) {
			this.mRequestListener.onSuccess(parsed, headers, url, actionId);
		}
	}

	@Override
	public void onError(String errorMsg, String url, int actionId) {
		if (mRequestListenerRef != null) {
			RequestListener requestListener = mRequestListenerRef.get();
			if (requestListener != null) {
				requestListener.onError(errorMsg, url, actionId);
				return;
			}
		}

		if (this.mRequestListener != null) {
			this.mRequestListener.onError(errorMsg, url, actionId);
		}
	}
}