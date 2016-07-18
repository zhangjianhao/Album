package com.zjianhao.bizImpl;

import java.util.List;

import com.zjianhao.biz.AdminBiz;
import com.zjianhao.dao.AdminDao;
import com.zjianhao.model.Admin;

public class AdminBizImpl implements AdminBiz {
	AdminDao adminDao;

	
	
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}



	/* (non-Javadoc)
	 * @see com.zjianhao.bizImpl.AdminBiz#login(java.lang.String, java.lang.String)
	 */
	@Override
	public int login(String username,String password){
		List<Admin> result = adminDao.login(username);
		if (result.size()<1)
			return -1;
		for (int i=0; i<result.size(); i++){
			Admin admin = result.get(i);
			if (admin.getPassword().equals(password))
				return 1;
		}
		return 0;
	}

	
	

}
