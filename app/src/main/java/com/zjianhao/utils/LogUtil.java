package com.zjianhao.utils;

import android.util.Log;

/**
 * Created by 张建浩（Clarence) on 2016-5-10 15:30.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class LogUtil {
    private static boolean canDebug = true;
    public static void isDebug(boolean state){
        canDebug = state;
    }
    public static void v(String TAG,String msg){
        if (canDebug)
            Log.v(TAG, msg);
    }
    public static void v(Object o,String msg){
        if ( o == null|| msg == null)
            return;
        if (canDebug){
            String TAG = o.getClass().getName();
            Log.v(TAG, msg);
        }
    }
    public static void i(String TAG,String msg){
        if ( TAG == null|| msg == null)
            return;
        if (canDebug)
            Log.i(TAG, msg);
    }
    public static void i(Object o,String msg){
        if ( o == null|| msg == null)
            return;
        if (canDebug){
            String TAG = o.getClass().getName();
            Log.i(TAG, msg);
        }
    }
    public static void w(String TAG,String msg){
        if ( TAG == null|| msg == null)
            return;
        if (canDebug)
            Log.w(TAG, msg);
    }
    public static void w(Object o,String msg){
        if ( o == null|| msg == null)
            return;
        if (canDebug){
            String TAG = o.getClass().getName();
            Log.w(TAG, msg);
        }
    }
    public static void e(String TAG,String msg){
        if ( TAG == null|| msg == null)
            return;
        if (canDebug)
            Log.e(TAG, msg);
    }
    public static void e(Object o,String msg){
        if ( o == null|| msg == null)
            return;
        if (canDebug){
            String TAG = o.getClass().getName();
            Log.e(TAG, msg);
        }
    }
}
