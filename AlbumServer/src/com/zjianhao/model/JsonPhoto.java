package com.zjianhao.model;

import java.io.Serializable;

public class JsonPhoto implements Serializable{
	private String photoName;
	private long date;
	private String photoUrl;
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
	public String getPhotoName() {
		return photoName;
	}
	public long getDate() {
		return date;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	

}
