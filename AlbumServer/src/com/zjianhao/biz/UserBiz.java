package com.zjianhao.biz;

import java.util.List;

import com.zjianhao.model.User;

public interface UserBiz {
	public User getUser(int userId);
	public abstract int login(String username, String password);
	public int regist(String username,String email,String password);
	
	public List<User> getUserList(int pageIndex,int pageSize);

}