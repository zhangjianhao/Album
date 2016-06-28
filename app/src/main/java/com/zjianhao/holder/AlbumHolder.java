package com.zjianhao.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjianhao.album.R;
import com.zjianhao.ui.AlbumDetailAty;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 21:06.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AlbumHolder extends RecyclerView.ViewHolder {
    public ImageView albumThumbnail;
    public TextView albumName;
    public TextView photoSize;

    public AlbumHolder(final Context context,View itemView) {
        super(itemView);
        albumThumbnail = (ImageView)itemView.findViewById(R.id.album_item_thumbnail);
        albumName = (TextView) itemView.findViewById(R.id.album_name);
        photoSize = (TextView) itemView.findViewById(R.id.album_photo_size);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumDetailAty.class);
                intent.putExtra("album_position",getAdapterPosition());
                context.startActivity(intent);
            }
        });
    }

}
