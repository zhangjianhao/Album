package com.zjianhao.action;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.zjianhao.biz.AlbumBiz;
import com.zjianhao.biz.UserBiz;
import com.zjianhao.model.Photo;
import com.zjianhao.model.User;

public class UploadAction extends BaseAction{
	private int userId;
	private File [] photos;
	private String [] photosFileName;
	
	public void setPhotos(File[] photos) {
		this.photos = photos;
	}

	public void setPhotosFileName(String[] photosFileName) {
		this.photosFileName = photosFileName;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}	
	
	UserBiz userBiz;
	AlbumBiz albumBiz;	
	
	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

	public void setAlbumBiz(AlbumBiz albumBiz) {
		this.albumBiz = albumBiz;
	}

	public void upload() throws IOException{
		saveFiles();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("success");
	}

	private void saveFiles() {
		try {
			System.out.println("save start"+photos.length);
			ArrayList<Photo> photoList = new ArrayList<Photo>();
			Photo photo ;
			User user = userBiz.getUser(userId);
			System.out.println("username:"+user.getUsername());
			String root = ServletActionContext.getServletContext().getRealPath("/upload/"+user.getUsername());
			System.out.println(root);
			
				for (int i=0; i<photos.length; i++){
					File file = photos[i];
					File dstFile = new File(root,photosFileName[i]);
					if (dstFile.exists())
						continue;
					if (!dstFile.getParentFile().exists())
						dstFile.getParentFile().mkdirs();
					FileUtils.copyFile(file, dstFile);
					photo = new Photo();
					photo.setPhotoName(photosFileName[i]);
					photo.setPhotoUrl("/upload/"+user.getUsername()+"/"+photosFileName[i]);
					photo.setPhotoDate(new Timestamp(System.currentTimeMillis()));
					photoList.add(photo);
					System.out.println(photo.getPhotoUrl()+":"+file.length()/1024);
					
				}
				if (!albumBiz.isExist(userId))
					albumBiz.insertAlbum(userId, photoList);//插入一条相册记录
				else //已经存在相册记录
					albumBiz.addPhotos(userId,photoList);
				System.out.println("save success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
