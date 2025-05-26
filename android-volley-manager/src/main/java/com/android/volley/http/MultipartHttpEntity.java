package com.android.volley.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class MultipartHttpEntity extends HttpEntity {

	private final static char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	private String boundary = null;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	boolean isSetLast = false;
	boolean isSetFirst = false;

	public MultipartHttpEntity() {
		final StringBuffer buf = new StringBuffer();
		final Random rand = new Random();
		for (int i = 0; i < 30; i++) {
			buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
		}
		this.boundary = buf.toString();
	}

	/**
	 * "Content-Type","multipart/form-data; boundary="+boundary
	 */
	public String getContentType() {
		return createContentType(MULTIPART_FORM_DATA, DEFAULT_CHARSET)
				+ "; boundary=" + boundary;
	}

	public void writeFirstBoundaryIfNeeds() {
		if (!isSetFirst) {
			try {
				out.write(("--" + boundary + "\r\n").getBytes());
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		isSetFirst = true;
	}

	public void writeLastBoundaryIfNeeds() {
		if (isSetLast) {
			return;
		}
		try {
			out.write(("\r\n--" + boundary + "--\r\n").getBytes());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		isSetLast = true;
	}

	public void addPart(final String key, final String value) {
		writeFirstBoundaryIfNeeds();
		try {
			out.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n")
					.getBytes());
			out.write(value.getBytes());
			out.write(("\r\n--" + boundary + "\r\n").getBytes());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void addPart(final String key, final String fileName,
			final InputStream fin, final boolean isLast) {
		addPart(key, fileName, fin, "application/octet-stream", isLast);
	}

	public void addPart(final String key, final String fileName,
			final InputStream fin, String type, final boolean isLast) {
		writeFirstBoundaryIfNeeds();
		try {
			type = "Content-Type: " + type + "\r\n";
			out.write(("Content-Disposition: form-data; name=\"" + key
					+ "\"; filename=\"" + fileName + "\"\r\n").getBytes());
			out.write(type.getBytes());
			out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());

			final byte[] tmp = new byte[4096];
			int l = 0;
			while ((l = fin.read(tmp)) != -1) {
				out.write(tmp, 0, l);
			}
			if (!isLast)
				out.write(("\r\n--" + boundary + "\r\n").getBytes());
			else {
				writeLastBoundaryIfNeeds();
			}
			out.flush();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addPart(final String key, final File value, final boolean isLast) {
		try {
			addPart(key, value.getName(), new FileInputStream(value), isLast);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public long getContentLength() {
		writeLastBoundaryIfNeeds();
		return out.toByteArray().length;
	}

	public void writeTo(final OutputStream outstream) throws IOException {
		outstream.write(out.toByteArray());
	}

	public String getContentEncoding() {
		return DEFAULT_CHARSET;
	}

	public InputStream getContent() throws IOException {
		return new ByteArrayInputStream(out.toByteArray());
	}
}