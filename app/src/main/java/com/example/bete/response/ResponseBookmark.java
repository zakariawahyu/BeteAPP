package com.example.bete.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBookmark{

	@SerializedName("bookmark")
	private List<BookmarkItem> bookmark;

    @SerializedName("status")
	private boolean status;

	public void setBookmark(List<BookmarkItem> bookmark){
		this.bookmark = bookmark;
	}

	public List<BookmarkItem> getBookmark(){
		return bookmark;
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
			"ResponseBookmark{" + 
			"bookmark = '" + bookmark + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}