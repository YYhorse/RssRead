package com.rssread.www.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.rssread.www.R;
import com.rssread.www.Util.H5WebviewUtils;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.Util.ViewAnimationUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wang.avi.AVLoadingIndicatorView;


public class MainActivity extends Activity{
    private com.tencent.smtt.sdk.WebView webView;
    private AVLoadingIndicatorView loadingView;
    private boolean LoadStatus = false;             //加载网页标志位
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitUi();
        H5WebviewUtils.InitH5WebViewSettingMethod(MainActivity.this, webView);
        WebviewDealWork();
        LoadWebview("http://txy.product.wityun.com/products");
    }

    private void InitUi() {
        webView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webView);
        loadingView = (AVLoadingIndicatorView) findViewById(R.id.loadingView);
        loadingView.setIndicator("BallScaleMultipleIndicator");
    }

    private void LoadWebview(String url) {
        LoadStatus = false;
        webView.loadUrl(url);
        loadingView.bringToFront();
    }

    private void WebviewDealWork() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                PopMessageUtil.Log("内部加载" + url);
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
                if (newProgress >= 80 && LoadStatus == false) {
                    LoadStatus = true;
                    ViewAnimationUtils.StartAnimation(webView);
                }
            }
        });
    }

    /********************************************************************************************
     * * 功能说明：重写onKeyDown(keyCode, event)方法 改写物理按键 返回的逻辑
     ******************************************************************************************/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();                       //返回上一页面
                return true;
            } else {
                System.exit(0);                         //退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
