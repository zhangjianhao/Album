package com.zjianhao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.zjianhao.album.R;
import com.zjianhao.constants.Constants;
import com.zjianhao.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 张建浩（Clarence) on 2016-6-24 17:37.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class TestAty extends AppCompatActivity {


    @InjectView(R.id.query_photo)
    Button queryPhoto;
    private ArrayList<String> selectPath = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        ButterKnife.inject(this);


    }

    public void getAddress(double latitude, double longitude) {
        LogUtil.isDebug(true);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.map.baidu.com/geocoder/v2/?ak=F4T8ucQkc9l7C10PLW38VtSM&callback=renderReverse&location=" + latitude + "," + longitude + "&output=json&pois=1&coordtype=wgs84ll&mcode=73:5B:FC:73:85:F9:C4:05:21:49:82:87:21:61:5E:BF:87:FD:42:17;com.zjianhao.baiddupoi").build();
//        Request request = new Request.Builder().url("http://www.baidu.com").build();
        LogUtil.v(this, "http request start2");
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.v(TestAty.this, "------request f--------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtil.v(TestAty.this, "------request true--------"+string);
            }




        });
    }


    @OnClick(R.id.query_photo)
    public void onClick() {


//        getAddress(25.321762,110.411217);
//        ToastUtil.show(this,"aa");
//        getImageFromAlbum();
        startUpload();
    }


    public void startUpload(){

        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("userId", "123");


        builder.addFormDataPart("photos","aa.jpg", RequestBody.create(MediaType.parse("application/octet-stream"),new File("/storage/emulated/0/DCIM/Camera/IMG20160528204240.jpg")));
//        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=" + "aaa.jpg" + "")
//                ,RequestBody.create(MediaType.parse("application/octet-stream"), new File("/storage/emulated/0/DCIM/Camera/IMG20160528204240.jpg")));


        final Request request = new Request.Builder()
                .url(Constants.UPLOAD_URL)
                .post(builder.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.v(this,"on failure");
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.v(this,"response:"+response.body().string());
//                response.close();

            }
        });


    }


    public void getImageFromAlbum() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 100);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, selectPath);
        startActivityForResult(intent, 0x01);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0x01:
                if(resultCode == RESULT_OK && data != null){

                    if(resultCode == RESULT_OK){
                        // Get the result list of select image paths
                        List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        for (String s : path) {
                            LogUtil.v(TestAty.this,s);
                        }
                        // do your logic ....
                    }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
