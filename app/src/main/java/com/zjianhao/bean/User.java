package com.zjianhao.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements Parcelable {

	// Fields

	private Integer id;
	private String username;
	private String email;
	private String password;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	// Property accessors


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.id);
		dest.writeString(this.username);
		dest.writeString(this.email);
		dest.writeString(this.password);
	}

	protected User(Parcel in) {
		this.id = (Integer) in.readValue(Integer.class.getClassLoader());
		this.username = in.readString();
		this.email = in.readString();
		this.password = in.readString();
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}