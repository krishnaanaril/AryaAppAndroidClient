package com.example.aarya;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TasksAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<AryaTaskModel> data;
	private static LayoutInflater inflater=null;
	
	public TasksAdapter(Activity a, ArrayList<AryaTaskModel> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
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
			vi=inflater.inflate(R.layout.list_row, null);
		}
		TextView eventCreator = (TextView)vi.findViewById(R.id.eventCreator); // eventCreator
        TextView eventInfo = (TextView)vi.findViewById(R.id.eventInfo); // eventInfo
        TextView duration = (TextView)vi.findViewById(R.id.duration);
        TextView isSync = (TextView)vi.findViewById(R.id.isSync);
        
        eventCreator.setText(data.get(position).taskFrom);
        eventInfo.setText(data.get(position).taskTitle);
        duration.setText(data.get(position).taskDate);
        CharSequence tmp=data.get(position).taskCloudID;
        isSync.setText("Not Synced");
        if (data.get(position).taskCloudID!=null && !data.get(position).taskCloudID.isEmpty()) {
			isSync.setText("Synced");
		}
        
		return vi;
	}

}
