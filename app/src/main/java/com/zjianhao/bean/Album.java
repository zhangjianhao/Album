package com.zjianhao.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 17:01.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class Album {
    private String name;//相册名
    private int size = 0;
    private String thumbnail;

    private Map<String,List<Photo>> photoMap = new HashMap<>();//每个日期对应的图片

    public Map<String, List<Photo>> getPhotoMap() {
        return photoMap;
    }

    public void setPhotoMap(Map<String, List<Photo>> photoMap) {
        this.photoMap = photoMap;
    }

    public Album(String name, int size, String thumbnail) {
        this.name = name;
        this.size = size;
        this.thumbnail = thumbnail;
    }

    public Album(String name) {
        this.name = name;
    }

    public Album(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public void autoIncrementSize(){
        size++;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<GridPhoto> mapToList(){
        ArrayList<GridPhoto> gridPhotos = new ArrayList<>(photoMap.size());
        Set<String> set = photoMap.keySet();
        Iterator<String> iterator = set.iterator();
        GridPhoto photo ;
        while (iterator.hasNext()){
            photo = new GridPhoto();
            String key = iterator.next();
            photo.setDateLabel(key);
            photo.setPhotos(photoMap.get(key));
            gridPhotos.add(photo);

        }

        Collections.sort(gridPhotos);


        return gridPhotos;
    }



}
