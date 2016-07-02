package com.zjianhao.utils;

import com.zjianhao.bean.Album;
import com.zjianhao.bean.GridPhoto;
import com.zjianhao.bean.Photo;
import com.zjianhao.bean.User;
import com.zjianhao.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张建浩（Clarence) on 2016-6-30 20:41.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class JsonUtil {
    public static User parseToUser(JSONObject object){
        User user = new User();
        try {
            user.setId(object.getInt("id"));
            user.setUsername(object.getString("username"));
            user.setEmail(object.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<GridPhoto> passeToList(JSONArray array) throws JSONException {
        Album album = new Album("name",0,null);
        Map<String,List<Photo>> photoMap = new HashMap<>();
        ArrayList<Photo> list = new ArrayList<>();
        Photo photo;
        for (int i = 0; i < array.length(); i++) {
            JSONObject p = array.getJSONObject(i);
            photo = new Photo();
            photo.setImgUrl(Constants.PROJECT_URL+p.getString("photoUrl"));
            photo.setName(p.getString("photoName"));
            photo.setDate(p.getLong("date"));
            String dateStr = "上传时间:"+TimeUtil.parseIntDate(photo.getDate());
            if (photoMap.containsKey(dateStr)){//包含此时间戳的图片则继续添加
                photoMap.get(dateStr).add(photo);
            }else {//创建新的图片时间戳键值对
                ArrayList<Photo> photos = new ArrayList<>();
                photos.add(photo);
                photoMap.put(dateStr,photos);
            }

        }
        album.setPhotoMap(photoMap);
        return album.mapToList();
    }
}
