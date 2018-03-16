package com.rssread.www.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rssread.www.R;
import com.rssread.www.Util.H5WebviewUtils;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.Util.SildingFinishLayout;
import com.rssread.www.Util.SwitchUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by yy-surface on 2018/3/14.
 */
public class WebviewActivity extends Activity{
    private com.tencent.smtt.sdk.WebView webView;
    private TextView Title_txt;
    private RelativeLayout Showloading_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        InitUi();
        H5WebviewUtils.InitH5WebViewSettingMethod(WebviewActivity.this, webView);
        InitSildingFinishLayout();
        WebviewDealWork();
        GetIntent();
    }

    private void InitUi() {
        webView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.ShomeWebview);
        Title_txt = (TextView) findViewById(R.id.Webview_Title_txt);
        Showloading_layout = (RelativeLayout) findViewById(R.id.Showloading_layout);
    }

    private void InitSildingFinishLayout() {
        SildingFinishLayout main_sildinglayout = (SildingFinishLayout)findViewById(R.id.main_Slidelayout);
        LinearLayout fire_sildinglayout = (LinearLayout)findViewById(R.id.fire_Slidelayout);
        fire_sildinglayout.bringToFront();
        main_sildinglayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                SwitchUtil.switchActivity(WebviewActivity.this, MainActivity.class).switchToFinishWithValue(RESULT_CANCELED);
            }
        });
        main_sildinglayout.setTouchView(fire_sildinglayout);
    }

    public void ClickWebviewBackMethod(View view){
        SwitchUtil.switchActivity(WebviewActivity.this, MainActivity.class).switchToFinishWithValue(RESULT_CANCELED);
    }

    private void LoadWebview(String url) {
        webView.setVisibility(View.GONE);
        Showloading_layout.setVisibility(View.VISIBLE);
        webView.loadUrl(url);
    }

    private void GetIntent() {
        Intent intent = this.getIntent();
        Title_txt.setText(intent.getStringExtra("title"));
        LoadWebview(intent.getStringExtra("url"));
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
                    Showloading_layout.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void ClickSaveUrlMethod(View view){
        PopMessageUtil.Log("点击收藏网页:"+webView.getTitle()+"|"+webView.getUrl());

    }

}
