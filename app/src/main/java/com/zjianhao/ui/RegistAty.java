package com.zjianhao.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjianhao.album.R;
import com.zjianhao.constants.Constants;
import com.zjianhao.utils.ToastUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by zjianhao on 15-9-12.
 */
public class RegistAty extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = RegistAty.class.getName();
    private Button summitUserInfo;
    private EditText passwordEt;
    private EditText confirmPasswordEt;
    private EditText emailEt;
    private EditText usernameEt;
    private Toolbar mainToolbar;
    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x00:
                    ToastUtil.show(RegistAty.this,"请求错误，请检查网络连接");
                    break;
                case 0x01:
                    int result = Integer.parseInt((String)msg.obj);
                    switch (result){
                        case 1:
                            Intent intent = new Intent(RegistAty.this, LoginAty.class);
                            startActivity(intent);
                            finish();
                            break;
                        case -1:
                            ToastUtil.show(RegistAty.this,"该用户信息已经存在");
                            break;

                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_main);
        summitUserInfo = (Button)findViewById(R.id.summit_userinfo_btn);
        summitUserInfo.setOnClickListener(this);
        passwordEt = (EditText)findViewById(R.id.password_et);
        confirmPasswordEt = (EditText)findViewById(R.id.confirm_password_et);
        emailEt = (EditText)findViewById(R.id.email_et);
        usernameEt = (EditText)findViewById(R.id.username_et);
        mainToolbar = (Toolbar)findViewById(R.id.main_toolbar);

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
        mainToolbar.setTitle("注册");


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.summit_userinfo_btn:
                if (checkUpdateUI())
                    registUser(Constants.REGIST_URL);
                break;
        }



    }

    public void registUser(String url){

//        params.add("phone_number",phoneNumberEt.getText().toString());
//        params.add("email",emailEt.getText().toString());
//        params.add("password",passwordEt.getText().toString());
//        params.add("username",usernameEt.getText().toString());
//
//
//        client.post(this, Constants.REGIST_URL, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String result = null;
//                Log.v(TAG, bytes.length + "");
//                try {
//                    result = new String(bytes,"utf-8");
//                    if ("500".equals(result)){
//                        Toast.makeText(RegistAty.this,R.string.user_exist,Toast.LENGTH_SHORT).show();
//                    }else {
//                        //注册成功
//                        Toast.makeText(RegistAty.this,"注册成功",Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(RegistAty.this, LoginAty.class);
////                        startActivity(intent);
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//            }
//        });


        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("email",emailEt.getText().toString().trim())
                .add("username",usernameEt.getText().toString().trim())
                .add("password",passwordEt.getText().toString().trim()).build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x00);

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message message = new Message();
                message.what = 0x01;
                message.obj = result;
                handler.sendMessage(message);


            }
        });
    }

    public boolean checkUpdateUI(){
        if (usernameEt.getText().toString().length()<1){
            Toast.makeText(RegistAty.this,R.string.please_input_username,Toast.LENGTH_SHORT).show();

            return false;
        }else if (emailEt.getText().toString().length()<1){
            Toast.makeText(RegistAty.this,R.string.please_input_email,Toast.LENGTH_SHORT).show();
            return false;

        }
        else if (passwordEt.getText().toString().length()<1){
            Toast.makeText(RegistAty.this,R.string.please_input_password,Toast.LENGTH_SHORT).show();
            return false;

        }else if (!passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString())){
            Toast.makeText(RegistAty.this,R.string.password_not_equal,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




}
