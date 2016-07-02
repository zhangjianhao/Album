package com.zjianhao.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zjianhao.album.AppContext;
import com.zjianhao.album.R;
import com.zjianhao.bean.Photo;
import com.zjianhao.presenter.PhotoPresenter;
import com.zjianhao.utils.LogUtil;
import com.zjianhao.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张建浩（Clarence) on 2016-6-26 11:03.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AdvancedSearchAty extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {
    @InjectView(R.id.photo_name_key)
    EditText photoNameKey;
    @InjectView(R.id.photo_name_state)
    CheckBox photoNameState;
    @InjectView(R.id.photo_start_date)
    EditText photoStartDate;
    @InjectView(R.id.photo_date_state)
    CheckBox photoDateState;
    @InjectView(R.id.photo_end_date)
    EditText photoEndDate;
    @InjectView(R.id.middle_line)
    TextView middleLine;
    @InjectView(R.id.photo_location_state)
    CheckBox photoLocationState;
    @InjectView(R.id.photo_location_key)
    EditText photoLocationKey;
    @InjectView(R.id.search_btn)
    Button searchBtn;
    @InjectView(R.id.main_toolbar)
    Toolbar mainToolbar;
    private PhotoPresenter photoPresenter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_advance_main);
        ButterKnife.inject(this);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainToolbar.setTitle("高级搜索");
        mainToolbar.setTitleTextColor(Color.WHITE);
        mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (android.os.Build.VERSION.SDK_INT>=21)
            mainToolbar.setElevation(20);

        photoStartDate.setOnFocusChangeListener(this);
        photoEndDate.setOnFocusChangeListener(this);
        searchBtn.setOnClickListener(this);
        AppContext application = (AppContext) getApplication();
        photoPresenter = new PhotoPresenter(application.getAlbums());


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_start_date:
                LogUtil.v(this, "onfocus change:");
                showDatePicker(R.id.photo_start_date);
                break;
            case R.id.photo_end_date:
                LogUtil.v(this, "onfocus change:");
                showDatePicker(R.id.photo_end_date);
                break;
            case R.id.search_btn:
                int check = check();
                if ( check !=0){
                    if (check != -1)
                    search();
                }else {
                    ToastUtil.show(this,"请至少选择一种搜索条件");
                }

                break;
        }
    }


    private int check(){
        int checkCount = 0;
        if (photoNameState.isChecked()){
            checkCount++;
            if (TextUtils.isEmpty(photoNameKey.getText())){
                ToastUtil.show(this,"请输入照片名称");
                return -1;
            }
        }

        if (photoDateState.isChecked()){
            checkCount++;
            if (TextUtils.isEmpty(photoStartDate.getText()) || TextUtils.isEmpty(photoEndDate.getText())){
                ToastUtil.show(this,"起始日期不能全为空");
                return -1;
            }
        }

        if (photoLocationState.isChecked()){
            checkCount++;
            if (TextUtils.isEmpty(photoLocationKey.getText())){
                ToastUtil.show(this,"请输入拍摄地点");
                return -1;
            }
        }
        return checkCount;
    }

    private void search() {




        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Photo> result = new ArrayList<Photo>();
                if (photoDateState.isChecked()){
                   result =  photoPresenter.getPhotoByDateInterval(photoStartDate.getText().toString().trim(),photoEndDate.getText().toString().trim());
                    if (photoNameState.isChecked() && result.size()>0){
                        result = photoPresenter.getPhotoByName(result,photoNameKey.getText().toString().trim());
                    }
                    if (photoLocationState.isChecked() && result.size()>0)
                        result = photoPresenter.getPhotoByLocation(result,photoLocationKey.getText().toString().trim());
                }else {
                    if (photoNameState.isChecked()){
                        result = photoPresenter.getPhotoByName(photoNameKey.getText().toString().trim());
                        if (photoLocationState.isChecked() && result.size()>0)
                            result = photoPresenter.getPhotoByLocation(result,photoLocationKey.getText().toString().trim());

                    }else {
                        result = photoPresenter.getPhotoByLocation(photoLocationKey.getText().toString().trim());
                    }
                }

                for (Photo photo : result) {
                    LogUtil.e(this,photo.getName()+":"+photo.getLocation());

                }

                Intent intent = new Intent(AdvancedSearchAty.this,AmbigiousSearchAty.class);
                intent.putParcelableArrayListExtra("search_result",result);
                startActivity(intent);
            }
        }).start();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.photo_start_date:
                LogUtil.v(this, "onfocus change:" + hasFocus);
                if (hasFocus)
                    showDatePicker(R.id.photo_start_date);
                break;
            case R.id.photo_end_date:
                LogUtil.v(this, "onfocus change:" + hasFocus);
                if (hasFocus)
                    showDatePicker(R.id.photo_end_date);

                break;
        }

    }


    public void showDatePicker(final int which) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month = monthOfYear + "";
                if (monthOfYear < 10)
                    month = "0" + month;
                String day = dayOfMonth + "";
                if (dayOfMonth < 10)
                    day = "0" + day;
                if (which == R.id.photo_start_date) {
                    photoStartDate.setText(year + "-" + month + "-" + day);
                    photoEndDate.requestFocus();

                }
                if (which == R.id.photo_end_date) {
                    photoEndDate.setText(year + "-" + month + "-" + day);
                    photoLocationKey.requestFocus();

                }

            }
        }, 2016, 7, 1);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (which == R.id.photo_start_date)
                    photoStartDate.clearFocus();
                if (which == R.id.photo_end_date)
                    photoEndDate.clearFocus();
            }
        });
        dialog.show();
    }
}
