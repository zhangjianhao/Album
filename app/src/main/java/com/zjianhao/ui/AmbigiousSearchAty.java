package com.zjianhao.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zjianhao.adapter.GridAdapter;
import com.zjianhao.adapter.PhotoAdapter;
import com.zjianhao.album.AppContext;
import com.zjianhao.album.R;
import com.zjianhao.bean.Album;
import com.zjianhao.bean.Photo;
import com.zjianhao.presenter.PhotoPresenter;
import com.zjianhao.utils.DateUtil;
import com.zjianhao.utils.LogUtil;
import com.zjianhao.utils.TimeUtil;
import com.zjianhao.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2016-6-26 11:36.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AmbigiousSearchAty extends AppCompatActivity implements PhotoAdapter.OnPhotoItemClickListener {
    @InjectView(R.id.search_back)
    ImageView searchBack;
    @InjectView(R.id.search_keyword)
    EditText searchKeyword;
    @InjectView(R.id.search_command)
    ImageView searchCommand;
    @InjectView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @InjectView(R.id.search_result_photos)
    GridView searchResultPhotos;
    @InjectView(R.id.search_not_found)
    RelativeLayout searchNotFound;
    @InjectView(R.id.is_searching)
    RelativeLayout isSearching;
    private PhotoPresenter presenter;
    private ArrayList<Photo> resultPhotos = new ArrayList<>();
    private InputMethodManager inputManager;

    private GridAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    isSearching.setVisibility(View.GONE);

                    if (resultPhotos != null && resultPhotos.size() > 0) {
                        searchResultPhotos.setVisibility(View.VISIBLE);
                        searchNotFound.setVisibility(View.GONE);
                        adapter.setData(resultPhotos);
                        adapter.notifyDataSetChanged();
                        for (Photo resultPhoto : resultPhotos) {
                            LogUtil.v(this,resultPhoto.getName()+":date:"+ TimeUtil.parseLongToString(resultPhoto.getDate()));
                        }


                    } else {

                        searchNotFound.setVisibility(View.VISIBLE);
                        searchResultPhotos.setVisibility(View.GONE);
                    }
                    break;
                case 0x01:

                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ambiguous_main);
        ButterKnife.inject(this);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (android.os.Build.VERSION.SDK_INT>=21)
            mainToolbar.setElevation(20);

        if (getIntent() != null){
            LogUtil.e(this,"intent not null");
            ArrayList<Photo> result  =getIntent().getParcelableArrayListExtra("search_result");
            if (result != null && result.size()>1)
                resultPhotos = result;
            else if (result!= null && result.size() == 0 ){
                searchNotFound.setVisibility(View.VISIBLE);
            }

        }
        if (inputManager.isActive())
            inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);

        AppContext application = (AppContext) getApplication();
        List<Album> albums = application.getAlbums();
        presenter = new PhotoPresenter(albums);
        adapter = new GridAdapter(this,0,resultPhotos,this);
        searchResultPhotos.setAdapter(adapter);
        searchResultPhotos.setVisibility(View.VISIBLE);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (inputManager.isActive())
            inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick({R.id.search_back, R.id.search_command})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                finish();
                break;

            case R.id.search_command:
                searchKeyword.clearFocus();
                inputManager.hideSoftInputFromWindow(searchKeyword.getWindowToken(),0);
                if (TextUtils.isEmpty(searchKeyword.getText()))
                    ToastUtil.show(this, "请输入内容");
                else {
                    isSearching.setVisibility(View.VISIBLE);
                    searchNotFound.setVisibility(View.GONE);

                    searchResultPhotos.setVisibility(View.GONE);
                    String keyword = searchKeyword.getText().toString().trim();
                    if (DateUtil.isDate(keyword)){
                        keyword = DateUtil.parseToDate(keyword);
                        searchByDate(keyword);

                    }
                    else searchByName(keyword);
                }
                break;

        }
    }





    public void searchByName(final String keyword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                resultPhotos = presenter.getPhotoByName(keyword);
                handler.sendEmptyMessage(0x00);
            }
        }).start();
    }

    public void searchByDate(final String date) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                resultPhotos = presenter.getPhotoByDate(date);
                handler.sendEmptyMessage(0x00);
            }
        }).start();
    }


    @Override
    public void OnPhotoItemClick(Photo photo, int listPosition, int gridPosition) {
        Intent intent = new Intent(this,PhotoDetailAty.class);
        intent.putExtra("photo",photo);
        startActivity(intent);
    }
}
