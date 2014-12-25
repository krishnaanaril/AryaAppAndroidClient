package com.example.aarya;

public class AryaTaskModel {
	
	public long taskLocalId;
	
	public String taskTitle;
	
	public String taskDate;
	
	public String taskFrom;
	
	public String taskTo;	
	
	public String taskDueDate;
	
	public String taskCloudID;
	
	public AryaTaskModel(long _taskLocalId, String _taskTitle, String _taskDate, 
			String _taskFrom, String _taskTo, String _taskCloudID, String _tashDueDate) {
		this.taskDate=_taskDate;
		this.taskTitle=_taskTitle;
		this.taskLocalId=_taskLocalId;
		this.taskFrom=_taskFrom;
		this.taskTo=_taskTo;
		this.taskCloudID=_taskCloudID;
		this.taskDueDate=_tashDueDate;
	}	
}
