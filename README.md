Android Volley Manager
====================
##About
Android Volley Manager is based on Google's [Android Volley](https://github.com/mcxiaoke/android-volley).  
Powered by HttpURLConnection, no HttpClient need now!(HttpClient is Deperacated in 5.1)  
JSON, XML, Map, RequestMap(with file) request supported.  
Android 2.3+(API>=9) is required.  

#####[ ![Download](https://api.bintray.com/packages/panxw/maven/android-volley-manager/images/download.svg) ](https://bintray.com/panxw/maven/android-volley-manager/_latestVersion)  

##Usage
#####for Maven
	<dependency>
		<groupId>com.panxw.volley</groupId>
		<artifactId>library</artifactId>
		<version>1.0.0</version>
	</dependency>

#####for Gradle
	dependencies {
		compile 'com.panxw.volley:library:1.0.0'
	}

#####latest jars for download
[com.panxw.volley-1.0.0-source.jar](https://github.com/panxw/android-volley-manager/blob/master/release/com.panxw.volley-1.0.0-sources.jar)  [com.panxw.volley-1.0.0-javadoc.jar](https://github.com/panxw/android-volley-manager/raw/master/release/com.panxw.volley-1.0.0-javadoc.jar)  

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
		private static final String OUT_FILE = "upload.txt";
		private static final String OUT_DATA = "sadf464764sdf3ds1f3adsf78921355u2390q3rtheslafkhsdafhreasof";
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
		
		private void testPost() {//test post 
			mLoadControler = RequestManager.getInstance().post(POST_URL, POST_JSON, this, 0);
		}
		
		private void testGet() {//test GET
			mLoadControler = RequestManager.getInstance().get(GET_URL, this, 1);
		}
		
		private void testFileUpload() {//test POST(with file) in RequestMap
			MainActivity.prepareFile(this);
	
			RequestMap params = new RequestMap();
			File uploadFile = new File(this.getFilesDir(), OUT_FILE);
			params.put("uploadedfile", uploadFile);
			params.put("share", "1");
	
			mLoadControler = RequestManager.getInstance().post(UPLOAD_URL, params, this, 2);
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

