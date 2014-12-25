package com.example.aarya;

import java.text.SimpleDateFormat;
import java.util.Date;


public class aryaTaskItem {
	
	private String taskSubject;
	
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy  HH:mm");
	
	private Date taskDueDate;
	
	private Date taskDate;
	
	private String taskFrom;
	
	private String fromUserName;
	
	private String toUserName;
	
	private String taskTo;
	
	private long taskLocalId;
	
	private String taskCloudId;
	
	public aryaTaskItem(long _taskLocalId, String _taskSubject, Date _taskDate, 
			Date _taskDueDate, String _taskFrom, String _taskTo, String _taskCloudId,
			String _fromUserName, String _toUsername ){
		this.taskLocalId=_taskLocalId;
		this.taskSubject=_taskSubject;
		this.taskDate=_taskDate;
		this.taskDueDate=_taskDueDate;
		this.taskFrom=_taskFrom;
		this.taskTo=_taskTo;
		this.taskCloudId=_taskCloudId;
		this.fromUserName=_fromUserName;
		this.toUserName=_toUsername;
	}
	
	public void setLocalId(long _id) {
		this.taskLocalId=_id;
	}
	
	public String getSubject(){
		return taskSubject;
	}
	
	public String getDate(){
		String _taskDate=dateFormat.format(taskDate);
		return _taskDate;
	}
	
	public String getDueDate(){
		String _taskDueDate=dateFormat.format(taskDueDate);
		return _taskDueDate;
	}
	
	public String getFrom() {
		return taskFrom;
	}
	
	public String getCloudId() {
		return taskCloudId;
	}
	
	public String getTo() {
		return taskTo;
	}
	
	public long getLocalId() {
		return taskLocalId;
	}
	
	public String getFromUser() {
		return fromUserName;
	}
	
	public String getToUser() {
		return toUserName;
	}
}
