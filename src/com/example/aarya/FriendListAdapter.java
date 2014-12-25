package com.example.aarya;

import java.util.ArrayList;

import android.R;
import android.R.raw;
import android.app.Activity;
import android.content.Context;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.aarya.R.id;

public class FriendListAdapter extends BaseAdapter {

	private Activity activity;
    private ArrayList<ContactsModel> data;
    private static LayoutInflater inflater=null;
    CheckBox[] checkBoxArray;
    LinearLayout[] viewArray;
    private boolean[] checked;

    
    
    public FriendListAdapter(Activity a, ArrayList<ContactsModel> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader=new ImageLoader(activity.getApplicationContext());
        checked= new boolean[d.size()];
        for(int i=0; i<checked.length; i++){
            checked[i]=false;
        }
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if (convertView==null) {
			vi=inflater.inflate(com.example.aarya.R.layout.friendslist_row, null);
		}
		TextView title = (TextView)vi.findViewById(com.example.aarya.R.id.title); // title
        TextView artist = (TextView)vi.findViewById(com.example.aarya.R.id.artist); // artist name
        //TextView duration = (TextView)vi.findViewById(R.id.duration);
        CheckBox cBox = (CheckBox) vi.findViewById(com.example.aarya.R.id.checkbox1);
        cBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
        cBox.setChecked(checked[position]); // set the status as we stored it        
        cBox.setOnCheckedChangeListener(mListener); 
        
        artist.setText(data.get(position).emailId);
        title.setText(data.get(position).displayName);
        
		return vi;
	}
	
	OnCheckedChangeListener mListener = new OnCheckedChangeListener() {
	     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {   
	         checked[(Integer)buttonView.getTag()] = isChecked; // get the tag so we know the row and store the status 
	     }
	};
	
	public String[] getCheckedUsers() {
		String users[]= {"",""};
		for (int i = 0; i < checked.length; i++) {
			if (checked[i]) {
				users[0]+= data.get(i).emailId+";";
				users[1]+=data.get(i).displayName+";";
			}
		}
		return users;
	}

	public ArrayList<ContactsModel> getChecked() {
		ArrayList<ContactsModel> tmp= new ArrayList<ContactsModel>();
		for (int i = 0; i < checked.length; i++) {
			if (checked[i]) {
				ContactsModel contacts= new ContactsModel(data.get(i).displayName, data.get(i).emailId);
				tmp.add(0, contacts);
			}
		}
		return tmp;
	}
	
}
