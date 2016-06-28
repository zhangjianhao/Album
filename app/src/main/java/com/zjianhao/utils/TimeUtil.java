package com.zjianhao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 张建浩（Clarence) on 2016-6-24 20:34.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class TimeUtil {
    public static String parseIntDate(long millions){
        Date date = new Date(millions);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static String parseLongToString(long millions){
        Date date = new Date(millions);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(date);
    }

    public static String parseLongToWeek(long millions){
        Date date = new Date(millions);
        SimpleDateFormat format = new SimpleDateFormat("E");
        return format.format(date);
    }
}
