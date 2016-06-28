package com.zjianhao.album;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zjianhao.adapter.MainTabAdapter;
import com.zjianhao.fragments.AlbumFragment;
import com.zjianhao.fragments.PhotoFragment;
import com.zjianhao.ui.AdvancedSearchAty;
import com.zjianhao.ui.AmbigiousSearchAty;
import com.zjianhao.ui.LocalAlbum;
import com.zjianhao.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener{

    @InjectView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @InjectView(R.id.main_tab_layout)
    TabLayout mainTabLayout;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.main_content)
    CoordinatorLayout mainContent;
    @InjectView(R.id.navigation_view)
    NavigationView navigationView;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.take_photo_fb)
    FloatingActionButton takePhotoFb;
    private MainTabAdapter adapter;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private PhotoFragment photoFragment;
    private AlbumFragment albumFragment;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private MediaScannerConnection msc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainToolbar.setTitle(getString(R.string.album));
        mainToolbar.setTitleTextColor(Color.WHITE);
        mainToolbar.setNavigationIcon(R.drawable.ic_list_white);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionBarDrawerToggle.syncState();
            }
        });






        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);
        init();
        adapter = new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
        viewpager.setAdapter(adapter);
        mainTabLayout.setupWithViewPager(viewpager);
        mainTabLayout.setTabMode(TabLayout.MODE_FIXED);

        navigationView.inflateHeaderView(R.layout.drawer_head_view);
        navigationView.inflateMenu(R.menu.drawable_home_main);
        navigationView.setNavigationItemSelectedListener(this);

    }


    public void init() {
        titles.add(getString(R.string.photo));
        titles.add(getString(R.string.album));
        photoFragment = new PhotoFragment();
        fragments.add(photoFragment);
        albumFragment = new AlbumFragment();
        fragments.add(albumFragment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_menu:
                Intent intent = new Intent(this, AmbigiousSearchAty.class);
                startActivity(intent);

                break;
            case R.id.share_menu:
                ToastUtil.show(this,"search");
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.take_photo_fb)
    public void onClick() {
        takePicture();

    }

    public void takePicture(){
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == Activity.RESULT_OK){
                   savePicture(data);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void savePicture(Intent data) {
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        final File filepath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/album/");
        if (!filepath.exists())
            filepath.mkdirs();
        final File file = new File(filepath,getDateImgName());
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        msc = new MediaScannerConnection(this, new MediaScannerConnection.MediaScannerConnectionClient() {
            @Override
            public void onMediaScannerConnected() {
                msc.scanFile(filepath.getAbsolutePath(),"image/jpeg");

            }

            @Override
            public void onScanCompleted(String path, Uri uri) {

            }
        });
        msc.connect();

    }


    public String getDateImgName(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss");
        return "IMG_"+format.format(new Date(System.currentTimeMillis()))+".jpg";

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (msc != null && msc.isConnected()){
            msc.disconnect();
            msc = null;

        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_upload_photo:
                 intent = new Intent(this, LocalAlbum.class);
                startActivity(intent);

                break;
            case R.id.nav_search_photo:
                intent = new Intent(this, AdvancedSearchAty.class);
                startActivity(intent);

                break;
        }

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
