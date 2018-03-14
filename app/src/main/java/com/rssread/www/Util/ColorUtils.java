package com.rssread.www.Util;

import android.support.v4.content.ContextCompat;

import com.rssread.www.Application.BaseApplication;


/**
 * Created by yy on 2018/2/27.
 */
public class ColorUtils {
    //colorUtils.getColor(R.color.green)
    public static int getColor(int color){
        return ContextCompat.getColor(BaseApplication.getInstance(), color);
    }
}
