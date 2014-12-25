package com.example.aarya;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment{

	private static int taskHour;
	private static int taskMinute;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), (AddTaskActivity)getActivity(), hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	
	
	public String getStringTime(){
		return taskHour+":"+taskMinute;
	}
	
}
