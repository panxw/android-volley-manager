Android Http RequestManager based on [Volley](https://github.com/mcxiaoke/android-volley).
=====================
##About
Android Http RequestManager based on HttpURLConnection, No HttpClient(apache http) need!  
With JSON, XML, Map, RequestMap(with file) support.  
Require Android 2.3+(API>=9).  

##Usage
####for Maven
	<dependency>
		<groupId>com.panxw.volley</groupId>
		<artifactId>library</artifactId>
		<version>1.0.1</version>
	</dependency>

####for Gradle
	dependencies {
		compile 'com.panxw.volley:library:1.0.1'
	}

##Sample
#####1.init RequestManager in your Application
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

#####2.use RequestManager to load data
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

##Related
######Deprecated HTTP Classes...in Android 5.1: http://dwz.cn/245GXz  

