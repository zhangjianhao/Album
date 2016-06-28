package com.zjianhao.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zjianhao.bean.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2016-6-27 20:32.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class GridAdapter extends BaseAdapter {

    private List<Photo> photos;
    private ImageLoader imageLoader;
    private int listPosition;
    private int width;
    private int height;
    private Context context;

    private PhotoAdapter.OnPhotoItemClickListener listener;


    public GridAdapter(Context context, int listPosition, List<Photo> photos, PhotoAdapter.OnPhotoItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.listPosition = listPosition;
        this.photos = photos;
        imageLoader = ImageLoader.getInstance();
        WindowManager win = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = win.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;
    }


    public void setData(ArrayList<Photo> photos){
        this.photos.clear();
        this.photos.addAll(photos);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            int w = width/3-8;
            imageView.setLayoutParams(new GridView.LayoutParams(w, w));//设置ImageView对象布局

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.OnPhotoItemClick(photos.get(position),listPosition,position);
                }
            });
        }
        else {
            imageView = (ImageView) convertView;
        }


        imageLoader.displayImage("file://"+photos.get(position).getImgUrl(),imageView);
        return imageView;
    }




}
