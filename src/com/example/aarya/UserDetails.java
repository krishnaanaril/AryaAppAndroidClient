package com.example.aarya;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class UserDetails {
	public String userName;
	public String userEmail;
	public String userPassword;
	public String regid;

	private final Context context;
	static final String TAG = "GCM Demo";
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_USER_NAME = "username";
	public static final String PROPERTY_USER_EMAIL = "useremail";
	public static final String PROPERTY_USER_PASSWORD = "userpassword";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
 // User Settings variable
    public static final String USER_EMAIL="userEmail";

    public UserDetails(Context _context) {
		this.context=_context;
	}
    
	/**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    public void storeUserDetails(Context context, UserDetails userDetails) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_USER_NAME, userDetails.userName);
        editor.putString(PROPERTY_USER_EMAIL, userDetails.userEmail);
        editor.putString(PROPERTY_USER_PASSWORD, userDetails.userPassword);
        editor.putString(PROPERTY_REG_ID, userDetails.regid);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
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
        userDetails.userName=prefs.getString(PROPERTY_USER_NAME, "");
        userDetails.userEmail=prefs.getString(PROPERTY_USER_EMAIL, "");
        userDetails.userPassword=prefs.getString(PROPERTY_USER_PASSWORD, "");
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
            //userDetails=new UserDetails(context);
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
    
}
