package com.zjianhao.presenter;

import com.zjianhao.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张建浩（Clarence) on 2016-6-27 12:07.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class LocationPresenter {

    public interface OnTranSuccessListener{
        public void onTranSuccess(String addr);
    }
    public void getAddress(double latitude, double longitude, final OnTranSuccessListener listener) {

        LogUtil.isDebug(true);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.map.baidu.com/geocoder/v2/?ak=F4T8ucQkc9l7C10PLW38VtSM&callback=renderReverse&location=" + latitude + "," + longitude + "&output=json&pois=1&coordtype=wgs84ll&mcode=73:5B:FC:73:85:F9:C4:05:21:49:82:87:21:61:5E:BF:87:FD:42:17;com.zjianhao.baiddupoi").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onTranSuccess(null);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                String addr = parseJson(string.substring(string.indexOf("(")+1,string.length()-1));
                listener.onTranSuccess(addr);

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
