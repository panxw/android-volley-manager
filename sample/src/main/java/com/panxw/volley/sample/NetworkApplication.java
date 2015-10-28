package com.panxw.volley.sample;

import com.panxw.volley.RequestManager;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

public class NetworkApplication extends Application {

	private static ImageLoader sImageLoader = null;

	private final NetworkImageCache imageCacheMap = new NetworkImageCache();

	public static ImageLoader getImageLoader() {
		return sImageLoader;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		RequestManager.getInstance().init(NetworkApplication.this);
		sImageLoader = new ImageLoader(RequestManager.getInstance()
				.getRequestQueue(), imageCacheMap);
	}

	/**
	 * @description NetworkImageCache
	 */
	public static class NetworkImageCache extends LruCache<String, Bitmap>
			implements ImageCache {

		public NetworkImageCache() {
			this(getDefaultLruCacheSize());
		}

		public NetworkImageCache(int sizeInKiloBytes) {
			super(sizeInKiloBytes);
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight() / 1024;
		}

		@Override
		public Bitmap getBitmap(String url) {
			return get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			put(url, bitmap);
		}

		public static int getDefaultLruCacheSize() {
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
			final int cacheSize = maxMemory / 8;
			return cacheSize;
		}
	}

}
