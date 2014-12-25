package com.example.aarya;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.PersonBuffer;

public class SignInActivity extends Activity implements
ConnectionCallbacks, OnConnectionFailedListener,  ResultCallback<People.LoadPeopleResult>{
	
	/* Request code used to invoke sign in user interactions. */
	  private static final int RC_SIGN_IN = 0;

	  /* Client used to interact with Google APIs. */
	  private GoogleApiClient mGoogleApiClient;

	  /* A flag indicating that a PendingIntent is in progress and prevents
	   * us from starting further intents.
	   */
	  private boolean mIntentInProgress;

	  /* Track whether the sign-in button has been clicked so that we know to resolve
	   * all issues preventing sign-in without waiting.
	   */
	  private boolean mSignInClicked;

	  /* Store the connection result from onConnectionFailed callbacks so that we can
	   * resolve them when the user clicks sign-in.
	   */
	  private ConnectionResult mConnectionResult;

	  /* A helper method to resolve the current ConnectionResult error. */
	  private void resolveSignInError() {
	    if (mConnectionResult.hasResolution()) {
	      try {
	        mIntentInProgress = true;
	        mConnectionResult.startResolutionForResult(this, // your activity
                    RC_SIGN_IN);
	      } catch (SendIntentException e) {
	        // The intent was canceled before it was sent.  Return to the default
	        // state and attempt to connect to get an updated ConnectionResult.
	        mIntentInProgress = false;
	        mGoogleApiClient.connect();
	      }
	    }
	  }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		int error=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API, null)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();

		com.google.android.gms.common.SignInButton loginButton=(com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);	
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				  if (!mGoogleApiClient.isConnecting()) {
				    mSignInClicked = true;
				    resolveSignInError();
				  }
				}
		});		
		// Commented 'cause it is not found in the sample code
		
	}
		
	protected void onStart() {
	    super.onStart();
	    mGoogleApiClient.connect();
	  }

	  protected void onStop() {
	    super.onStop();

	    if (mGoogleApiClient.isConnected()) {
	      mGoogleApiClient.disconnect();
	    }
	  }
	  
	  public void onConnectionFailed(ConnectionResult result) {
		  Toast.makeText(this, "User is not connected!", Toast.LENGTH_LONG).show();
//		  int errorCode = GooglePlusUtil.checkGooglePlusApp(this);
//		  if (errorCode != GooglePlusUtil.SUCCESS) {
//		    GooglePlusUtil.getErrorDialog(errorCode, this, 0).show();
//		  }
		  
		  Boolean temp=result.hasResolution();
		  if (!mIntentInProgress && result.hasResolution()) {
		    try {
		      mIntentInProgress = true;
		      result.startResolutionForResult(this, // your activity
                      RC_SIGN_IN);
		    } catch (SendIntentException e) {
		      // The intent was canceled before it was sent.  Return to the default
		      // state and attempt to connect to get an updated ConnectionResult.
		      mIntentInProgress = false;
		      mGoogleApiClient.connect();
		    }
		  }
		  if (!mIntentInProgress) {
			    // Store the ConnectionResult so that we can use it later when the user clicks
			    // 'sign-in'.
			    mConnectionResult = result;

			    if (mSignInClicked) {
			      // The user has already clicked 'sign-in' so we attempt to resolve all
			      // errors until the user is signed in, or they cancel.
			      resolveSignInError();
			    }
			  }

		}

	  @Override
	  public void onResult(LoadPeopleResult peopleData) {
	    if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
	      PersonBuffer personBuffer = peopleData.getPersonBuffer();
	      try {
	        int count = personBuffer.getCount();
	        for (int i = 0; i < count; i++) {
	          Log.d("Name: ", "Display name: " + personBuffer.get(i).getDisplayName());
	          Toast.makeText(getApplicationContext(), personBuffer.get(i).getDisplayName(),Toast.LENGTH_SHORT).show();
	        }
	      } finally {
	        personBuffer.close();
	      }
	    } else {
	      Log.e("Error", "Error requesting visible circles: " + peopleData.getStatus());
	    }
	  }
	  
	  private void storeRegistrationId(Context context, String email) {
//	        final SharedPreferences prefs = new HomeActivity().getUserPreferences(context);
//	                
//	        SharedPreferences.Editor editor = prefs.edit();
//	        editor.putString("userEmail", email);
//	        
//	        editor.commit();
	    }

	  
		public void onConnected(Bundle connectionHint) {
		  // We've resolved any connection errors.  mGoogleApiClient can be used to
		  // access Google APIs on behalf of the user.
			mSignInClicked = false;
//			  Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
			  if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient)!=null) {
				Person currentPerson=Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				String email= Plus.AccountApi.getAccountName(mGoogleApiClient);
				
				Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
				
				
				String personName=currentPerson.getDisplayName();
				Image image =  currentPerson.getImage();
				
				File file= new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/AaryaApp/");
//				file.mkdirs();
				if (!file.isDirectory()) {
					file.mkdirs();
				}
				try {
					if(file.createNewFile()){
					    file.createNewFile();
					  }  
//				if (file.isDirectory()) {
					String profilePic=file.getPath();
					
						String paths[]={image.getUrl(), profilePic};
						new GetUserInfo().execute(paths);
					} catch (Exception e) {
						// TODO: handle exception
						Log.w("Profile Pic", "Error in Pic Saving");
						Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
					}					
//				}
				Toast.makeText(this, "User "+personName+" in "+image.toString()+" is connected!", Toast.LENGTH_LONG).show();
			}
			  finish();
		}
		
		public class GetUserInfo extends AsyncTask<String, Integer, Long>{
			
			protected Long doInBackground(String... paths) {
				try {
					URL url = new URL(paths[0]);
					InputStream is = url.openStream();
					OutputStream os = new FileOutputStream(paths[1]+"/ProfilePic.jpg");

					byte[] b = new byte[2048];
					int length;

					while ((length = is.read(b)) != -1) {
						os.write(b, 0, length);
					}

					is.close();
					os.close();
				} catch (Exception e) {
					// TODO: handle exception
					Log.w("Profile Pic", "Error in Pic Saving");
				}				
				return (long) 0;
			}
		}
		
		
		protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
//			super.onActivityResult(requestCode, responseCode, intent) ; 
			
			if (requestCode == RC_SIGN_IN) {
				  if (responseCode != RESULT_OK) {
				      mSignInClicked = false;
				    }

			    mIntentInProgress = false;

			    if (!mGoogleApiClient.isConnecting()) {
			      mGoogleApiClient.connect();
			    }
			  }
			}

		public void onConnectionSuspended(int cause) {
			  mGoogleApiClient.connect();
			}		
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

}