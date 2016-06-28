package com.zjianhao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjianhao.album.R;
import com.zjianhao.bean.GridPhoto;
import com.zjianhao.bean.Photo;
import com.zjianhao.holder.PhotoHolder;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 22:39.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<GridPhoto> gridPhotos;

    private OnPhotoItemClickListener listener;

    public PhotoAdapter(Context context, List<GridPhoto> photos) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.gridPhotos = photos;


    }

    public interface OnPhotoItemClickListener {
        public void OnPhotoItemClick(Photo photo, int listPosition, int gridPosition);
    }

    public void setOnPhotoItemClickListener(OnPhotoItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.photo_list_item, parent, false);
        PhotoHolder holder = new PhotoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {

        GridPhoto gridPhoto = gridPhotos.get(position);
        holder.date.setText(gridPhoto.getDateLabel());
//        int hn = gridPhoto.getPhotos().size()/3+gridPhoto.getPhotos().size()%3==0? 0:1;
//        int w = width/3-5;
//        LogUtil.v(this,"height should:"+hn*w);
//        holder.photoGrid.setLayoutParams(new LinearLayout.LayoutParams(width,hn*w));
        holder.photoGrid.setAdapter(new GridAdapter(context,position, gridPhoto.getPhotos(),listener));

    }

    public void setData(List<GridPhoto> gridphotos) {
        this.gridPhotos = gridphotos;
    }

    @Override
    public int getItemCount() {
        return gridPhotos.size();
    }

}


