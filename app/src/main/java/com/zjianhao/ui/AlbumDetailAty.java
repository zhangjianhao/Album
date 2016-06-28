package com.zjianhao.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.zjianhao.adapter.PhotoAdapter;
import com.zjianhao.adapter.PhotoAdapter.OnPhotoItemClickListener;
import com.zjianhao.album.AppContext;
import com.zjianhao.album.R;
import com.zjianhao.bean.Photo;
import com.zjianhao.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张建浩（Clarence) on 2016-6-25 15:05.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AlbumDetailAty extends AppCompatActivity implements OnPhotoItemClickListener {
    @InjectView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @InjectView(R.id.photo_list)
    RecyclerView photoList;
    @InjectView(R.id.load_camera_progress)
    ProgressBar loadCameraProgress;

    private PhotoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_detail_main);
        ButterKnife.inject(this);
        setSupportActionBar(mainToolbar);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= 21)
            mainToolbar.setElevation(20);
        Intent intent = getIntent();
        int positon = 0;
        if (intent != null)
            positon = intent.getIntExtra("album_position", 0);



        AppContext application = (AppContext) getApplication();
        adapter = new PhotoAdapter(this, application.getAlbums().get(positon).mapToList());
        adapter.setOnPhotoItemClickListener(this);
        mainToolbar.setTitle(application.getAlbums().get(positon).getName());
        mainToolbar.setTitleTextColor(Color.WHITE);
        photoList.setLayoutManager(new LinearLayoutManager(this));
        photoList.setAdapter(adapter);
        loadCameraProgress.setVisibility(View.GONE);

    }

    @Override
    public void OnPhotoItemClick(Photo photo, int listPosition, int gridPosition) {
        LogUtil.e(this,"onitemclick:"+photo.getLatitude()+":"+photo.getLongitude());
        Intent intent = new Intent(this, PhotoDetailAty.class);
        intent.putExtra("photo", photo);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter_anim, 0);
    }
}
