package com.zjianhao.action;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.zjianhao.biz.UserBiz;
import com.zjianhao.model.JsonUser;
import com.zjianhao.model.User;

public class UserAction extends BaseAction{
	private String username;
	private String password;
	private String email;
	
	private int page;
	
	
	
	public void setEmail(String email) {
		this.email = email;
	}

	UserBiz userBiz;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}
	
	public void login(){
		int result = userBiz.login(username, password);
		String json = null;
		try {
			if (result>0){
				User user = userBiz.getUser(result);
				JsonUser jsonUser = new JsonUser(user.getId(),user.getUsername(),user.getEmail());
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(jsonUser);
			}
			
			
			response.setContentType("text/html;charset=utf-8");
			json = "{\"code\":"+result+",\"entity\":"+json+"}";
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (result) {
		case -1:
			System.out.println("no user");
			break;
		case 0:			
			System.out.println("password error");
			break;
		case 1:
			System.out.println("login success");
			
			break;

		default:
			break;
		}
	}
	
	public void regist(){
		try {
			response.setContentType("text/html;charset=utf-8");
			int result = userBiz.regist(username, email, password);
			response.getWriter().write(result+"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void userlist(){
		
	}
	
	
	
	
	
	

}
