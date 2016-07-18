package com.zjianhao.bizImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zjianhao.biz.AlbumBiz;
import com.zjianhao.dao.AlbumDao;
import com.zjianhao.dao.UserDao;
import com.zjianhao.model.Album;
import com.zjianhao.model.Photo;
import com.zjianhao.model.User;

public class AlbumBizImpl implements AlbumBiz {
	AlbumDao albumDao;
	UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAlbumDao(AlbumDao albumDao) {
		this.albumDao = albumDao;
	}
	
	/* (non-Javadoc)
	 * @see com.zjianhao.bizImpl.AlbumBiz#isExist(int)
	 */
	@Override
	public boolean isExist(int userId){
		List<Album> albums = albumDao.getAlbumByUserId(userId);
		if (albums.size()>0)
			return true;
		else return false;
	}
	
	/* (non-Javadoc)
	 * @see com.zjianhao.bizImpl.AlbumBiz#insertAlbum(int, java.util.List)
	 */
	@Override
	public void insertAlbum(int userId,ArrayList<Photo> photos){
		User user = userDao.findById(userId);
		Album album = new Album();
		album.setAlbumName(user.getUsername());
		album.setUser(user);
		album.setDate(new Timestamp(System.currentTimeMillis()));
		album.setThumbnail(photos.get(0).getPhotoUrl());
		for (Photo photo:photos){
			photo.setAlbum(album);
		}
		Set<Photo> set = new HashSet<Photo>(photos);
		album.setPhotos(set);
		albumDao.save(album);
//		addPhotos(userId, photos);
	}

	@Override
	public void addPhotos(int userId, ArrayList<Photo> photoList) {
		User user = userDao.findById(userId);
		Album album = (Album)user.getAlbums().toArray()[0];
		for (Photo photo:photoList){
			photo.setAlbum(album);
		}
		album.getPhotos().addAll(photoList);
		albumDao.update(album);
	}
	public List<Album> getAlbums(int pageIndex,int pageSize){
		return albumDao.getAlbums(pageIndex, pageSize);
	}
	

}
