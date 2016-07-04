package com.zjianhao.presenter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

import com.zjianhao.album.AppContext;
import com.zjianhao.bean.Album;
import com.zjianhao.bean.Photo;
import com.zjianhao.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 16:52.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class AlbumPresenter {

    private static AlbumPresenter presenter;
    private ArrayList<OnFinishLoadAlbum> listeners = new ArrayList<>();

    private AlbumPresenter() {
    }

    public static AlbumPresenter getInstance(){
        if (presenter == null)
            presenter = new AlbumPresenter();
        return presenter;
    }

    public void addOnFinishLoadAlbumListener(OnFinishLoadAlbum listener){
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public interface OnFinishLoadAlbum{
        public void onFinishLoadAlbumListener(ArrayList<Album> albums, Album cameraPhotos);
    }
    public ArrayList<Album> getAlbums(FragmentActivity activity) {
        ArrayList<Album> albums = new ArrayList();
        Map<String,Album> map = new HashMap();
        ContentResolver resolver = activity.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID,MediaStore.Images.ImageColumns.DATA,MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN,MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.ImageColumns.LONGITUDE,MediaStore.Images.ImageColumns.LATITUDE}, null, null,  null);
        if (cursor == null || !cursor.moveToNext()) return null;
        cursor.moveToLast();
        Album album;
        Photo photo ;

        do {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            String photoName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            Long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));


            String albumUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            double latitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.ImageColumns.LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.ImageColumns.LONGITUDE));
            photo = new Photo();
            photo.setId(id);
            photo.setName(photoName);
            photo.setDate(date);
            photo.setImgUrl("file://"+albumUrl);
            photo.setLatitude(latitude);
            photo.setLongitude(longitude);
            String dateStr = TimeUtil.parseIntDate(date);

            if (map.containsKey(name)){
                Album a = map.get(name);
                a.autoIncrementSize();
                Map<String, List<Photo>> photoMap = a.getPhotoMap();
                if (photoMap.containsKey(dateStr)){//包含此时间戳的图片则继续添加
                    photoMap.get(dateStr).add(photo);
                }else {//创建新的图片时间戳键值对
                    ArrayList<Photo> photos = new ArrayList<>();
                    photos.add(photo);
                    photoMap.put(dateStr,photos);
                }
            }else {
                album = new Album(name,1,albumUrl);
                Map<String, List<Photo>> photoMap = album.getPhotoMap();
                ArrayList<Photo> photos = new ArrayList<>();
                photos.add(photo);
                photoMap.put(dateStr,photos);
                map.put(name,album);
                albums.add(album);
            }


        } while (cursor.moveToPrevious());
        cursor.close();
        AppContext application = (AppContext)activity.getApplication();
        application.setAlbums(albums);
        Album cameras = map.get("Camera");
        application.setCameraAlbum(cameras);
        for (OnFinishLoadAlbum listener : listeners) {
            listener.onFinishLoadAlbumListener(albums,cameras);
        }
        return albums;
    }




}
