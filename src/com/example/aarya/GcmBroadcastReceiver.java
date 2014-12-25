package com.example.aarya;

import static com.example.aarya.CommonUtilities.TAG;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmBroadcastReceiver extends BroadcastReceiver {
	
	Context ctx;
	AryaDBAdapter taskDBAdapter;
	UserDetails userDetails;
	ScheduleClient scheduleClient;
	private AlarmManager am;	
	@Override
	public void onReceive(Context context, Intent intent){
		ctx = context;
		
		userDetails= new UserDetails(context.getApplicationContext());
        userDetails= userDetails.getUserDetails(context.getApplicationContext());
        
        
		
        taskDBAdapter= new AryaDBAdapter(context.getApplicationContext());
        taskDBAdapter.open();
        PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        WakeLock mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        mWakeLock.acquire();
         
        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
             
            String messageType = gcm.getMessageType(intent);
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error", false);
                 
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server", false);
                 
            } else {
                String msg = intent.getStringExtra(DataProvider.COL_MSG);
                if (msg!=null) {
	                String date=intent.getStringExtra(DataProvider.COL_DATE);
	                String dueDate=intent.getStringExtra(DataProvider.COL_DUEDATE);
	                String from=intent.getStringExtra(DataProvider.COL_FROM);
	                String to=intent.getStringExtra(DataProvider.COL_TO);
	                String tlocalId=intent.getStringExtra(DataProvider.COL_LOCALID);
	                long localId=Long.valueOf(tlocalId);
	                String cloudId=intent.getStringExtra(DataProvider.COL_CLOUDID);
	                String fromUser=intent.getStringExtra(DataProvider.COL_FROMUSER);
	                String toUser=intent.getStringExtra(DataProvider.COL_TOUSER);
	                Date taskDate= Calendar.getInstance().getTime();
	                Date taskDueDate=taskDate;
	                SimpleDateFormat oldFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
	                
	                try {
						taskDate=oldFormat.parse(date);
						taskDueDate=oldFormat.parse(dueDate);
					} catch (Exception e) {
						e.printStackTrace();					
					}
	                aryaTaskItem receivedTask= new aryaTaskItem(localId, msg, taskDate,taskDueDate, from, to, cloudId, fromUser, toUser);
                
                	if (from.equals(userDetails.userEmail)) {
    					//Update cloudID
                		taskDBAdapter.updateCloudId(localId, cloudId);
    				}
                    else {
                    	long _id=taskDBAdapter.insertTask(receivedTask);
                    	receivedTask.setLocalId(_id);
                		// Create a new calendar set to the date chosen
                        // we set the time to midnight (i.e. the first minute of that day)
                        Calendar c = Calendar.getInstance();
                        c.setTime(taskDate);
                        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
                        //scheduleClient.setAlarmForNotification(c, receivedTask);
                        setAlarm(context, c, receivedTask);
                        taskDBAdapter.updateIsReminder(_id, 1);
                    	sendNotification("You got a Pulse", true);                    	
    				}
				}
            }
            setResultCode(Activity.RESULT_OK);             
        } 
        catch (Exception e) {
			e.printStackTrace();					
		}finally {
            mWakeLock.release();
        }
	}
	
	private void setAlarm(Context context, Calendar c, aryaTaskItem receivedTask) {
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra(NotifyService.TASK_TITLE, receivedTask.getSubject());
        PendingIntent pendingIntent = PendingIntent.getService(context,1, intent, 0);
        Toast.makeText(context, "Set reminder at: "+ c.getTime().toString(), Toast.LENGTH_SHORT).show(); 
        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Toast.makeText(context, "Alarm set for"+receivedTask.getSubject(), Toast.LENGTH_SHORT).show();
	}
	
	private void sendNotification(String text, boolean launchApp) {
	    NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	     
	    Notification.Builder mBuilder = new Notification.Builder(ctx)
	        .setAutoCancel(true)
	        .setSmallIcon(R.drawable.ic_aryalogo2)
	        .setContentTitle(ctx.getString(R.string.notification_title))
	        .setContentText(text);
	 
	    Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.setSound(uri);
		
//	    if (!TextUtils.isEmpty(Common.getRingtone())) {
//	        mBuilder.setSound(Uri.parse(Common.getRingtone()));
//	    }
	     
//	    if (launchApp) {
	        Intent intent = new Intent(ctx, HomeActivity.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	        mBuilder.setContentIntent(pi);
//	    }
	    mBuilder.addAction(R.drawable.ic_action_done, "Completed", pi);
		mBuilder.addAction(R.drawable.ic_action_alarms, "Later", pi); 
	    mNotificationManager.notify(1, mBuilder.getNotification());
	}

}
