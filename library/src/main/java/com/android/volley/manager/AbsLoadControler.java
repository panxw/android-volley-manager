package com.android.volley.manager;

import com.android.volley.Request;

/**
 * Abstract LoaderControler that implements LoadControler
 * 
 * @author steven pan
 * 
 */
public  class AbsLoadControler implements LoadControler {

	protected Request<?> mRequest;

	public void bindRequest(Request<?> request) {
		this.mRequest = request;
	}

	@Override
	public void cancel() {
		if (this.mRequest != null) {
			this.mRequest.cancel();
		}
	}

	protected String getOriginUrl() {
		return this.mRequest.getOriginUrl();
	}
}