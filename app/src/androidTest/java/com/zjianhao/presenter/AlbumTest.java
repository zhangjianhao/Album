package com.zjianhao.presenter;

import android.content.Intent;
import android.test.InstrumentationTestCase;

import com.zjianhao.album.MainActivity;
import com.zjianhao.bean.Album;
import com.zjianhao.bean.GridPhoto;

import java.util.ArrayList;

/**
 * Created by 张建浩（Clarence) on 2016-7-3 20:05.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AlbumTest extends InstrumentationTestCase {
    AlbumPresenter presenter;
    private MainActivity activity;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.setClassName("com.zjianhao.album", MainActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity = (MainActivity) getInstrumentation().startActivitySync(intent);
        presenter = AlbumPresenter.getInstance();
        System.out.println("aaa");
    }

    public void testgetAlbums(){
        System.out.println("bbb");
        ArrayList<Album> albums = presenter.getAlbums(activity);
        for (Album album : albums) {
            System.out.println(album.getName());
            ArrayList<GridPhoto> gridPhotos = album.mapToList();
            for (GridPhoto gridPhoto : gridPhotos) {
                System.out.println(gridPhoto.getDateLabel()+":"+gridPhoto.getPhotos().get(0).getImgUrl());
            }
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        presenter = null;

    }
}
