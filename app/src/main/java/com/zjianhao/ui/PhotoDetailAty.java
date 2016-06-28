package com.zjianhao.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zjianhao.album.R;
import com.zjianhao.bean.Photo;
import com.zjianhao.view.TouchImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张建浩（Clarence) on 2016-6-25 20:59.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class PhotoDetailAty extends Activity {
    @InjectView(R.id.photo_detail_back)
    ImageView photoDetailBack;
    @InjectView(R.id.photo_detail_iv)
    TouchImageView photoDetailIv;
    @InjectView(R.id.photo_name)
    TextView photoName;
    @InjectView(R.id.photo_send_ll)
    LinearLayout photoSendLl;
    @InjectView(R.id.photo_delete_ll)
    LinearLayout photoDeleteLl;
    @InjectView(R.id.photo_detail_ll)
    LinearLayout photoDetailLl;
    private Photo photo;

    private ImageLoader imageloader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail_main);
        ButterKnife.inject(this);
        imageloader = ImageLoader.getInstance();
        photo = (Photo) getIntent().getParcelableExtra("photo");

        imageloader.displayImage("file://" + photo.getImgUrl(), photoDetailIv);
        photoName.setText(photo.getName());
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(0, R.anim.activity_exit_anim);
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.photo_send_ll, R.id.photo_delete_ll, R.id.photo_detail_ll,R.id.photo_detail_back, R.id.photo_detail_iv})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.photo_detail_back:
                finish();
                overridePendingTransition(0, R.anim.activity_exit_anim);
                break;

            case R.id.photo_send_ll:

                break;
         
            case R.id.photo_detail_ll:
                Intent intent = new Intent(this,PhotoInfoAty.class);
                intent.putExtra("photo",photo);
                startActivity(intent);

                break;
        }
    }
}
