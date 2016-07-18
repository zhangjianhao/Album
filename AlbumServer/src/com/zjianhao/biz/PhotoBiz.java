package com.zjianhao.biz;

import java.util.List;

import com.zjianhao.model.Photo;

public interface PhotoBiz {

	public abstract List<Photo> getPhotos(int userId);

	public void delete(int photoId);
}