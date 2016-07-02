package com.zjianhao.presenter;

import com.zjianhao.bean.Album;
import com.zjianhao.bean.GridPhoto;
import com.zjianhao.bean.Photo;
import com.zjianhao.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2016-6-25 22:36.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class PhotoPresenter {


    private List<Album> albums;
    private int count = 0;

    public PhotoPresenter(List<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Photo> getPhotoByDate(String date){

        ArrayList<Photo> photos = new ArrayList<>();
        for (Album album : albums) {
            ArrayList<GridPhoto> gridPhotos = album.mapToList();
            for (GridPhoto gridPhoto : gridPhotos) {
                if (gridPhoto.getDateLabel().contains(date))
                    photos.addAll(gridPhoto.getPhotos());
            }

        }
        return photos;
    }


    public ArrayList<Photo> getPhotoByDateInterval(String startDate,String endDate){
        LogUtil.v(this,startDate+":"+endDate);
        ArrayList<Photo> photos = new ArrayList<>();
        GridPhoto start = new GridPhoto();
        start.setDateLabel(startDate);
        GridPhoto end = new GridPhoto();
        end.setDateLabel(endDate);
        for (Album album : albums) {
            ArrayList<GridPhoto> gridPhotos = album.mapToList();
            for (GridPhoto gridPhoto : gridPhotos) {
                if (gridPhoto.compareTo(start) <=0 && gridPhoto.compareTo(end)>=0)
                    photos.addAll(gridPhoto.getPhotos());
            }

        }
        return photos;
    }


    public ArrayList<Photo> getPhotoByName(ArrayList<Photo> photos,String photoName){
        ArrayList<Photo> result = new ArrayList<>();
        for (Photo photo : photos) {
            if (photo.getName().matches(getKeywords(photoName))){
                LogUtil.e(this,photo.getName()+":match:"+getKeywords(photoName));
                result.add(photo);

            }
        }
        return result;
    }

    public ArrayList<Photo> getPhotoByLocation(ArrayList<Photo> photos,final String location){
        count = 0;
        final ArrayList<Photo> result = new ArrayList<>();
        LocationPresenter presenter = new LocationPresenter();
        for (final Photo photo : photos) {
            if (photo.getLocation() != null) {
                if (photo.getLocation().matches(getKeywords(location)))
                    photos.add(photo);
            } else {
                if (Math.abs(photo.getLatitude() - 0) > 0) {
                    count++;
                    presenter.getAddress(photo.getLatitude(), photo.getLongitude(), new LocationPresenter.OnTranSuccessListener() {
                        @Override
                        public void onTranSuccess(String addr) {
                            photo.setLocation(addr);
                            if (addr != null && addr.matches(".*" + location + ".*"))
                                result.add(photo);
                            count--;

                        }
                    });
                }

            }
        }
        //要等所有的经纬度转化完全后才返回结果，此方法较为耗时
        while(count>0);


        return result;
    }


    public ArrayList<Photo> getPhotoByLocation(final String location){
        count = 0;
        final ArrayList<Photo> photos = new ArrayList<>();
        for (Album album : albums) {
            ArrayList<GridPhoto> gridPhotos = album.mapToList();
            for (GridPhoto gridPhoto : gridPhotos) {
                List<Photo> datephotos = gridPhoto.getPhotos();
                for (final Photo photo : datephotos) {
                    if (photo.getLocation() != null) {
                        if (photo.getLocation().matches(getKeywords(location)))
                            photos.add(photo);
                    } else {
                        if (Math.abs(photo.getLatitude() - 0) > 0) {
                            count++;
                            LocationPresenter presenter = new LocationPresenter();
                            presenter.getAddress(photo.getLatitude(), photo.getLongitude(), new LocationPresenter.OnTranSuccessListener() {
                                @Override
                                public void onTranSuccess(String addr) {
                                    photo.setLocation(addr);
                                    if (addr != null && addr.matches(".*" + location + ".*"))
                                        photos.add(photo);
                                    count--;

                                }
                            });
                        }

                    }
                }

            }
        }

        while(count>0);

        return photos;

    }


    public ArrayList<Photo> getPhotoByName(String photoName){
        ArrayList<Photo> photos = new ArrayList<>();
        for (Album album : albums) {
            ArrayList<GridPhoto> gridPhotos = album.mapToList();
            for (GridPhoto gridPhoto : gridPhotos) {
                List<Photo> datephotos = gridPhoto.getPhotos();
                for (final Photo photo : datephotos) {
                    if (photo.getName().matches(getKeywords(photoName))){
                        LogUtil.e(this,photo.getName()+":match:"+getKeywords(photoName));
                        photos.add(photo);

                    }
                }
            }
        }
        return photos;
    }


    public String getKeywords(String keywords){
        String[] split = keywords.split("\\s+");
        StringBuilder builder = new StringBuilder();
        builder.append(".*");
        for (int i = 0; i < split.length; i++) {
            builder.append("("+split[i]+")+.*");
        }
        return builder.toString();
    }




}
