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

import com.zjianhao.adapter.AlbumAdapter;
import com.zjianhao.album.R;
import com.zjianhao.bean.Album;
import com.zjianhao.presenter.AlbumPresenter;
import com.zjianhao.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张建浩（Clarence) on 2016-6-20 10:32.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AlbumFragment extends Fragment {
    @InjectView(R.id.local_album_list)
    RecyclerView localAlbumList;
    private AlbumAdapter adapter;

    private List<Album> albums = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x00:
//                    ToastUtil.show(getActivity(),"load over");
                    adapter.setData(albums);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_album_list_main, container, false);
        ButterKnife.inject(this, view);
        adapter = new AlbumAdapter(getActivity(),albums);
        localAlbumList.setLayoutManager(new LinearLayoutManager(getActivity()));
        localAlbumList.setAdapter(adapter);
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
                        LogUtil.v(this,"finish:album");
                        AlbumFragment.this.albums = albums;
                        handler.sendEmptyMessage(0x00);
                    }
                });
            }
        }).start();

    }
}
