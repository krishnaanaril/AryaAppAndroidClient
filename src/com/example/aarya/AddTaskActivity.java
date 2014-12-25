	package com.example.aarya;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.id;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddTaskActivity extends FragmentActivity  implements
TimePickerDialog.OnTimeSetListener {
	
	AryaDBAdapter taskDBAdapter;
	private Date taskDate;
	private Date taskDueDate;
	public final static String MYPREFF="mySharedPrefernces";
	String email2;
	String toUserName;
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		
		int mode=Activity.MODE_PRIVATE;
		SharedPreferences mySharedPreferences=getSharedPreferences(MYPREFF, mode);
		
		String txtDate=mySharedPreferences.getString("taskDate", "Oops, not found!");
		String txtDateTime=txtDate+" "+hourOfDay+":"+minute;
		SimpleDateFormat  oldFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		taskDate=Calendar.getInstance().getTime();
		try {
			taskDate= oldFormat.parse(txtDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat  format=new SimpleDateFormat("dd MMM yyyy hh:mm aaa");
		txtDateTime=format.format(taskDate);		
		((TextView)findViewById(R.id.txttime_task)).setText(txtDateTime);
		((TextView)findViewById(R.id.txttime_task)).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_right, R.anim.fadeout);
		setContentView(R.layout.activity_add_task);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
		
		taskDBAdapter=new AryaDBAdapter(this);
		taskDBAdapter.open();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu){
		switch (menu.getItemId()) {
		case R.id.save_task:
			save_task();			
			return true;
		case R.id.cancel_task:
			cancel_task();
			setResult(RESULT_CANCELED);
			finish();
			return true;
		case id.home:
			setResult(RESULT_CANCELED);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(menu);
		}
	}
	
	public void viewFriends(View view) {
		try {
			Intent iViewFriends=new Intent(AddTaskActivity.this, FriendsListActivity.class);
			startActivityForResult(iViewFriends, 1);
		} catch (Exception e) {
			e.printStackTrace();			
		}		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode==RESULT_OK) {			
			if (requestCode==1) {
				email2=data.getStringExtra("email2");
				toUserName=data.getStringExtra("userNames");
//				List<String> emails=Common.StringToList(email2);
//				if (emails.size()>0) {
//					String msg=" - with "+emails.get(0);
//					if (emails.size()>1) {
//						msg+=" and "+(emails.size()-1)+" others";
//					}
//					String currentText=((EditText) findViewById(R.id.txtSubject)).getText().toString();
//					((EditText) findViewById(R.id.txtSubject)).setText(currentText+msg);
//				}
				
			}
			
		}
	}

	public void setDueDate(View view) {
		//custom.showDialog();
		DatePickerFragment newFragment = new DatePickerFragment(true);		
	    newFragment.show(getFragmentManager(), "dueDatePicker");
	}
	
	public void settime_task(View view) {
		
		//custom.showDialog();
		DatePickerFragment newFragment = new DatePickerFragment(false);		
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void cancel_task(){
		Toast.makeText(getApplicationContext(), R.string.dummy,Toast.LENGTH_SHORT).show();
	}
	
	public static ScheduleClient scheduleClient;
	
	public void save_task(){
		String taskTitle=((EditText) findViewById(R.id.txtSubject)).getText().toString();
		taskTitle=taskTitle.split(" - ")[0];
		
		int mode=Activity.MODE_PRIVATE;
		SharedPreferences mySharedPreferences=getSharedPreferences(MYPREFF, mode);		
		String txtDate=mySharedPreferences.getString("taskDate", "Oops, not found!");
		String txtDateTime=mySharedPreferences.getString("taskDueDate", "Oops, not found!");
		SimpleDateFormat  oldFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		taskDueDate=Calendar.getInstance().getTime();
		try {
			taskDueDate= oldFormat.parse(txtDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (taskDueDate==null) {
			taskDueDate=Calendar.getInstance().getTime();
		}
		
		if (taskDate==null) {
			taskDate=Calendar.getInstance().getTime();
		}

		String email1= new UserDetails(getApplicationContext()).getUserDetails(getApplicationContext()).userEmail;
		String fromUserName= new UserDetails(getApplicationContext()).getUserDetails(getApplicationContext()).userName;
		if (email2==null || email2.isEmpty()) {
			email2=email1;
		}
		if (toUserName==null || toUserName.isEmpty()) {
			toUserName=fromUserName;
		}
		aryaTaskItem taskItem=new aryaTaskItem(0, taskTitle, taskDate, taskDueDate, email1, email2,null,fromUserName, toUserName);
		long _id=taskDBAdapter.insertTask(taskItem);
		taskItem.setLocalId(_id);
		// Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        c.setTime(taskDate);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c, taskItem);
        taskDBAdapter.updateIsReminder(_id, 1);
        // Notify the user what they just did
//        Toast.makeText(this, "Notification set for: "+ c.getTime().toString(), Toast.LENGTH_SHORT).show();
		
//		Intent aTaskData=getIntent();
//		String taskTitle=((EditText) findViewById(R.id.txtSubject)).getText().toString();
//		aTaskData.putExtra("taskSubject", taskTitle);
		setResult(RESULT_OK, getIntent());
		send(taskItem);
		finish();
	}
	
	@Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }
	
	private BroadcastReceiver registrationStatusReceiver = new  BroadcastReceiver() {
		 
	    @Override
	    public void onReceive(Context context, Intent intent) {
//	        if (intent != null && Common.ACTION_REGISTER.equals(intent.getAction())) {
//	            switch (intent.getIntExtra(Common.EXTRA_STATUS, 100)) {
//	            case Common.STATUS_SUCCESS:
//	                getActionBar().setSubtitle("online");
//	                break;
//	                 
//	            case Common.STATUS_FAILED:
//	                getActionBar().setSubtitle("offline");                 
//	                break;                 
//	            }
//	        }
	    }
	};
	
	private void send(final aryaTaskItem taskItem) {
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
	                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	            }
	        }
	    }.execute(null, null, null);       
	}
	
}
