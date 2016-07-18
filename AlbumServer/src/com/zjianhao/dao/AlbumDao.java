package com.zjianhao.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.zjianhao.model.Album;

public interface AlbumDao {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Album transientInstance);

	public abstract void delete(Album persistentInstance);

	public abstract Album findById(java.lang.Integer id);

	public abstract List<Album> findByExample(Album instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Album> findByAlbumName(Object albumName);

	public abstract List findAll();

	public abstract Album merge(Album detachedInstance);

	public abstract void attachDirty(Album instance);

	public abstract void attachClean(Album instance);

	public abstract List<Album> getAlbumByUserId(int userId);

	public abstract void update(Album album);
	public List<Album> getAlbums(int pageIndex,int pageSize);

}