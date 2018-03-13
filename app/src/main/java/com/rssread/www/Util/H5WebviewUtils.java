package com.rssread.www.Util;

import android.app.Activity;
import android.os.Build;

import com.rssread.www.Application.BaseApplication;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;

/**
 * Created by yy on 2018/2/27.
 */
public class H5WebviewUtils {
    /*********************************************************************************************************
     * * 功能说明：登录界面加载  腾讯浏览器内核初始化
     ********************************************************************************************************/
    public static void InitH5WebViewSettingMethod(Activity activity,com.tencent.smtt.sdk.WebView webview) {
        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowUniversalAccessFromFileURLs(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
//        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
//        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(activity.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(activity.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(BaseApplication.getInstance().getDir("geolocation", 0).getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setLoadsImagesAutomatically(true);    //支持自动加载图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(2);
        }
        CookieSyncManager.createInstance(activity);
        CookieSyncManager.getInstance().sync();
        //----------------配置userAgentString-------------------//
        String userAgentStr = webSetting.getUserAgentString();
        userAgentStr = userAgentStr + "Android";
        webSetting.setUserAgentString(userAgentStr);
    }
}
