package com.rssread.www.Application;

import android.app.Application;
import android.content.ClipboardManager;
import android.content.Intent;

import com.rssread.www.Service.CopyService;
import com.rssread.www.Util.PopMessageUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by yy on 2018/3/13.
 */
public class BaseApplication  extends Application {
    public static BaseApplication instance;
    public static ClipboardManager clipboardManager;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        X5Init();
//        InitCopyService();
    }

    private void InitCopyService() {
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        Intent startIntent = new Intent(this, CopyService.class);
        startService(startIntent);
    }

    private void X5Init(){
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                PopMessageUtil.Log("X5内核 onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                PopMessageUtil.Log("X5内核 onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                PopMessageUtil.Log("X5内核 onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                PopMessageUtil.Log("X5内核 onDownloadProgress:" + i);
            }
        });
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
    public static BaseApplication getInstance(){
        return instance;
    }
}
