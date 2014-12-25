package com.example.aarya;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetBroadcastReceiver extends BroadcastReceiver {
	static int count=0;
	private static boolean firstConnect=true;
	@Override
	public void onReceive(Context context, Intent intent) {
		if (Common.isOnline(context)) {
			if (firstConnect) {
				Toast.makeText(context, "Net Connected: "+(++count), Toast.LENGTH_SHORT).show();	
				CommonUtilities.SyncTasks(context);
				firstConnect=false;
			}
			
		}
		else {
			firstConnect=true;
			Toast.makeText(context, "Sorry, Connection lost", Toast.LENGTH_SHORT).show();
		}
	}

}
