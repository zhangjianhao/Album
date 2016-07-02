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
import android.widget.TextView;

import com.zjianhao.adapter.PhotoAdapter;
import com.zjianhao.album.AppContext;
import com.zjianhao.album.R;
import com.zjianhao.bean.GridPhoto;
import com.zjianhao.bean.Photo;
import com.zjianhao.bean.User;
import com.zjianhao.constants.Constants;
import com.zjianhao.ui.LoginAty;
import com.zjianhao.ui.PhotoDetailAty;
import com.zjianhao.utils.JsonUtil;
import com.zjianhao.utils.LogUtil;
import com.zjianhao.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张建浩（Clarence) on 2016-6-20 10:28.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class CloudFragment extends Fragment implements PhotoAdapter.OnPhotoItemClickListener ,View.OnClickListener{

    RecyclerView photoList;


    private PhotoAdapter adapter;
    private ProgressBar loadProgress;
    private SwipeRefreshLayout refreshView;
    private TextView clicktoLogin;



    private List<GridPhoto> photos = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    refreshView.setRefreshing(false);
                    try {
                        LogUtil.v(this,(String)msg.obj);
                        JSONObject object = new JSONObject((String)msg.obj);
                        int code = object.getInt("code");
                        if (code>0){
                            photos = JsonUtil.passeToList(object.getJSONArray("entity"));

                        }else {
                            ToastUtil.show(getActivity(),"云端没有数据");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadProgress.setVisibility(View.GONE);
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

        photoList = (RecyclerView)view.findViewById(R.id.photo_list);
        loadProgress = (ProgressBar)view.findViewById(R.id.load_camera_progress);
        refreshView = (SwipeRefreshLayout)view.findViewById(R.id.photoRefresh);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
            }
        });
        clicktoLogin = (TextView)view.findViewById(R.id.click_to_login);
        clicktoLogin.setOnClickListener(this);

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
        AppContext application = (AppContext) getActivity().getApplication();
        User user = application.getUser();
        if (user == null){
          clicktoLogin.setVisibility(View.VISIBLE);
            loadProgress.setVisibility(View.GONE);
            return;
        }
        clicktoLogin.setVisibility(View.GONE);
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS).build();
        FormBody body = new FormBody.Builder()
                .add("userId",user.getId()+"").build();
        final Request request = new Request.Builder()
                .url(Constants.CLOUD_PHOTO)
                .post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x00);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtil.v(this,result);
                Message message = new Message();
                message.what = 0x01;
                message.obj = result;
                handler.sendMessage(message);

            }
        });


    }






    @Override
    public void OnPhotoItemClick(Photo photo, int listPosition, int gridPosition) {

        Intent intent = new Intent(getActivity(), PhotoDetailAty.class);
        intent.putExtra("photo",photo);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_enter_anim,0);

//        showDetailViewPager(photo);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.click_to_login){
            Intent intent = new Intent(getActivity(), LoginAty.class);
            startActivity(intent);
        }

    }
}


