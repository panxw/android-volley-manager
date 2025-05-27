package com.android.volley.manager.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.manager.sample.R;


import java.lang.ref.WeakReference;

/**
 * ImageLoader使用举例
 * 
 * @author panxw
 *
 */
public class ImageTestActivity extends Activity {

	private static final String URLS[] = new String[] {
			"http://www.baidu.com/img/bdlogo.png",
			"http://www.baidu.com/img/bd_logo1.png" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 建议使用方式
		requestUrl(URLS[0]);
		requestUrl(URLS[1]);

		// 以下方式强烈不建议使用
//		requestUrlb(URLS[0]);
//		requestUrlb(URLS[1]);
	}

	private void setImageView(Bitmap bitmap, String mUrl) {
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(bitmap);
	}

	/**
	 * @param url
	 */
	private void requestUrl(final String url) {
		NetworkApplication.getImageLoader().get(url,
				new MyImageListener(ImageTestActivity.this, url));
	}

	/**
	 * 静态类无法直接持有ImageTestActivity实例,通过WeakReference引用ImageTestActivity实例，避免内存泄露
	 * 
	 * @author panxw
	 *
	 */
	private static class MyImageListener implements ImageListener {
		private String mUrl;
		private WeakReference<ImageTestActivity> mImageTestActivityRef;

		public MyImageListener(ImageTestActivity imageTestActivity, String url) {
			this.mImageTestActivityRef = new WeakReference<ImageTestActivity>(
					imageTestActivity);
			this.mUrl = url;
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			System.out.println("error");
		}

		@Override
		public void onResponse(ImageContainer response, boolean isImmediate) {
			System.out.println("onResponse:" + response.getBitmap());

			ImageTestActivity imageTestActivity = this.mImageTestActivityRef
					.get();
			if (imageTestActivity != null) {// 通过弱引用操作ImageTestActivity实例
				imageTestActivity.setImageView(response.getBitmap(), mUrl);
			}
		}
	}

	/**
	 * @deprecated 内部类会持有ImageTestActivity实例,可能使ImageTestActivity生命周期结束了但无法释放内存
	 */
	private void requestUrlb(final String url) {
		NetworkApplication.getImageLoader().get(url, new ImageListener() {

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				setImageView(response.getBitmap(), url);
			}

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
	}

}
