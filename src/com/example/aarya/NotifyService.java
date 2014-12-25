package com.example.aarya;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotifyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class ServiceBinder extends Binder{
		NotifyService getService(){
			return NotifyService.this;
		}
	}
	
	// Unique id to identify the notification.
    private static final int NOTIFICATION = 123;
    // Name of an intent extra we can use to identify if this service was started to create a notification 
    public static final String INTENT_NOTIFY = "com.aryainc.arya_b.service.INTENT_NOTIFY";
    
    public static final String TASK_TITLE="TASK_TITLE";
    
    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
         
        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false)){
        	String taskSubject=intent.getStringExtra(TASK_TITLE);
    		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.ON_AFTER_RELEASE	, "bbbb");
    		wl.acquire();
        	showNotification(taskSubject);
        	wl.release();
        }
         
        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }
    
    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();
    
    // Notification Variables
 	NotificationManager mNotificationManager;
 	Notification note;
    
    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification(String taskSubject) {
        // This is the 'title' of the notification
        CharSequence title = "AryaApp";
        // This is the icon to use on the notification
        int icon = R.drawable.ic_action_mail_add;
        // This is the scrolling text of the notification
        CharSequence text = taskSubject;      
        // What time to show on the notification
        long time = System.currentTimeMillis();
         
        NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setWhen(time)
		        .setSmallIcon(R.drawable.ic_aryalogo2)
		        .setContentTitle(title)
		        .setContentText(text)
		        .setAutoCancel(true);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, HomeActivity.class);
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(HomeActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		
		mBuilder.setContentIntent(resultPendingIntent);
		mBuilder.addAction(R.drawable.ic_action_done, "Completed", resultPendingIntent);
		mBuilder.addAction(R.drawable.ic_action_alarms, "Later", resultPendingIntent);
		Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.setSound(uri);
//		mNotificationManager =
//		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		note=mBuilder.build();

		mNotificationManager.notify(NOTIFICATION, note); 
//		wl.release();
        // Stop the service when we are finished
        stopSelf();
    } 
	
}
