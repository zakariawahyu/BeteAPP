package com.example.bete.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseForum{

	@SerializedName("forum")
	private List<ForumItem> forum;

	@SerializedName("status")
	private boolean status;

	public void setForum(List<ForumItem> forum){
		this.forum = forum;
	}

	public List<ForumItem> getForum(){
		return forum;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseForum{" + 
			"forum = '" + forum + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}