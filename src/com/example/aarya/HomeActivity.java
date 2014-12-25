package com.example.aarya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aarya.R.id;
import com.example.aarya.noteendpoint.Noteendpoint;
import com.example.aarya.noteendpoint.model.Note;
import com.example.aarya.tasksendpoint.Tasksendpoint;
import com.example.aarya.tasksendpoint.model.Tasks;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class HomeActivity extends Activity {
	
	ListView aTaskListView;
	ArrayList<String> aTaskItems;
	ArrayList<AryaTaskModel> aTasks;
	TasksAdapter tasksAdapter;
	ArrayAdapter<String> aArrayAdapter;

	AryaDBAdapter aryaDBAdapter;
	UserDetails userDetails;
	Cursor taskListCursor;
	
	static final private int REMOVE_TODO=Menu.FIRST+1;
	
	// Navigation Drawer Variables
		private String[] mSideBarTitles;
		private DrawerLayout mDrawerLayout;
		private ListView mDrawerList;
		private ActionBarDrawerToggle mDrawerToggle;
		private CharSequence mTitle;
		private CharSequence mDrawerTitle;
	
	//GCM Varibles
		public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	 // User Settings variable
	    public static final String USER_EMAIL="userEmail";
	   /*
	    * Substitute you own sender ID here. This is the project number you got
	     * from the API Console, as described in "Getting Started."
	     */
	    String SENDER_ID = "290723829978";

	    /**
	     * Tag used on log messages.
	     */
	    static final String TAG = "GCM Demo";
	    TextView mDisplay;
	    GoogleCloudMessaging gcm;
	    AtomicInteger msgId = new AtomicInteger();
	    Context context;

	    String regid;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
//        View decorView=this.getWindow().getDecorView();
//		int uiOptions=View.SYSTEM_UI_FLAG_LOW_PROFILE;
//		decorView.setSystemUiVisibility(uiOptions);
        
        mSideBarTitles=getResources().getStringArray(R.array.arya_sidebar);
		mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList=(ListView) findViewById(R.id.left_drawer);
	
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mSideBarTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//		TextView mDrawerText=(TextView) findViewById(R.id.side_username);
//		mDrawerText.setText("Krishna");
		mTitle=mDrawerTitle=getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_previous_item,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        FrameLayout aLayout=(FrameLayout) findViewById(R.id.content_frame);
        aTaskListView=(ListView)aLayout.findViewById(R.id.myTaskListView);
        
        userDetails= new UserDetails(this);
        userDetails= userDetails.getUserDetails(this);
       
        if (userDetails.userEmail.isEmpty()) {
        	ShowLogIn();
		}
        aTaskItems=new ArrayList<String>();
        aTasks=new ArrayList<AryaTaskModel>();
		//aArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, aTaskItems);
		
        registerForContextMenu(aTaskListView);

//		Code to display the saved profile pic
		
//		ImageView iv=(ImageView) findViewById(R.id.profilepic);
//        File f= new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/AaryaApp/ProfilePic.jpg");
//        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
//        iv.setImageBitmap(bMap);
//        
//        ((TextView)findViewById(R.id.profileName)).setText("Krishna Mohan");
		
		aryaDBAdapter=new AryaDBAdapter(this);
		aryaDBAdapter.open();
		populateTaskList();
		String values[]={"First Try","krishnamohan.a.m@gmail.com"};
		
//		// Check device for Play Services APK. If check succeeds, proceed with GCM registration.
//        if (checkPlayServices()) {
//            gcm = GoogleCloudMessaging.getInstance(this);
//            regid = getRegistrationId(getApplicationContext());
//
//            if (regid.isEmpty()) {
//                registerInBackground();
//            }
//        } else {
//            Log.i(TAG, "No valid Google Play Services APK found.");
//        }
        
//		new ReminderTasksEndpoints().execute(values);
//		new EndpointsTask().execute(values);
    }

	private void populateTaskList(){
		try {
			taskListCursor=aryaDBAdapter.getDayToDoItem();
		} catch (SQLException e) {
			// TODO: handle exception
		}		
		startManagingCursor(taskListCursor); 
		updateArray();
	}
	
	private void updateArray(){
		try {
			taskListCursor=aryaDBAdapter.getAllToDoItemsCursor();
		} catch (Exception e) {
			// TODO: handle exception
		}	
//		aTaskItems.clear();
		aTasks.clear();
		
		//aTaskListView.setAdapter(aArrayAdapter);
		if (taskListCursor.moveToFirst()) {
			do {
//				Toast.makeText(getApplicationContext(), taskListCursor.getString(aryaDBAdapter.TASK_COLUMN), Toast.LENGTH_SHORT).show();
				
				AryaTaskModel temp=new AryaTaskModel(taskListCursor.getLong(0), //0th Column 
						taskListCursor.getString(1),      //1st column...
						taskListCursor.getString(2),
						taskListCursor.getString(3),
						taskListCursor.getString(4),
						taskListCursor.getString(5),
						taskListCursor.getString(6));
				aTasks.add(0,temp);
//				aTaskItems.add(0,taskListCursor.getString(aryaDBAdapter.TASK_ID_COLUMN)+"-"+ +"-"+);
			} while (taskListCursor.moveToNext());
		}
		tasksAdapter= new TasksAdapter(this, aTasks);
		aTaskListView.setAdapter(tasksAdapter);
		//aArrayAdapter.notifyDataSetChanged();
	}

    
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu){
		if (mDrawerToggle.onOptionsItemSelected(menu)) {
			return true;
		}
		switch (menu.getItemId()) {
		case id.action_refresh:
			refreshPage();
			return true;
		case id.action_add:
			addNewTask();
			return true;				
		default:
			return super.onOptionsItemSelected(menu);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,
									View v,
									ContextMenu.ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);		
		menu.setHeaderTitle(R.string.context_title);
		menu.add(0,REMOVE_TODO,Menu.NONE, R.string.context_remove);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		super.onContextItemSelected(item);
		switch (item.getItemId()) {
		case (REMOVE_TODO):
		{
			AdapterView.AdapterContextMenuInfo menuInfo;
			menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
			ListView listView = (ListView)findViewById(R.id.myTaskListView);

            // In my example i've used a string. Do not use copy pasta. yeah ? 
            AryaTaskModel aryaTaskItem = (AryaTaskModel) listView.getAdapter().getItem(menuInfo.position);
            long _id= aryaTaskItem.taskLocalId;
//			int index=menuInfo.position;
			aryaDBAdapter.removeTask(_id);
			updateArray();
			return true;
		}
		default:
			break;
		}
		return false;
	}
		
	public void refreshPage(){
//		Toast.makeText(getApplicationContext(), string.dummy, Toast.LENGTH_SHORT).show();
//		Intent iLogin=new Intent(this, SignInActivity.class);
//		startActivity(iLogin);
		//      alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+TEN_SECONDS, alarmIntent);
		//		updateArray();  // Commented to test Alarm
		
		//new TrialClass().execute(new String[]{"3"});
		
		CommonUtilities.SyncTasks(getApplicationContext());
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					URL url=new URL(CommonUtilities.SERVER_URL+"/sync");
//					HttpURLConnection connection= (HttpURLConnection)url.openConnection();	
//					String qString="id=2";
//					byte[] bytes= qString.getBytes();
//					connection.setDoOutput(true);
//					connection.setFixedLengthStreamingMode(bytes.length);
////					OutputStreamWriter out= new OutputStreamWriter(connection.getOutputStream());
////					out.write(qString);
////					out.close();
//					
//					// post the request
//		            OutputStream out = connection.getOutputStream();
//		            out.write(bytes);
//		            out.close();
//					
//					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//					String returnString="";		
//					int doubledValue =0;
//					 
//                    while ((returnString = in.readLine()) != null)
//                    {
//                        doubledValue= Integer.parseInt(returnString);
//                    }
//                    in.close();
//                    Toast.makeText(getApplicationContext(), "Success: "+doubledValue, Toast.LENGTH_SHORT).show();	
//					
//				} catch (Exception e) {
//					Log.d("Exception: 	", e.getMessage());
//				}
//				
//			}
//		}).start();
	}
	
	public class TrialClass extends AsyncTask<String, Integer, Long>{
		protected Long doInBackground(String... num) {
			try {
				URL url=new URL(CommonUtilities.SERVER_URL+"/sync");
				HttpURLConnection connection= (HttpURLConnection)url.openConnection();	
				String qString="id="+num[0];
				byte[] bytes= qString.getBytes();
				connection.setDoOutput(true);
				connection.setFixedLengthStreamingMode(bytes.length);
//				OutputStreamWriter out= new OutputStreamWriter(connection.getOutputStream());
//				out.write(qString);
//				out.close();
				
				// post the request
	            OutputStream out = connection.getOutputStream();
	            out.write(bytes);
	            out.close();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String returnString="";		
//				int doubledValue =0;
				JSONObject jsonObj;
				String tmp=""; 
				
	            while ((returnString = in.readLine()) != null)
	            {
//	                doubledValue= Integer.parseInt(returnString);
	            	jsonObj= new JSONObject(returnString);
	            	tmp=jsonObj.getString("key1");
	            }
	            in.close();
	            Toast.makeText(getApplicationContext(), "Success: "+tmp, Toast.LENGTH_SHORT).show();	
				
			} catch (Exception e) {
				Log.d("Exception: 	", e.getMessage());
			}
			return (long) 0;
		}
	}
	
	public void addNewTask(){
		Intent iaddTask = new Intent(this, AddTaskActivity.class);
		startActivityForResult(iaddTask, 1);
	}
	
	public void ShowLogIn() {
		Intent iLogin=new Intent(this, LogInActivity.class);
		startActivity(iLogin);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode==RESULT_OK) {			
			if (requestCode==1) {
				String taskTitle=data.getStringExtra("taskTitle");
				updateArray();
			}
			
		}
	}
	
	public class ReminderTasksEndpoints extends AsyncTask<String, Integer, Long>{
		protected Long doInBackground(String... values) {
			
			Tasksendpoint.Builder endpointBuilder=new Tasksendpoint.Builder(AndroidHttp.newCompatibleTransport(),
					new JacksonFactory(),
					new HttpRequestInitializer() {
						
						@Override
						public void initialize(HttpRequest arg0) throws IOException {
							// TODO Auto-generated method stub
							
						}
					});
			Tasksendpoint endpoint=CloudEndpointUtils.updateBuilder(endpointBuilder).build();
			try {
				Tasks tasks=new Tasks().setTaskDescription(values[0]);
				
				tasks.setKey(null);		
				//tasks.setId(new Date().toString());
				tasks.setTaskDate(values[1]);
				Tasks result=endpoint.insertTasks(tasks).execute();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return (long) 0;
		}
	}
	
	public class EndpointsTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... values) {
        	
          Noteendpoint.Builder endpointBuilder = new Noteendpoint.Builder(
              AndroidHttp.newCompatibleTransport(),
              new JacksonFactory(),
              new HttpRequestInitializer() {
              public void initialize(HttpRequest httpRequest) { }
              });
      Noteendpoint endpoint = CloudEndpointUtils.updateBuilder(
      endpointBuilder).build();
      try {
          Note note = new Note().setDescription(values[0]);
          String noteID = new Date().toString();
          note.setId(noteID);

          note.setEmailAddress(values[1]);      
          Note result = endpoint.insertNote(note).execute();
//          Toast.makeText(getApplicationContext(), "In endpoint", Toast.LENGTH_SHORT).show();
      } catch (IOException e) {
        e.printStackTrace();
      }
          return (long) 0;
        }
    }
	
    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public UserDetails getUserDetails(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        UserDetails userDetails= new UserDetails(context);
        userDetails.userName=prefs.getString(UserDetails.PROPERTY_USER_NAME, "");
        userDetails.userEmail=prefs.getString(UserDetails.PROPERTY_USER_EMAIL, "");
        userDetails.userPassword=prefs.getString(UserDetails.PROPERTY_USER_PASSWORD, "");
        userDetails.regid = prefs.getString(PROPERTY_REG_ID, "");
        
        if (userDetails.regid.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            //return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            userDetails=new UserDetails(context);
            //return "";
        }
        return userDetails;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences( LogInActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


//	private boolean checkPlayServices() {
//		int resultCode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//		if (resultCode!=ConnectionResult.SUCCESS) {
//			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//                finish();
//            }
//            return false;
//		}
//		return true;
//	}
//
//	/**
//     * Stores the registration ID and the app versionCode in the application's
//     * {@code SharedPreferences}.
//     *
//     * @param context application's context.
//     * @param regId registration ID
//     */
//    private void storeRegistrationId(Context context, String regId) {
//        final SharedPreferences prefs = getGcmPreferences(context);
//        int appVersion = getAppVersion(context);
//        Log.i(TAG, "Saving regId on app version " + appVersion);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(PROPERTY_REG_ID, regId);
//        editor.putInt(PROPERTY_APP_VERSION, appVersion);
//        editor.commit();
//    }
//
//    /**
//     * Gets the current registration ID for application on GCM service, if there is one.
//     * <p>
//     * If result is empty, the app needs to register.
//     *
//     * @return registration ID, or empty string if there is no existing
//     *         registration ID.
//     */
//    private String getRegistrationId(Context context) {
//        final SharedPreferences prefs = getGcmPreferences(context);
//        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
//        if (registrationId.isEmpty()) {
//            Log.i(TAG, "Registration not found.");
//            return "";
//        }
//        // Check if app was updated; if so, it must clear the registration ID
//        // since the existing regID is not guaranteed to work with the new
//        // app version.
//        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
//        int currentVersion = getAppVersion(context);
//        if (registeredVersion != currentVersion) {
//            Log.i(TAG, "App version changed.");
//            return "";
//        }
//        return registrationId;
//    }
//
//    /**
//     * Registers the application with GCM servers asynchronously.
//     * <p>
//     * Stores the registration ID and the app versionCode in the application's
//     * shared preferences.
//     */
//    private void registerInBackground() {
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//                try {
//                    if (gcm == null) {
//                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
//                    }
//                    regid = gcm.register(SENDER_ID);
//                    msg = "Device registered, registration ID=" + regid;
//
//                    // You should send the registration ID to your server over HTTP, so it
//                    // can use GCM/HTTP or CCS to send messages to your app.
//                    ServerUtilities.register(getApplicationContext(), regid);
//
//                    // For this demo: we don't need to send it because the device will send
//                    // upstream messages to a server that echo back the message using the
//                    // 'from' address in the message.
//
//                    // Persist the regID - no need to register again.
//                    storeRegistrationId(getApplicationContext(), regid);
//                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
//                    // If there is an error, don't just keep trying to register.
//                    // Require the user to click a button again, or perform
//                    // exponential back-off.
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                //mDisplay.append(msg + "\n");
//            }
//        }.execute(null, null, null);
//    }
//    
//    /**
//     * @return Application's version code from the {@code PackageManager}.
//     */
//    private static int getAppVersion(Context context) {
//        try {
//            PackageInfo packageInfo = context.getPackageManager()
//                    .getPackageInfo(context.getPackageName(), 0);
//            return packageInfo.versionCode;
//        } catch (NameNotFoundException e) {
//            // should never happen
//            throw new RuntimeException("Could not get package name: " + e);
//        }
//    }
//
//    /**
//     * @return Application's {@code SharedPreferences}.
//     */
//    private SharedPreferences getGcmPreferences(Context context) {
//        // This sample app persists the registration ID in shared preferences, but
//        // how you store the regID in your app is up to you.
//        return getSharedPreferences( HomeActivity.class.getSimpleName(),
//                Context.MODE_PRIVATE);
//    }
//    
//    /**
//     * @return User's {@code SharedPreferences}.
//     */
//    public SharedPreferences getUserPreferences(Context context) {
//        // This sample app persists the registration ID in shared preferences, but
//        // how you store the regID in your app is up to you.
//        return getSharedPreferences( "UserSettings",
//                Context.MODE_PRIVATE);
//    }
//    
//    private String getUserEmail(Context context) {
//        final SharedPreferences prefs = getUserPreferences(context);
//        String email = prefs.getString(USER_EMAIL, "");
//        if (email.isEmpty()) {
//            Log.i(TAG, "Email not found.");
//            return "";
//        }        
//        return email;
//    }
}
