package com.zjianhao.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zjianhao.album.R;
import com.zjianhao.bean.Photo;
import com.zjianhao.utils.LogUtil;
import com.zjianhao.utils.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张建浩（Clarence) on 2016-6-26 19:41.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class PhotoInfoAty extends AppCompatActivity {
    @InjectView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @InjectView(R.id.info_time_tv)
    TextView infoTimeTv;
    @InjectView(R.id.info_week)
    TextView infoWeek;
    @InjectView(R.id.info_filename)
    TextView infoFilename;
    @InjectView(R.id.info_size)
    TextView infoSize;
    @InjectView(R.id.info_filepath_tv)
    TextView infoFilepathTv;
    @InjectView(R.id.info_location_tv)
    TextView infoLocationTv;
    private Photo photo;
    private boolean switcher = true;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x00:
                    String addr = (String)msg.obj;
                    if (addr != null && addr.trim().length()>0){
                        infoLocationTv.setText(addr);
                        if (photo.getLocation() == null)
                            photo.setLocation(addr);
                    }
                    break;

            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_info_main);
        ButterKnife.inject(this);
        setSupportActionBar(mainToolbar);
        if (android.os.Build.VERSION.SDK_INT>=21)
            mainToolbar.setElevation(20);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainToolbar.setTitleTextColor(Color.WHITE);
        mainToolbar.setTitle(getString(R.string.photo_detail));

        photo = (Photo) getIntent().getParcelableExtra("photo");
        initData();


    }


    public void initData(){
        infoTimeTv.setText(TimeUtil.parseLongToString(photo.getDate()));
        infoWeek.setText(TimeUtil.parseLongToWeek(photo.getDate()));
        infoFilename.setText(photo.getName());
        File file = new File(photo.getImgUrl());
        LogUtil.v(this,"----------"+Environment.DIRECTORY_PICTURES);
        String size = getFileSize(file.length());
        infoSize.setText(size);
        infoFilepathTv.setText(photo.getImgUrl());
        getAddress(photo.getLatitude(),photo.getLongitude());
        if (photo.getLocation() != null)
            infoLocationTv.setText(photo.getLocation());
        else if (photo.getLatitude()>1){
           getAddress(photo.getLatitude(),photo.getLongitude());
        }
    }

    public String getFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
    public void getAddress(double latitude, double longitude) {
        if (switcher)
            switcher = false;
        LogUtil.isDebug(true);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.map.baidu.com/geocoder/v2/?ak=F4T8ucQkc9l7C10PLW38VtSM&callback=renderReverse&location=" + latitude + "," + longitude + "&output=json&pois=1&coordtype=wgs84ll&mcode=73:5B:FC:73:85:F9:C4:05:21:49:82:87:21:61:5E:BF:87:FD:42:17;com.zjianhao.baiddupoi").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.v(PhotoInfoAty.this, "------request f--------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtil.v(PhotoInfoAty.this, "------request true--------"+string);
                String addr = parseJson(string.substring(string.indexOf("(")+1,string.length()-1));
                LogUtil.v(PhotoInfoAty.this,"---------addr:"+addr);
                Message message = new Message();
                message.what = 0x00;
                message.obj = addr;
                handler.sendMessage(message);
            }

        });
    }


    public String parseJson(String json){
        try {
            JSONObject object = new JSONObject(json);
            JSONObject result = object.getJSONObject("result");
            String formatAddr = result.getString("formatted_address");
            JSONArray pois = result.getJSONArray("pois");
            if (pois.length()>0){
                return pois.getJSONObject(0).getString("addr");
            }
            return formatAddr;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
