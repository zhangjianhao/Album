package com.zjianhao.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.zjianhao.model.Photo;

public interface PhotoDao {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Photo transientInstance);

	public abstract void delete(Photo persistentInstance);

	public abstract Photo findById(java.lang.Integer id);

	public abstract List<Photo> findByExample(Photo instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Photo> findByPhotoName(Object photoName);

	public abstract List<Photo> findByPhotoUrl(Object photoUrl);

	public abstract List<Photo> findByLatitude(Object latitude);

	public abstract List<Photo> findByLongitude(Object longitude);

	public abstract List findAll();

	public abstract Photo merge(Photo detachedInstance);

	public abstract void attachDirty(Photo instance);

	public abstract void attachClean(Photo instance);
	public List<Photo> getPhotoByAlbumId(int albumId);

}