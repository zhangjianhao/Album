package com.zjianhao.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zjianhao.adapter.PhotoAdapter;
import com.zjianhao.album.R;
import com.zjianhao.bean.Album;
import com.zjianhao.bean.GridPhoto;
import com.zjianhao.presenter.AlbumPresenter;
import com.zjianhao.utils.LogUtil;
import com.zjianhao.widget.AlbumViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2016-6-20 10:28.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class PhotoFragment extends Fragment {
    @InjectView(R.id.photo_list)
    RecyclerView photoList;
    @InjectView(R.id.photo_detail_pager)
    AlbumViewPager photoDetailPager;
    @InjectView(R.id.photo_detail_back)
    ImageView photoDetailBack;
    @InjectView(R.id.album_item_header_bar)
    RelativeLayout albumItemHeaderBar;
    @InjectView(R.id.photo_detail_frame)
    FrameLayout photoDetailFrame;
    private PhotoAdapter adapter;

    private List<GridPhoto> photos = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x00:
                    adapter.setData(photos);
                    adapter.notifyDataSetChanged();

                    break;
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_list_main, container, false);
        ButterKnife.inject(this, view);

        photoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PhotoAdapter(getActivity(), photos);
        photoList.setAdapter(adapter);


        getdata();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void getdata() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                AlbumPresenter presenter = AlbumPresenter.getInstance();
                presenter.addOnFinishLoadAlbumListener(new AlbumPresenter.OnFinishLoadAlbum() {
                    @Override
                    public void onFinishLoadAlbumListener(ArrayList<Album> albums, Album cameraPhotos) {
                        LogUtil.v(this, "photo finish");
                        photos = cameraPhotos.mapToList();
                        handler.sendEmptyMessage(0x00);
                    }
                });
                presenter.getAlbums(getActivity());
            }
        }).start();

    }


    @OnClick(R.id.photo_detail_back)
    public void onClick() {

    }

    public void showDetailViewPager(){
        photoDetailFrame.setVisibility(View.VISIBLE);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0.9, 1, (float) 0.9, 1, photoDetailFrame.getWidth() / 2, photoDetailFrame.getHeight() / 2);
        scaleAnimation.setDuration(300);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        photoDetailFrame.startAnimation(set);
    }


    public void hideDetailViewPager(){
        photoDetailFrame.setVisibility(View.GONE);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, (float) 0.9, 1, (float) 0.9, photoDetailFrame.getWidth() / 2, photoDetailFrame.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        photoDetailFrame.startAnimation(set);
    }
}


