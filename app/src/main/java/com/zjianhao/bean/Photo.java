package com.zjianhao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zjianhao.utils.LogUtil;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 17:30.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class Photo implements Parcelable {
    private int id;



    private String name;
    private String imgUrl;
    private String thumbNailUrl;
    private double longitude;//经度
    private double latitude;//纬度
    private String location;
    private long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        LogUtil.e(this,"onset:"+longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        LogUtil.e(this,"onset:"+latitude);

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imgUrl);
        dest.writeString(this.thumbNailUrl);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.location);
        dest.writeLong(this.date);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.imgUrl = in.readString();
        this.thumbNailUrl = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.location = in.readString();
        this.date = in.readLong();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
