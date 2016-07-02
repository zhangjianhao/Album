package com.zjianhao.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zjianhao.album.AppContext;
import com.zjianhao.album.MainActivity;
import com.zjianhao.album.R;
import com.zjianhao.bean.User;
import com.zjianhao.constants.Constants;
import com.zjianhao.utils.JsonUtil;
import com.zjianhao.utils.LogUtil;
import com.zjianhao.utils.SharePreferenceUtils;
import com.zjianhao.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by zjianhao on 15-9-12.
 */
public class LoginAty extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginAty";
    private TextView gotoRegist;
    private TextView usernameET;
    private TextView passwordEt;
    private Button loginBtn;
    private CheckBox rememberPassword;
    private CheckBox autoLogin;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    ToastUtil.show(LoginAty.this,"请求错误，请检查网络连接");
                    break;
                case 0x01:
                    String json = (String)msg.obj;
                    int result =-1;
                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(json);
                        result  = jo.getInt("code");
                        LogUtil.v(this,"code:"+result);
                        if (result>0){
                            User user = JsonUtil.parseToUser(jo.getJSONObject("entity"));
                            AppContext application = (AppContext) getApplication();
                            application.setUser(user);
                            Intent intent = new Intent(LoginAty.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    switch (result){

                        case -1:
                            ToastUtil.show(LoginAty.this,"该用户不存在");
                            break;
                        case 0:
                            ToastUtil.show(LoginAty.this,"密码错误");
                            break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        gotoRegist = (TextView)findViewById(R.id.regist_now_tv);
        gotoRegist.setOnClickListener(this);

        usernameET = (EditText)findViewById(R.id.username_et);
        String useraccount = SharePreferenceUtils.getValue(this, "user", "useraccount");
        usernameET.setText(useraccount);
        passwordEt = (EditText)findViewById(R.id.password_et);
        passwordEt.setText(SharePreferenceUtils.getValue(this, "user", "password"));
        loginBtn = (Button)findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        rememberPassword = (CheckBox)findViewById(R.id.remember_password);
        if (useraccount != null)
            rememberPassword.setChecked(true);
        autoLogin = (CheckBox)findViewById(R.id.auto_login);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regist_now_tv:
                Intent intent = new Intent(this,RegistAty.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                String username = usernameET.getText().toString();
                String password = passwordEt.getText().toString();

                if (checkInput(username,password)){
                    if (rememberPassword.isChecked()){
                        SharePreferenceUtils.save(this, "user", "useraccount", username);
                        SharePreferenceUtils.save(this, "user", "password", password);
                    }
                    if (autoLogin.isChecked()){
                        SharePreferenceUtils.save(this, "user", "autologin", true);
                    }
                    login(username,password, Constants.LOGIN_URL);
                }
                break;

        }
    }

    public boolean checkInput(String username,String password){
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(this,R.string.username_password_cannot_empty,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void login(String username,String password,String url){
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("type","user");
//        params.add("username",username);
//        params.add("password",password);
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i,Header[] headers, byte[] bytes) {
//                String result = null;
//                Log.v(TAG,bytes.length+"");
//                try {
//                    result = new String(bytes,"utf-8");
//                    if ("500".equals(result)){
//                        Toast.makeText(LoginAty.this,R.string.login_failure,Toast.LENGTH_SHORT).show();
//                    }else {
//                        //登陆成功
//                        Log.v(TAG, result);
//                        User user = JsonUtil.parseUser(result);
//                        MyApplication application = (MyApplication) getApplication();
//                        application.setUser(user);
//                        Intent intent = new Intent(LoginAty.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                Toast.makeText(LoginAty.this,R.string.connect_failure,Toast.LENGTH_SHORT).show();
//            }
//        });


        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS).build();
        client.connectTimeoutMillis();
        FormBody body = new FormBody.Builder()
                .add("username",usernameET.getText().toString().trim())
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
}
