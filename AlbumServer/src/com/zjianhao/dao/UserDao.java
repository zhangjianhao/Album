package com.zjianhao.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.zjianhao.model.Admin;
import com.zjianhao.model.User;

public interface UserDao {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(User transientInstance);

	public abstract void delete(User persistentInstance);

	public abstract User findById(java.lang.Integer id);

	public abstract List<User> findByExample(User instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<User> findByUsername(Object username);

	public abstract List<User> findByEmail(Object email);

	public abstract List<User> findByPassword(Object password);

	public abstract List findAll();

	public abstract User merge(User detachedInstance);

	public abstract void attachDirty(User instance);

	public abstract void attachClean(User instance);

	public abstract List<User> login(String username);
	public void add(User user);
	public List<User> findUser(String username,String email);
	public List<User> getUserList(int pageIndex,int pageSize);

}