package com.zjianhao.bean;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 22:49.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class GridPhoto implements Comparable<GridPhoto>{
    private String dateLabel;
    private List<Photo> photos;

    public String getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(String dateLabel) {
        this.dateLabel = dateLabel;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public int compareTo(GridPhoto another) {
        String[] date1 = dateLabel.split("-");
        String[] date2 = another.dateLabel.split("-");
        for (int i=0; i<date1.length; i++){
            int a = Integer.parseInt(date1[i]);
            int b = Integer.parseInt(date2[i]);
            if (a>b)
                return -1;
            else if (a<b)
                return 1;
        }
        return 0;
    }





}
