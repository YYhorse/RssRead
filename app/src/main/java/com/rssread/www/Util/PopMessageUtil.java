package com.rssread.www.Util;

import android.util.Log;
import android.widget.Toast;

import com.rssread.www.Application.BaseApplication;

/**
 * Created by yy on 2018/3/13.
 */
public class PopMessageUtil {
    /**********************************************************************************************
     * * 功能说明：Toast
     **********************************************************************************************/
    private static Toast mToast;

    public static void showToastLong(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    private static void showToast(String text, int time) {
        if (mToast == null)
            mToast = Toast.makeText(BaseApplication.getInstance(), text, time);
        else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**********************************************************************************************
     * * 功能说明：Log.e
     **********************************************************************************************/
    public static void Log(String context) {
        Log.e("YY", context);
    }
}
