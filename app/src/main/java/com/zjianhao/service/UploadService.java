package com.zjianhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.zjianhao.album.AppContext;
import com.zjianhao.bean.User;
import com.zjianhao.constants.Constants;
import com.zjianhao.utils.ImgUtil;
import com.zjianhao.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 张建浩（Clarence) on 2016-7-1 16:34.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class UploadService extends Service {

    private ArrayList<String> photos;
    private User user;
    ArrayList<File> tempFile = new ArrayList<>();
    final File filepath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/album/");


    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    LogUtil.v(this,"上传完成,压缩了："+tempFile.size());
                    delteTempFile();
                    break;
                case 0x01:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        photos = intent.getStringArrayListExtra("upload_photos");
        LogUtil.v(this,"on start command");
        AppContext application = (AppContext) getApplication();
        user = application.getUser();
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                startUpload();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.v(this,"on create");

    }

    public void startUpload(){


        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("userId", user.getId()+"");
        for (int i=0; i<photos.size(); i++) {
            File file = new File(photos.get(i));
            File temp;
            if (file.length()>1024*80){
                temp = ImgUtil.compressImage(photos.get(i),filepath.getPath()+"/temp/"+file.getName(),50);
                tempFile.add(temp);
            }else
            temp = file;
            builder.addFormDataPart("photos",file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),temp));
        }

        final Request request = new Request.Builder()
                .url(Constants.UPLOAD_URL)
                .post(builder.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.v(this,"on failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.v(this,"response:"+response.body().string());
                handler.sendEmptyMessage(0x00);
                response.close();

            }
        });


    }


    private void delteTempFile(){
        for (File file:tempFile){
            if (file.exists())
                file.delete();
        }
    }


}
