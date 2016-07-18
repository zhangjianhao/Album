package com.zjianhao.bizImpl;

import java.util.ArrayList;
import java.util.List;

import com.zjianhao.biz.PhotoBiz;
import com.zjianhao.dao.AlbumDao;
import com.zjianhao.dao.PhotoDao;
import com.zjianhao.dao.UserDao;
import com.zjianhao.model.Album;
import com.zjianhao.model.Photo;
import com.zjianhao.model.User;

public class PhotoBizImpl implements PhotoBiz {
	PhotoDao photoDao;
	UserDao userDao;
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}
	
	/* (non-Javadoc)
	 * @see com.zjianhao.bizImpl.PhotoBiz#getPhotos(int)
	 */
	@Override
	public List<Photo> getPhotos(int userId){
		User user = userDao.findById(userId);
		if (user == null){
			System.out.println("userId:"+userId+"user is null");
			return new ArrayList<Photo>();
		}
		Object[] albums = user.getAlbums().toArray();
		if (albums.length ==0)
			return new ArrayList<Photo>();
		else 
		return photoDao.getPhotoByAlbumId(((Album)albums[0]).getId());
	}
	
	public void delete(int photoId){
		Photo photo = new Photo();
		photo.setId(photoId);
		photoDao.delete(photo);
	}
	
	

}
