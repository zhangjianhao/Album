package com.zjianhao.action;

import com.opensymphony.xwork2.ActionSupport;
import com.zjianhao.biz.AdminBiz;
import com.zjianhao.model.Admin;

public class AdminAction extends BaseAction{

	
	private String username;
	private String password;
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	AdminBiz adminBiz;
	
	
	
	public void setAdminBiz(AdminBiz adminBiz) {
		this.adminBiz = adminBiz;
	}
	@Override
	public String execute() throws Exception {
		int result = adminBiz.login(username, password);
		
		
		switch (result) {
		case -1:
			this.addFieldError("username", "该用户不存在");
			System.out.println("no user");
			return "login";
		case 0:
			this.addFieldError("password", "密码错误");
			System.out.println("password error");
			return "login";
		case 1:
			System.out.println("login success");
			getSession().put("username", username);
			return SUCCESS;

		default:
			break;
		}
		return super.execute();
	}
	
	
	
	

}
