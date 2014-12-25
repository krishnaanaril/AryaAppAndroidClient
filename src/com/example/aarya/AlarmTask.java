package com.example.aarya;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmTask implements Runnable {
	// The date selected for the alarm
    private final Calendar date;
    // The android system alarm manager
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;
 
    public AlarmTask(Context context, Calendar date) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
    }
    
    @Override
    public void run(){
    	
    }
    
	public void run(aryaTaskItem taskItem) {
		// Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra(NotifyService.TASK_TITLE, taskItem.getSubject());
        PendingIntent pendingIntent = PendingIntent.getService(context, (int)taskItem.getLocalId(), intent, 0);
        Toast.makeText(this.context, "Set reminder at: "+ date.getTime().toString(), Toast.LENGTH_SHORT).show(); 
        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
	}

}
