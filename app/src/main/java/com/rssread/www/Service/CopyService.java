package com.rssread.www.Service;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.rssread.www.Application.BaseApplication;
import com.rssread.www.Util.PopMessageUtil;

/**
 * Created by yy on 2018/3/13.
 */
public class CopyService extends Service{
//    Handler CopyCycleQuery_handler = new Handler();
//    Runnable CopyCycleQuery_runnable = new Runnable() {
//        @Override
//        public void run() {
//            CopyCycleQuery_handler.postDelayed(CopyCycleQuery_runnable, 3000);
//            if(BaseApplication.clipboardManager !=null && BaseApplication.clipboardManager.hasText()){
//                CharSequence tmpText = BaseApplication.clipboardManager.getText();
//                if (tmpText != null && tmpText.length() > 0) {
//                    PopMessageUtil.Log(tmpText.toString().trim());
//                }
//            }
//        }
//    };
    @Override
    public void onCreate() {
        super.onCreate();
        PopMessageUtil.Log("onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PopMessageUtil.Log("开始监听服务");
        RegisterClipEvents();
//        CopyCycleQuery_handler.postDelayed(CopyCycleQuery_runnable, 0);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PopMessageUtil.Log("onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean yes = false;
    private void RegisterClipEvents() {
        BaseApplication.clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if(yes == false) {
                    yes = true;
                    Log.e("YY", "有变化:" + BaseApplication.clipboardManager.getPrimaryClip().getItemAt(0).getText());
                }
            }
        });
    }
}
