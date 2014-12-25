package com.example.aarya;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AryaDBAdapter {
	
	private static final String DATABASE_NAME="arya.db";
	private static final String DATABASE_TABLE="aryaTasks";
	private static final String FRIENDSLIST_TABLE="aryaFriends";
	private static final int DATABASE_VERSION=2;
	
	private SQLiteDatabase db;
	private final Context context;
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	 * Variables for aryaTasks table
	 */
	public static final String KEY_ID = "_id";
	public static final int TASK_ID_COLUMN = 0;
	public static final String KEY_TASK = "taskSubject";
	public static final int TASK_COLUMN = 1;
	public static final String KEY_TASK_DATE = "taskDate";
	public static final int TASK_DATE_COLUMN = 2;
	public static final String KEY_TASK_TO="taskTo";
	public static final int TASK_TO_COLUMN=3;
	public static final String KEY_TASK_FROM="taskFrom";
	public static final int TASK_FROM_COLUMN=4;
	public static final String KEY_CLOUDID="taskCloudId";
	public static final int TASK_CLOUDID=5;
	public static final String KEY_ISREMINDER="isReminder";
	public static final int TASK_ISREMINDER_COLUMN=6;
	public static final String KEY_DUEDATE="taskDueDate";
	public static final int TASK_DUEDATECOLUMN=7;
	public static final String KEY_FROMUSER="fromUser";
	public static final int TASK_FROMUSER=8;
	public static final String KEY_TOUSER="toUser";
	public static final int TASK_TOUSER=9;
//	public static final String KEY_ISSYNC="isSync";
//	public static final int TASK_ISSYNC=7;

	/**
	 * Variables for aryaFriends table
	 */
	public static final String KEY_FRIENDSID = "_id";
	public static final int FRIENDS_ID_COLUMN = 0;
	public static final String KEY_MAIL = "friendsMail";
	public static final int MAIL_COLUMN = 1;
	public static final String KEY_FRIENDNAME = "friendsName";
	public static final int NAME_COLUMN = 2;
	
	private AryaDBOpenHelper dbHelper;
	
	public AryaDBAdapter(Context _context){
		this.context=_context;
		dbHelper=new AryaDBOpenHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void close(){
		db.close();
	}
	
	public void open() throws SQLiteException{
		try {
			db=dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			// TODO: handle exception
			db=dbHelper.getReadableDatabase();
		}
	}
	
	
	public long insertFriend(ContactsModel contact) {
		//creating new row
		ContentValues newTaskValues=new ContentValues();
		// adding values tp row
		newTaskValues.put(KEY_FRIENDNAME, contact.displayName);
		newTaskValues.put(KEY_MAIL, contact.emailId);
		
		// Insert the row
		try {
			return db.insert(FRIENDSLIST_TABLE,null,newTaskValues);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 1;
	}
	
	// Remove task
	public boolean removeFriend(long _rowIndex){
		return db.delete(FRIENDSLIST_TABLE, KEY_FRIENDSID+"="+_rowIndex, null)>0;
	}
	
	public Cursor getUsersFriends()  throws SQLException{
				
		try {
			return db.query(FRIENDSLIST_TABLE,
					new String[] { KEY_FRIENDSID, KEY_FRIENDNAME, KEY_MAIL},
					null,null,null,null,KEY_FRIENDNAME+" COLLATE NOCASE DESC");
		} catch (Exception e) {
			// TODO: handle exception
			throw new SQLException("No friends, We are sorry...:(");
		}
		
	}
	
	public boolean isFriendAdded(String _email) throws SQLException{
		try {
			Cursor cursor=db.query(FRIENDSLIST_TABLE,
					new String[] { KEY_FRIENDSID, KEY_FRIENDNAME, KEY_MAIL}, KEY_MAIL+"= ?",
					new String[]{_email},null,null,null);
			if (cursor.getCount()==0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("No friends, We are sorry...:(");
		}
	}
	
	/**
	 * 
	 * @param _task
	 * @return long
	 */
	public long insertTask(aryaTaskItem _task){
		//creating new row
		ContentValues newTaskValues=new ContentValues();
		// adding values to row
		newTaskValues.put(KEY_TASK, _task.getSubject());
		newTaskValues.put(KEY_TASK_DATE, _task.getDate());
		newTaskValues.put(KEY_DUEDATE, _task.getDueDate());
		newTaskValues.put(KEY_TASK_FROM, _task.getFrom());
		newTaskValues.put(KEY_TASK_TO, _task.getTo());
		newTaskValues.put(KEY_CLOUDID, _task.getCloudId());
		newTaskValues.put(KEY_ISREMINDER, 0);
		newTaskValues.put(KEY_FROMUSER, _task.getFromUser());
		newTaskValues.put(KEY_TOUSER, _task.getToUser());
//		newTaskValues.put(KEY_ISSYNC, 0);
		// Insert the row
		return db.insert(DATABASE_TABLE,null,newTaskValues);
	}
	
	// Remove task
	public boolean removeTask(long _rowIndex){
		return db.delete(DATABASE_TABLE, KEY_ID+"="+_rowIndex, null)>0;
	}
	
	//Update task
	public boolean updateTask(long _rowIndex, String _task){
		ContentValues newValue=new ContentValues();
		newValue.put(KEY_TASK,_task);
		return db.update(DATABASE_TABLE, newValue, KEY_ID+"="+_rowIndex, null)>0;
	}
	
	//Update cloudId
	public boolean updateCloudId(long _rowIndex, String _cloudId) {
		ContentValues newValue=new ContentValues();
		newValue.put(KEY_CLOUDID, _cloudId);
		return db.update(DATABASE_TABLE, newValue, KEY_ID+"="+_rowIndex, null)>0;

	}
	
	//Update isReminder
	public boolean updateIsReminder(long _rowIndex, long isReminder) {
		ContentValues newValue=new ContentValues();
		newValue.put(KEY_ISREMINDER, isReminder);
		return db.update(DATABASE_TABLE, newValue, KEY_ID+"="+_rowIndex, null)>0; 
	}
	
//	public boolean updateHalfSync(long _rowIndex) {
//		ContentValues newValue= new ContentValues();
//		newValue.put(KEY_ISSYNC, 1);
//		return db.update(DATABASE_TABLE, newValue, KEY_ID+"="+_rowIndex, null)>0; 
//	}
	
	public Cursor getAllToDoItemsCursor(){
		return db.query(DATABASE_TABLE,
				new String[] { KEY_ID, KEY_TASK, KEY_TASK_DATE, KEY_TASK_FROM, KEY_TASK_TO, KEY_CLOUDID, KEY_DUEDATE},
				null,null,null,null,null);
	}
	
	public Cursor setCursorToDoItem(long _rowIndex) throws SQLException{
		Cursor result=db.query(true, DATABASE_TABLE, new String[]{KEY_ID, KEY_TASK}, KEY_ID+"="+_rowIndex, null, null, null, null, null);
		if ((result.getCount()==0)||(!result.moveToFirst())) {
			throw new SQLException("No task iems found for the row: "+_rowIndex);
		}
		return result;
	}
	
	public aryaTaskItem getToDoItem(long _rowIndex) throws SQLException{
		Cursor result=db.query(true, DATABASE_TABLE, 
				new String[]{KEY_ID, KEY_TASK, KEY_TASK_DATE, KEY_TASK_FROM, KEY_TASK_TO, KEY_DUEDATE, KEY_FROMUSER, KEY_TOUSER}, 
				KEY_ID+"="+_rowIndex, null, null, null, null, null);
		if ((result.getCount()==0)||(!result.moveToFirst())) {
			throw new SQLException("No task iems found for the row: "+_rowIndex);
		}
		
		long _taskLocalId=result.getLong(0);
		String _taskSubject=result.getString(1);
		String _taskDate=result.getString(2);
		String _taskFrom=result.getString(3);
		String _taskTo=result.getString(4);
		String _taskDueDate=result.getString(5);
		String _taskFromUser=result.getString(6);
		String _taskToUser=result.getString(7);
		
		return new aryaTaskItem(_taskLocalId, _taskSubject, new Date(_taskDate),new Date(_taskDueDate), _taskFrom, _taskTo,null,_taskFromUser, _taskToUser);
	}
	
	public Cursor getDayToDoItem()  throws SQLException{
		Calendar today=Calendar.getInstance();
		String startDate=dateFormat.format(today.getTime());
		today.add(Calendar.DATE, 1);
		String endDate=dateFormat.format(today.getTime());
		
		try {
			return db.query(DATABASE_TABLE,
					new String[] { KEY_ID, KEY_TASK, KEY_TASK_DATE},
					KEY_TASK_DATE+" BETWEEN ? AND ?",new String[]{startDate, endDate},null,null,null);
		} catch (Exception e) {
			// TODO: handle exception
			throw new SQLException("No task iems found for the row:");
		}		
	}
	
	public Cursor getNotSyncedTasks() throws SQLException {
		try {
			return db.query(DATABASE_TABLE,
					new String[] { KEY_ID, KEY_TASK, KEY_TASK_DATE, KEY_TASK_FROM, KEY_TASK_TO, KEY_CLOUDID, KEY_DUEDATE, KEY_FROMUSER, KEY_TOUSER},
					KEY_CLOUDID+ " IS NULL",null,null,null,null);
		} catch (Exception e) { 
			// TODO: handle exception
			throw new SQLException("No task iems found for the row:");
		}
	}
	
	private static class AryaDBOpenHelper extends SQLiteOpenHelper{
		
		public AryaDBOpenHelper(Context context, String name, CursorFactory factory, int version){
			super(context, name,factory,version);
		}
		
		private static final String TASKTABLE_CREATE="create table "+ DATABASE_TABLE+
				" ("+ KEY_ID+" integer primary key autoincrement, "+
				KEY_TASK+" text not null, "+ 
				KEY_TASK_DATE+ " long, "+
				KEY_DUEDATE+ " long, "+
				KEY_CLOUDID+" text, "+
				KEY_TASK_FROM+" text, "+
				KEY_TASK_TO+" text, "+
				KEY_ISREMINDER+" integer, "+
				KEY_FROMUSER+" text, "+
				KEY_TOUSER+" text"+
				");";
		
		private static final String FRIENDSTABLE_CREATE="create table "+ FRIENDSLIST_TABLE+
				" ("+ KEY_FRIENDSID+" integer primary key autoincrement, "+
				KEY_FRIENDNAME+" text not null, "+
				KEY_MAIL+" text not null);";
		
		@Override
		public void onCreate(SQLiteDatabase _db){
			_db.execSQL(TASKTABLE_CREATE);
			_db.execSQL(FRIENDSTABLE_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			Log.w("TaskDBAdapter", "Upgrading from version " +
			_oldVersion + " to "+
			_newVersion +
			", which will destroy all old data");
			// Drop the old table.
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + FRIENDSLIST_TABLE);
			// Create a new one.
			onCreate(_db);
		}
	} 	
}

