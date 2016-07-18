package com.zjianhao.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.zjianhao.model.Admin;

public interface AdminDao {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Admin transientInstance);

	public abstract void delete(Admin persistentInstance);

	public abstract Admin findById(java.lang.Integer id);

	public abstract List<Admin> findByExample(Admin instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Admin> findByAdminName(Object adminName);

	public abstract List<Admin> findByEmail(Object email);

	public abstract List<Admin> findByPassword(Object password);

	public abstract List findAll();

	public abstract Admin merge(Admin detachedInstance);

	public abstract void attachDirty(Admin instance);

	public abstract void attachClean(Admin instance);

	public abstract List<Admin> login(String username);

}