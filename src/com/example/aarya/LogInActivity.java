package com.example.aarya;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class LogInActivity extends Activity {
	
		/**
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
	    AryaDBAdapter aryaDBAdapter;
	    static  boolean  contactsFlag=false;
	    
	    //User Variables
	    UserDetails userDetails;
	    
	    //GCM Varibles
		public static final String EXTRA_MESSAGE = "message";
		public static final String PROPERTY_USER_NAME = "username";
		public static final String PROPERTY_USER_EMAIL = "useremail";
		public static final String PROPERTY_USER_PASSWORD = "userpassword";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	 // User Settings variable
	    public static final String USER_EMAIL="userEmail";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        userDetails= new UserDetails(this);
        aryaDBAdapter=new AryaDBAdapter(this);
		aryaDBAdapter.open();
		getFriendsFromContatcs();
		
        final Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	String tmp=((EditText) findViewById(R.id.username)).getText().toString();
            	userDetails.userName=((EditText) findViewById(R.id.username)).getText().toString();
            	userDetails.userEmail=((EditText) findViewById(R.id.email)).getText().toString();
            	userDetails.userPassword=((EditText) findViewById(R.id.password)).getText().toString();
            	// Check device for Play Services APK. If check succeeds, proceed with GCM registration.
                if (checkPlayServices()) {
                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    UserDetails tmpUserDetails =userDetails.getUserDetails(getApplicationContext());

                    if (tmpUserDetails.regid.isEmpty()) {
                        registerInBackground();
                    }
                } else {
                    Log.i(TAG, "No valid Google Play Services APK found.");
                }
                
                finish();
            }
        });

	}

	
	
	private boolean checkPlayServices() {
		int resultCode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode!=ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
		}
		return true;
	}

	/**
	 * 
	 */
	private void getFriendsFromContatcs() {
		if (!contactsFlag) {
			Cursor cursor = getContentResolver().query( 
			        ContactsContract.Contacts.CONTENT_URI, 
			        null,
			        null, 
			        null, 
			        ContactsContract.Contacts.DISPLAY_NAME+" COLLATE NOCASE DESC"); 
			if (cursor.getCount()>0) {
				while (cursor.moveToNext()) {
					String contact_id = cursor.getString(cursor.getColumnIndex( ContactsContract.Contacts._ID ));
					String name = cursor.getString(cursor.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME ));
					String email="";
					Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,    
							null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+ " = ?", new String[] { contact_id }, null);
					while (emailCursor.moveToNext()) {
	                    email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));                       
			        }
					emailCursor.close();
					ContactsModel contactsModel= new ContactsModel(name, email);
					try {
						if (!email.isEmpty()) {
							aryaDBAdapter.insertFriend(contactsModel);
						}	
					} catch (Exception e) {
						// TODO: handle exception
					}							
				}
			}
			contactsFlag=true;
		}		
	}
	
    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    //userDetails= new UserDetails(getApplicationContext());
                    userDetails.regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + userDetails.regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    ServerUtilities.register(getApplicationContext(), userDetails);

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    userDetails.storeUserDetails(getApplicationContext(), userDetails);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //mDisplay.append(msg + "\n");
            	
            }
        }.execute(null, null, null);
    }
}
