package com.example.aarya;

import java.util.Calendar;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DrawerItemClickListener implements ListView.OnItemClickListener{

	@Override
	public void onItemClick(AdapterView parent, View view, int postion, long id) {
		// TODO Auto-generated method stub
		
		selectItem(postion, view.getContext());
	}

	private void selectItem(int position, Context c){
		switch (position) {
		case 0:	
			Toast.makeText(c, "Calendar "+ c.getString(R.string.comingsoon), Toast.LENGTH_SHORT).show();
//			Calendar today = Calendar.getInstance();
//
//            Uri uriCalendar = Uri.parse("content://com.android.calendar/time/" + String.valueOf(today.getTimeInMillis()));
//            Intent intentCalendar = new Intent(Intent.ACTION_VIEW,uriCalendar);
//
//            //Use the native calendar app to view the date
//            c.startActivity(intentCalendar);
			Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
			builder.appendPath("time");
			ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
			Intent intent = new Intent(Intent.ACTION_VIEW)
			    .setData(builder.build());
			c.startActivity(intent);
			break;			
		case 1:	
//			Toast.makeText(c, "Friends "+c.getString(R.string.comingsoon), Toast.LENGTH_SHORT).show();
			Intent iViewFriends=new Intent(c, FriendsListActivity.class);
			c.startActivity(iViewFriends);
			break;
		case 2:	
			Toast.makeText(c, "Completed "+c.getString(R.string.comingsoon), Toast.LENGTH_SHORT).show();
			break;
		case 3:	
			Toast.makeText(c, "Procrastinated "+c.getString(R.string.comingsoon), Toast.LENGTH_SHORT).show();
			break;
		case 4:	
			Toast.makeText(c, "Settings "+c.getString(R.string.comingsoon), Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
	}
		
	
}
