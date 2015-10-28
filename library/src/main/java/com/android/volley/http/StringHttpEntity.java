package com.android.volley.http;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;

import android.text.TextUtils;

public class StringHttpEntity extends HttpEntity {
	
	private static final char NAME_VALUE_CONNECT = '&';
	private static final String NAME_VALUE_SEPARATOR = "=";

	public StringHttpEntity(final ConcurrentHashMap<String, String> params,
			String charsetName) {
		final String charset = TextUtils.isEmpty(charsetName) ? DEFAULT_CHARSET
				: charsetName;
		final String contentStr = format(params, charset);
		if (contentStr != null) {
			try {
				super.setContent(new ByteArrayInputStream(contentStr
						.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public String format(final ConcurrentHashMap<String, String> params,
			final String charset) {
		final StringBuilder result = new StringBuilder();
		for (ConcurrentHashMap.Entry<String, String> entry : params.entrySet()) {
			final String encodedName = encodeFormFields(entry.getKey(), charset);
			final String encodedValue = encodeFormFields(entry.getValue(),
					charset);
			if (result.length() > 0) {
				result.append(NAME_VALUE_CONNECT);
			}
			result.append(encodedName);
			if (encodedValue != null) {
				result.append(NAME_VALUE_SEPARATOR);
				result.append(encodedValue);
			}
		}
		return result.toString();
	}

	@Override
	public String getContentType() {
		return createContentType(APPLICATION_FORM_URLENCODED, DEFAULT_CHARSET);
	}

	private String encodeFormFields(final String content, final String charset) {
		if (content == null) {
			return null;
		}
		try {
			return URLEncoder.encode(content, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}