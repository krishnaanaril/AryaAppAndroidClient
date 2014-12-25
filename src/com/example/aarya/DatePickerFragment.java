package com.example.aarya;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment 
							implements DatePickerDialog.OnDateSetListener{
		
	public final static String MYPREFF="mySharedPrefernces";
	public static boolean dateFlag;
	public boolean isDueDate;
	
	public DatePickerFragment() {
		
	}
	
	public DatePickerFragment(boolean _isDueDate){
		this.isDueDate=_isDueDate;
	}
	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        dateFlag=false;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog aryaDate=new DatePickerDialog(getActivity(), this, year, month, day);
        aryaDate.getDatePicker().setMinDate(c.getTime().getTime());
        c.add(Calendar.YEAR, 1);
        aryaDate.getDatePicker().setMaxDate(c.getTime().getTime());
        if (isDueDate) {
        	aryaDate.setButton(DialogInterface.BUTTON_POSITIVE, "Done",aryaDate);
		}
        else {
        	aryaDate.setButton(DialogInterface.BUTTON_POSITIVE, "Next",aryaDate);
		}        
        return aryaDate;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    	if (!dateFlag) {
    		dateFlag=true;
    		int mode=Activity.MODE_PRIVATE;
    		SharedPreferences mySharedPreferences= view.getContext().getSharedPreferences(MYPREFF, mode);
    		SharedPreferences.Editor editor= mySharedPreferences.edit();
    		if (isDueDate) {
    			editor.putString("taskDueDate", year+"/"+(month+1)+"/"+day);
			}
    		else {
    			editor.putString("taskDate", year+"/"+(month+1)+"/"+day);
			}
    		
    		editor.commit();
    		if (!isDueDate) {
    			TimePickerFragment newFragment = new TimePickerFragment();		
        	    newFragment.show(getFragmentManager(), "timePicker");
			}    		
		}
    	
    }
        
}
