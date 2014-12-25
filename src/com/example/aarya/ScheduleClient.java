package com.example.aarya;

import java.util.Calendar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class ScheduleClient {
	// The hook into our service
    private SchedulerService mBoundService;
    // The context to start the service in
    private Context mContext;
    // A flag if we are connected to the service or not
    private boolean mIsBound;
 
    public ScheduleClient(Context context) {
        mContext = context;
    }
     
    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        mContext.bindService(new Intent(mContext, SchedulerService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
    
    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
        	SchedulerService.ServiceBinder binder=(SchedulerService.ServiceBinder) service;
            mBoundService = binder.getService();
            if(mBoundService != null){
                Log.i("service-bind", "Service is bonded successfully!");

                //do whatever you want to do after successful binding
            }
        }
 
        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };
 
    /**
     * Tell our service to set an alarm for the given date
     * @param c a date to set the notification for
     */
    public void setAlarmForNotification(Calendar c, aryaTaskItem taskItem){
        mBoundService.setAlarm(c, taskItem);
    }
     
    /**
     * When you have finished with the service call this method to stop it
     * releasing your connection and resources
     */
    public void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            mContext.unbindService(mConnection);
            mIsBound = false;
        }
    }
}