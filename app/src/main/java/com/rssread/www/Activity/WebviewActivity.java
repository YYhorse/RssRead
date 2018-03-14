package com.rssread.www.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rssread.www.R;
import com.rssread.www.Util.H5WebviewUtils;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.Util.SildingFinishLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by yy-surface on 2018/3/14.
 */
public class WebviewActivity extends Activity{
    private com.tencent.smtt.sdk.WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        InitUi();
        H5WebviewUtils.InitH5WebViewSettingMethod(WebviewActivity.this, webView);
        InitSildingFinishLayout();
        WebviewDealWork();
    }

    private void InitUi() {
        webView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.ShomeWebview);
    }

    private void InitSildingFinishLayout() {
        SildingFinishLayout main_sildinglayout = (SildingFinishLayout)findViewById(R.id.main_Slidelayout);
        LinearLayout fire_sildinglayout = (LinearLayout)findViewById(R.id.fire_Slidelayout);
        fire_sildinglayout.bringToFront();
        main_sildinglayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                finish();
            }
        });
        main_sildinglayout.setTouchView(fire_sildinglayout);
    }

    private void WebviewDealWork() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                PopMessageUtil.Log("Web内部加载=" + url);
                webView.loadUrl(url);
                return true;
            }
            public void onPageFinished(WebView view, String url) {}
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                PopMessageUtil.Log("网页加载失败");
            }
        });
        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 80) {

                }
            }
        });
    }
}
