package com.zjianhao.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 张建浩（Clarence) on 2016-5-10 14:14.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class ToastUtil {
    public static void show(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
