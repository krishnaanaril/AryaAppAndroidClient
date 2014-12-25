package com.example.aarya;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Patterns;

public class Common extends Application {
	
	public static String[] email_arr;
	private static SharedPreferences prefs;
	
	public static String getPreferredEmail() {
	    return prefs.getString("chat_email_id", email_arr.length==0 ? "" : email_arr[0]);
	}
	 
	public static String getDisplayName() {
	    String email = getPreferredEmail();
	    return prefs.getString("display_name", email.substring(0, email.indexOf('@')));
	}
	 
	public static boolean isNotify() {
	    return prefs.getBoolean("notifications_new_message", true);
	}
	 
	public static String getRingtone() {
	    return prefs.getString("notifications_new_message_ringtone", android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.toString());
	}
	 
	public static String getServerUrl() {
	    return prefs.getString("server_url_pref", Constants.SERVER_URL);
	}
	 
	public static String getSenderId() {
	    return prefs.getString("sender_id_pref", Constants.SENDER_ID);
	}   
	
	@Override
	public void onCreate(){
		super.onCreate();
		prefs=PreferenceManager.getDefaultSharedPreferences(this);
		
		List<String> emailList=getEmailList();
	}
	
//	public static String getPreferredEmail() {
//		return email_arr[0];
//	}
	
	private List<String> getEmailList() {
		List<String> lst=new ArrayList<String>();
		Account[] accounts=AccountManager.get(this).getAccounts();
		for(Account account:accounts){
			if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
				lst.add(account.name);
			}
		}
		return lst;
	}

/**
 * To Check whether internet is enabled or not
 * @return
 */
public static boolean isOnline(Context c) {
    ConnectivityManager cm =
        (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    if (netInfo != null && netInfo.isConnected()) {
        return true;
    }
    return false;
}

/**
 * Utility function to convert given List to a string appended with semi colon
 * @param toList
 * @return
 */
public static String ListToString(List<String> toList) {
	String toString="";
	for (String email : toList) {
		toString.concat(email+";");
	}
	return toString;
}

/**
 * Utility function to convert given String to a List
 * @param toString
 * @return
 */
public static List<String> StringToList(String toString) {
	List<String> toList= new ArrayList<String>();
	toList= Arrays.asList(toString.split(";"));
	return toList;
}

}
