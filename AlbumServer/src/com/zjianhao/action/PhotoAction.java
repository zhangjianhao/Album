package com.zjianhao.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.zjianhao.biz.PhotoBiz;
import com.zjianhao.biz.UserBiz;
import com.zjianhao.model.JsonPhoto;
import com.zjianhao.model.Photo;

public class PhotoAction extends BaseAction {

	private int userId;
	private int photoId;
	private int page;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getUserId() {
		return userId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	PhotoBiz photoBiz;

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPhotoBiz(PhotoBiz photoBiz) {
		this.photoBiz = photoBiz;
	}
	
	public void cloudPhoto(){
		List<Photo> photos = photoBiz.getPhotos(userId);
		List<JsonPhoto> jsonPhotos = new ArrayList<JsonPhoto>();
		JsonPhoto jsonPhoto;
		for (Photo photo:photos){
			jsonPhoto = new JsonPhoto();
			jsonPhoto.setPhotoName(photo.getPhotoName());
			if (photo.getPhotoDate() != null)
			jsonPhoto.setDate(photo.getPhotoDate().getTime());
			jsonPhoto.setPhotoUrl(photo.getPhotoUrl());
			jsonPhotos.add(jsonPhoto);
		}
		System.out.println("request photo");
		response.setContentType("text/html;charset=utf-8");
		String json = null;
		if (photos.size()>0)
			json = listToJson(jsonPhotos);
			json = "{\"code\":"+photos.size()+",\"entity\":"+json+"}";
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private String listToJson(List<JsonPhoto> photos) {
		if (photos.size()<1)
			return null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(photos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
	}
	
	public String delete(){
		if (photoId>0 && userId>0){
			photoBiz.delete(photoId);
			System.out.println("detele id:"+userId);
		}
		System.out.println("detele id:"+userId);
		return SUCCESS;
	}
	
	
	
	
	
	

}
