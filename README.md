Android Volley Manager
====================
##About
Based on Google's [Android Volley](https://github.com/mcxiaoke/android-volley).  
Powered by HttpURLConnection, no HttpClient need now!(HttpClient is Deprecated in Android 5.1)  
JSON, XML, Map, RequestMap(with file) request supported.  
Android 2.3+(API>=9) is required.  
Tested in Android 5.1, 6.0.  

## Update
Version: __v1.0.2__  
Date: 2016-05-02  
Desc: fix header bug and add gzip support  
__JCenter->__[![Download](https://api.bintray.com/packages/panxw/maven/android-volley-manager/images/download.svg) ](https://bintray.com/panxw/maven/android-volley-manager/_latestVersion)  

##Usage
#####for Maven
	<dependency>
		<groupId>com.panxw.volley</groupId>
		<artifactId>library</artifactId>
		<version>1.0.2</version>
	</dependency>

#####for Gradle
	dependencies {
		compile 'com.panxw.volley:library:1.0.2'
	}

#####latest jars for download
[com.panxw.volley-1.0.2-classes.jar](https://github.com/panxw/android-volley-manager/blob/master/release/com.panxw.volley-1.0.2-classes.jar?raw=true)

##Sample
#####init RequestManager in your Application
	public class VolleyApplication extends Application {
		@Override
		public void onCreate() {
			super.onCreate();
			RequestManager.getInstance().init(this);
		}
	
		@Override
		public void onTerminate() {
			super.onTerminate();
		}
	}

#####use RequestManager to load data
	public class MainActivity extends Activity implements RequestListener {

		private static final String TAG = "VolleyTest";

		private static final String GET_URL = "http://www.panxw.com/about.html";

		private static final String POST_URL = "http://www.panxw.com/index.php";

		private static final String POST_JSON = "{\"action\":\"test\", \"info\":\"hello world\"}";

		private static final String OUT_FILE = "upload.txt";

		private static final String OUT_DATA = "df464764sdf3ds1f3adsf789213557r12-34912-482130487321";

		private static final String UPLOAD_URL = "http://www.splashpadmobile.com/upload.php";

		private LoadController mLoadController = null;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			this.testGet();
			this.testPost();
			this.testFileUpload();
		}

		private void testGet() {
			mLoadController = RequestManager.getInstance().get(GET_URL, this, 1);
		}

		private void testPost() {
			mLoadController = RequestManager.getInstance().post(POST_URL, POST_JSON,
					this, 0);
		}

		private void testFileUpload() {
			MainActivity.prepareFile(this);

			RequestMap params = new RequestMap();
			File uploadFile = new File(this.getFilesDir(), OUT_FILE);
			params.put("uploadedfile", uploadFile);
			params.put("share", "1");

			mLoadController = RequestManager.getInstance().post(UPLOAD_URL, params,
					this, 2);
		}

		@Override
		public void onSuccess(String response, Map<String, String> headers,
				String url, int actionId) {
			Log.d(TAG, "actionId:" + actionId + ", OnSucess!\n" + response);
		}

		@Override
		public void onError(String errorMsg, String url, int actionId) {
			Log.d(TAG, "actionId:" + actionId + ", onError!\n" + errorMsg);
		}

		@Override
		public void onRequest() {
			Log.d(TAG, "request send...");
		}

		@Override
		public void onBackPressed() {
			super.onBackPressed();
			if (mLoadController != null) {
				mLoadController.cancel();
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

####Author
Blog: http://www.panxw.com  
Linkedin: http://www.linkedin.com/in/panxw  
QQ: 85902258  

## License

    Copyright (C) 2011 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

