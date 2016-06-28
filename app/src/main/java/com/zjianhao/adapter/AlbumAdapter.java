package com.zjianhao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zjianhao.album.R;
import com.zjianhao.bean.Album;
import com.zjianhao.holder.AlbumHolder;

import java.util.List;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 21:06.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

    private final ImageLoader imageLoader;
    private Context context;
    private List<Album> albums;
    private LayoutInflater inflater;

    public AlbumAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
        inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.album_list_item, parent, false);

        AlbumHolder holder = new AlbumHolder(context,view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
        Album album = albums.get(position);
        imageLoader.displayImage("file://"+album.getThumbnail(),holder.albumThumbnail);
        holder.photoSize.setText(album.getSize()+"");
        holder.albumName.setText(album.getName());

    }

    public void setData(List<Album> a){
        this.albums  = a;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
