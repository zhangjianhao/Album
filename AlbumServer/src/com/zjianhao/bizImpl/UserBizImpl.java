package com.zjianhao.bizImpl;

import java.util.List;

import com.zjianhao.biz.UserBiz;
import com.zjianhao.dao.UserDao;
import com.zjianhao.model.Admin;
import com.zjianhao.model.User;

public class UserBizImpl implements UserBiz {
	UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/* (non-Javadoc)
	 * @see com.zjianhao.bizImpl.UserBiz#login(java.lang.String, java.lang.String)
	 */
	@Override
	public int login(String username,String password){
		List<User> result = userDao.login(username);
		if (result.size()<1)
			return -1;
		for (int i=0; i<result.size(); i++){
			User user = result.get(i);
			if (user.getPassword().equals(password))
				return user.getId();
		}
		return 0;
	}
	
	public User getUser(int userId){
		return userDao.findById(userId);
	}
	
	public int regist(String username,String email,String password){
		//先查找
		List<User> findedUser = userDao.findUser(username, email);
		if (findedUser.size()>1)
			return -1;
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		userDao.add(user);
		return 1;
		
	}

	@Override
	public List<User> getUserList(int pageIndex, int pageSize) {
		return userDao.getUserList(pageIndex, pageSize);
	}
	
	
}
