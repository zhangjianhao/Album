package com.zjianhao.utils;

/**
 * Created by 张建浩（Clarence) on 2016-6-28 20:41.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class DateUtil {


    public static boolean isDate(String keyword) {
        if (keyword.matches("\\d{4}年"))
            return true;
        if (keyword.matches("\\d{2}月") || keyword.matches("\\d月"))
            return true;
        if (keyword.matches("\\d{2}日") || keyword.matches("\\d日"))
            return true;
        if (keyword.matches("\\d{4}-\\d{1,2}") || keyword.matches("\\d{4}年\\d{1,2}月"))
            return true;
        if (keyword.matches("\\d{1,2}-\\d{1,2}") || keyword.matches("\\d{1,2}月\\d{1,2}日*"))
            return true;

        if (keyword.matches("\\d{4}-\\d{1,2}-\\d{1,2}") ||
                keyword.matches("\\d{4}年\\d{1,2}月\\d{1,2}日*"))
            return true;
        return false;
    }


    public static String parseToDate(String keyword){
        keyword = keyword.replace("年","-").replace("月","-").replace("日","");
        String[] split = keyword.split("-");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {

            if (Integer.parseInt(split[i]) < 10){
                if (i== split.length-1)
                    builder.append("0"+split[i]);
                else
                    builder.append("0"+split[i]+"-");

            }
            else {
                if (i== split.length-1)
                    builder.append(split[i]);
                else builder.append(split[i]+"-");
            }
        }
        return builder.toString();

    }


}
