package com.rssread.www.Util;

/**
 * Created by Administrator on 2017-10-21.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.rssread.www.Application.BaseApplication;

/**
 * Created by Administrator on 2015/10/21.
 */
public class PrefUtils {
    private static final String TAG = PrefUtils.class.getSimpleName();
    private static final String NAME = "config";
    private static SharedPreferences sp=null;

    //------------使用------------------//
    public static String getMemoryString(String key){ return getString(BaseApplication.getInstance(),key,""); }
    public static String getMemoryBlogString(String key) {return getString(BaseApplication.getInstance(),key,"{\"blogItemList\":[{\"Title\":\"新浪博客\",\"Url\":\"https://sina.cn/?vt=4\"},{\"Title\":\"博客园\",\"Url\":\"https://www.cnblogs.com\"},{\"Title\":\"网易博客\",\"Url\":\"http://blog.163.com/\"},{\"Title\":\"博客中国\",\"Url\":\"http://fengbaohua.blogchina.com/\"}]}");}
    public static String getMemoryMyBlogString(String key) {return getString(BaseApplication.getInstance(),key,"{\"MyblogList\":[{\"blogCategroy\":\"技术文章\",\"blogInfo\":[{\"Title\":\"腾讯X5浏览服务\",\"Url\":\"http://x5.tencent.com/\"}]},{\"blogCategroy\":\"Blog主\",\"blogInfo\":[]},{\"blogCategroy\":\"明星消息\",\"blogInfo\":[]},{\"blogCategroy\":\"体坛风云\",\"blogInfo\":[]},{\"blogCategroy\":\"奇闻怪事\",\"blogInfo\":[]}]}\n");}
    public static void setMemoryString(String key,String value) {   putString(BaseApplication.getInstance(),key,value); }

    public static Boolean getMemoryBoolean(String key) { return getBoolean(BaseApplication.getInstance(),key,true);}
    public static void setMemoryBoolean(String key,Boolean value) { putBoolean(BaseApplication.getInstance(),key,value);}

    public static int getMemoryInt(String key) { return getInt(BaseApplication.getInstance(),key,0);}
    public static void setMemoryInt(String key,int value) { putInt(BaseApplication.getInstance(),key,value);}
    //---------------------------------//
    public static void putInt(Context context, String key, int value){
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
    public static int getInt(Context context, String key, int defaultValue){
        sp=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void putString(Context context, String key, String value){
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
    public static String getString(Context context, String key, String defaultValue){
        sp=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value){
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue){
        sp=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

}