package com.zjianhao.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.zjianhao.album.AppContext;
import com.zjianhao.album.R;
import com.zjianhao.bean.User;
import com.zjianhao.constants.Constants;
import com.zjianhao.utils.ImgUtil;
import com.zjianhao.utils.LogUtil;
import com.zjianhao.utils.ProgressRequestBody;
import com.zjianhao.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
    private int currentProgress;
    private Timer timer;


    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:

                    break;
                case 0x01:
                    if (timer != null)
                        timer.cancel();
                    currentProgress = 100;
                    mNotification.setContentText(currentProgress+"%");
                    mNotification.setProgress(100,100,false);
                    mNotification.setContentTitle("照片上传完成！");
                    mNotificationManager.notify(1,mNotification.build());
                    ToastUtil.show(getApplicationContext(),"上传完成");
                    LogUtil.v(this,"上传完成,压缩了："+tempFile.size());
                    delteTempFile();
                    break;

            }
            super.handleMessage(msg);
        }
    };
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotification;

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


        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification = new NotificationCompat.Builder(this);
        mNotification.setContentTitle("正在上传照片中");
        mNotification.setSmallIcon(R.mipmap.ic_launcher);
        mNotification.setContentText("0%");
        mNotification.setProgress(100,0,false);
        mNotificationManager.notify(1,mNotification.build());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mNotification.setContentText(currentProgress+"%");
                mNotification.setProgress(100,currentProgress,false);
                mNotificationManager.notify(1,mNotification.build());
            }
        },0,500);

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
            if (file.length()>1024*100){
                temp = ImgUtil.compressImage(photos.get(i),filepath.getPath()+"/temp/"+file.getName(),50);
                tempFile.add(temp);
            }else
            temp = file;
            builder.addFormDataPart("photos",file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),temp));
        }

        final Request request = new Request.Builder()
                .url(Constants.UPLOAD_URL)
                .post(new ProgressRequestBody(builder.build(), new ProgressRequestBody.ProgressRequestListener() {
                    @Override
                    public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                        LogUtil.v(this,"isdone:"+done);
                        if (!done)
                            currentProgress = (int) ((double)bytesWritten/contentLength*100);
                        else handler.sendEmptyMessage(0x01);
                        LogUtil.v(this,"progress:"+currentProgress);


                    }
                })).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.v(this,"on failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.v(this,"response:"+response.body().string());
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
