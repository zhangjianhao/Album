package com.zjianhao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zjianhao.adapter.PhotoAdapter;
import com.zjianhao.album.R;
import com.zjianhao.bean.Album;
import com.zjianhao.bean.GridPhoto;
import com.zjianhao.bean.Photo;
import com.zjianhao.presenter.AlbumPresenter;
import com.zjianhao.ui.PhotoDetailAty;
import com.zjianhao.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2016-6-20 10:28.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class PhotoFragment extends Fragment implements PhotoAdapter.OnPhotoItemClickListener {

    RecyclerView photoList;


    private PhotoAdapter adapter;
    private ProgressBar loadProgress;



    private List<GridPhoto> photos = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x00:
                    refreshView.setRefreshing(false);
                    loadProgress.setVisibility(View.GONE);
                    adapter.setData(photos);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };
    private SwipeRefreshLayout refreshView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_list_main, container, false);
        refreshView = (SwipeRefreshLayout)view.findViewById(R.id.photoRefresh);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
            }
        });
        photoList = (RecyclerView)view.findViewById(R.id.photo_list);
        loadProgress = (ProgressBar)view.findViewById(R.id.load_camera_progress);

        photoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PhotoAdapter(getActivity(), photos);
        photoList.setAdapter(adapter);
        adapter.setOnPhotoItemClickListener(this);

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



    @Override
    public void OnPhotoItemClick(Photo photo, int listPosition, int gridPosition) {

        Intent intent = new Intent(getActivity(), PhotoDetailAty.class);
        intent.putExtra("photo",photo);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_enter_anim,0);

//        showDetailViewPager(photo);
    }


}


