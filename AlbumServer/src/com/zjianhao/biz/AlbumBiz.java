package com.zjianhao.biz;

import java.util.ArrayList;
import java.util.List;

import com.zjianhao.model.Album;
import com.zjianhao.model.Photo;

public interface AlbumBiz {

	public abstract boolean isExist(int userId);

	public abstract void insertAlbum(int userId, ArrayList<Photo> photos);

	public abstract void addPhotos(int userId, ArrayList<Photo> photoList);
	public List<Album> getAlbums(int pageIndex,int pageSize);

}