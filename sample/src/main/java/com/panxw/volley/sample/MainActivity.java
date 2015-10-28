package com.panxw.volley.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.panxw.volley.LoadControler;
import com.panxw.volley.RequestManager;
import com.panxw.volley.RequestManager.RequestListener;
import com.panxw.volley.RequestMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 测试程序
 * 
 * @author panxw
 *
 */
public class MainActivity extends Activity implements RequestListener {

	private static final String OUT_FILE = "upload.txt";

	private static final String OUT_DATA = "sadf464764sdf3ds1f3adsf789213557r12-34912-482130487321gjsaldfalfu2390q3rtheslafkhsdafhreasof";

	private static final String POST_URL = "http://allthelucky.ap01.aws.af.cm/memoServer";

	private static final String POST_JSON = "{\"action\":\"test\", \"info\":\"hello world\"}";

	private static final String GET_URL = "https://raw.githubusercontent.com/panxw/android-volley-manager/master/test.txt";

	private static final String UPLOAD_URL = "http://www.splashpadmobile.com/upload.php";

	private LoadControler mLoadControler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.testPost();
		this.testGet();
		this.testFileUpload();
	}

	private void testPost() {
		mLoadControler = RequestManager.getInstance().post(POST_URL, POST_JSON,
				this, 0);
	}

	private void testGet() {
		mLoadControler = RequestManager.getInstance().get(GET_URL, this, 1);
	}

	private void testFileUpload() {
		MainActivity.prepareFile(this);

		RequestMap params = new RequestMap();
		File uploadFile = new File(this.getFilesDir(), OUT_FILE);
		params.put("uploadedfile", uploadFile);
		params.put("share", "1");

		mLoadControler = RequestManager.getInstance().post(UPLOAD_URL, params,
				this, 2);
	}

	@Override
	public void onSuccess(String response, Map<String, String> headers,
			String url, int actionId) {
		System.out.println("actionId:" + actionId + ", OnSucess!\n" + response);
	}

	@Override
	public void onError(String errorMsg, String url, int actionId) {
		System.out.println("actionId:" + actionId + ", onError!\n" + errorMsg);
	}

	@Override
	public void onRequest() {
		System.out.println("request send...");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mLoadControler != null) {
			mLoadControler.cancel();
		}
	}

	private static void prepareFile(Context context) {
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(OUT_FILE, Context.MODE_PRIVATE);
			try {
				fos.write(OUT_DATA.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
