package com.example.aarya;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
    static final String SERVER_URL = "https://1-dot-aria-21.appspot.com";

    /**
     * Google API project id registered to use GCM.
     */
    static final String SENDER_ID = "290723829978";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMDemo";

    /**
     * Intent used to display a message in the screen.
     */
    static final String DISPLAY_MESSAGE_ACTION =
            "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    static final String EXTRA_MESSAGE = "message";

    
    static Context ctx;
    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
//    	Commented on 1st May, for testing purposes - Krishna Mohan
//        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
//        intent.putExtra(EXTRA_MESSAGE, message);
//        context.sendBroadcast(intent);
    }
    
    public static void SyncTasks(Context context) {
		ctx=context;
    	Cursor taskLists;
		AryaDBAdapter aryaDBAdapter=new AryaDBAdapter(context);
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy  HH:mm");
		aryaDBAdapter.open();
		try {
			taskLists=aryaDBAdapter.getNotSyncedTasks();
			if (taskLists.moveToFirst()) {
				do {
					String cloud=taskLists.getString(5);
					aryaTaskItem pendingTask= new aryaTaskItem(taskLists.getLong(0),
							taskLists.getString(1),      //1st column...
							dateFormat.parse(taskLists.getString(2)),
							dateFormat.parse(taskLists.getString(6)),
							taskLists.getString(3),
							taskLists.getString(4),
							"",
							taskLists.getString(7),
							taskLists.getString(8));
					if (pendingTask.getCloudId().equals("")) {
						send(pendingTask);
					}
					
				} while (taskLists.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
    
	private static void send(final aryaTaskItem taskItem) {
	    new AsyncTask<Void, Void, String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "Done";
//	            String profileEmail="krishnamohan.a.m@gmail.com";
//	            
//	            List<String> a = new ArrayList<String>();
//	            a.add(email2);
//	            
//	            String[] toEmails= new String[a.size()];
//	            a.toArray(toEmails);
	            
					ServerUtilities.send(taskItem);
          
	            
	            return msg;
	        }
	 
	        @Override
	        protected void onPostExecute(String msg) {
	            if (!TextUtils.isEmpty(msg)) {
	                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	            }
	        }
	    }.execute(null, null, null);       
	}
	
}