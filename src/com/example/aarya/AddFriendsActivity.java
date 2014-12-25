package com.example.aarya;

import java.util.ArrayList;

import com.example.aarya.R.id;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AddFriendsActivity extends Activity {

	ListView aFriendsListView;
	ArrayList<ContactsModel> aFriendsItems;
	ArrayAdapter<String> aArrayAdapter;
	FriendListAdapter friendListAdapter;
	AryaDBAdapter aryaDBAdapter; 
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(com.example.aarya.R.layout.activity_addfriends);
        
//        RelativeLayout aLayout=(RelativeLayout) findViewById(R.id.addFrame);
        aFriendsListView=(ListView) findViewById(com.example.aarya.R.id.myAddFriendsListView);
        aFriendsListView.setLongClickable(true);
        aFriendsItems=new ArrayList<ContactsModel>();
        aryaDBAdapter= new AryaDBAdapter(this);
        aryaDBAdapter.open();
		getContactInfo(getIntent());
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(com.example.aarya.R.menu.activity_addfriendslist, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu){
		switch (menu.getItemId()) {
		case id.action_add:
			ArrayList<ContactsModel> contacts=friendListAdapter.getChecked();
			for (ContactsModel item : contacts) {
				aryaDBAdapter.insertFriend(item);
			}
			setResult(RESULT_OK, getIntent());
			finish();
			return true;			
		default:
			return super.onOptionsItemSelected(menu);
		}
	}
	
	protected void getContactInfo(Intent intent)
	{
		aFriendsItems.clear();
		aFriendsListView.setAdapter(aArrayAdapter);
		Cursor cursor = getContentResolver().query( 
		        ContactsContract.Contacts.CONTENT_URI, 
		        null,
		        null, 
		        null, 
		        ContactsContract.Contacts.DISPLAY_NAME+" COLLATE NOCASE DESC"); 
		if (cursor.getCount()>0) {
			while (cursor.moveToNext()) {
				String contact_id = cursor.getString(cursor.getColumnIndex( ContactsContract.Contacts._ID ));
				String name = cursor.getString(cursor.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME ));
				String email="";
				Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,    
						null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+ " = ?", new String[] { contact_id }, null);
				while (emailCursor.moveToNext()) {
                    email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));                       
		        }
				emailCursor.close();
				ContactsModel contactsModel= new ContactsModel(name, email);
				try {
					if (!email.isEmpty() && !aryaDBAdapter.isFriendAdded(email)) {
						aFriendsItems.add(0, contactsModel);
					}	
				} catch (Exception e) {
					// TODO: handle exception
				}							
			}
		}
	    friendListAdapter= new FriendListAdapter(this, aFriendsItems);
	    aFriendsListView.setAdapter(friendListAdapter);
	}//getContactInfo
}


