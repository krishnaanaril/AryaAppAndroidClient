package com.example.aarya;


import java.util.ArrayList;

import com.example.aarya.R.id;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FriendsListActivity extends Activity {

	ListView aFriendsListView;
	ArrayList<ContactsModel> aFriendsItems, aFriendsItemsRefresh;
	ArrayAdapter<String> aArrayAdapter;
	FriendListAdapter friendListAdapter;
	String email2;
	String userNames;
	
	AryaDBAdapter aryaDBAdapter;
	Cursor friendsListCursor;
		
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		email2="";
        setContentView(com.example.aarya.R.layout.activity_friends);
        
//        RelativeLayout aLayout=(RelativeLayout) findViewById(R.id.addFrame);
        aFriendsListView=(ListView) findViewById(com.example.aarya.R.id.myFriendsListView);
        aFriendsListView.setLongClickable(true);
        
        
//        /**
//         * Friends row item click listener
//         */
//        aFriendsListView.setOnItemClickListener(new OnItemClickListener() {
//        	 @Override
//             public void onItemClick(AdapterView<?> parent, View view, int position,
//                     long id) {
//                 Intent intent = getIntent();
//                 SparseBooleanArray sp=aFriendsListView.getCheckedItemPositions();
//                 String str="";                 
//                 for(int i=0;i<sp.size();i++)
//                 {
//                     str+=GENRES[sp.keyAt(i)]+",";
//                 }	
//                 String email2= ((TextView) view.findViewById(R.id.artist)).getText().toString();
////                 Toast.makeText(getApplicationContext(), email2, Toast.LENGTH_SHORT).show();
//                 intent.putExtra("email2", email2);
//         		 setResult(RESULT_OK, getIntent());
//         		 finish();
//             }        
//        });
        
        aFriendsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id)
            {
            	email2+= ((TextView) view.findViewById(com.example.aarya.R.id.artist)).getText().toString()+";";
            	return true;
            }
        });
        
        aFriendsItems=new ArrayList<ContactsModel>();		
//		aArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, aFriendsItems);
		aryaDBAdapter=new AryaDBAdapter(this);
		aryaDBAdapter.open();
		/**
		 * To get emails from local db
		 */
		populateFriendList();
		/**
		 * To get emails from Contacts
		 */
//		getContactInfo(getIntent());
//        final Button button = (Button) findViewById(R.id.add);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                addFriend();
//                populateFriendList();
//            }
//        });
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(com.example.aarya.R.menu.activity_friendslist, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu){
//		if (mDrawerToggle.onOptionsItemSelected(menu)) {
//			return true;
//		}
		switch (menu.getItemId()) {
		case id.action_done:
			DoneChoosingFriends();
			//refreshPage();
			return true;
		case id.action_friendsRefresh:
			refreshFriendsList();
			return true;
//		case id.action_addFriends:
//			addFriends();
//			return true;				
		default:
			return super.onOptionsItemSelected(menu);
		}
	}
	
	private void refreshFriendsList() {
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
//					if (!email.isEmpty() && !aryaDBAdapter.isFriendAdded(email)) {
//						aFriendsItemsRefresh.add(0, contactsModel);
//					}	
					if (!email.isEmpty() && !aFriendsItems.contains(contactsModel)) {
						aryaDBAdapter.insertFriend(contactsModel);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}							
			}
		}
//		for (ContactsModel item : aFriendsItemsRefresh) {
//			if (!aFriendsItems.contains(item)) {
//				aryaDBAdapter.insertFriend(item);
//				Toast.makeText(getApplicationContext(), item.displayName+ " Added", Toast.LENGTH_SHORT).show();
//			}
//			
//		}
	}
	
	private void DoneChoosingFriends(){
		Intent intent = getIntent();
		String users[]=friendListAdapter.getCheckedUsers();
		email2=users[0];
		userNames=users[1];
		intent.putExtra("email2", email2);
		intent.putExtra("userNames", userNames);
		setResult(RESULT_OK, getIntent());
		finish();
	}
	
//	private void addFriends() {
//		try {
//			Intent iViewFriends=new Intent(FriendsListActivity.this, AddFriendsActivity.class	);
//			startActivityForResult(iViewFriends, 1);
//		} catch (Exception e) {
//			e.printStackTrace();			
//		}	
//	}
	
//	private void addFriend() {
//		String email=((EditText) findViewById(R.id.usermail)).getText().toString();
//		aryaDBAdapter.insertFriend(email);
//	}
	
	private void populateFriendList() {
		try {
//			getContactInfo(getIntent());
//			Intent intentContact = new Intent(Intent.ACTION_PICK , ContactsContract.Contacts.CONTENT_URI); 
//			startActivityForResult(intentContact, 2);
			friendsListCursor=aryaDBAdapter.getUsersFriends();
			aFriendsItems.clear();
//			aFriendsListView.setAdapter(aArrayAdapter);
			if (friendsListCursor.moveToFirst()) {
				do {					
					String email=friendsListCursor.getString(2);
					String name=friendsListCursor.getString(1);
					ContactsModel contacts= new ContactsModel(name, email);
					aFriendsItems.add(0, contacts);
				} while (friendsListCursor.moveToNext());
			}
			friendListAdapter= new FriendListAdapter(this, aFriendsItems);
			aFriendsListView.setAdapter(friendListAdapter);
//			aArrayAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		if (resultCode==RESULT_OK) {			
			if (requestCode==1) {
				populateFriendList();
			}
		}
	}//onActivityResult
	
}
	