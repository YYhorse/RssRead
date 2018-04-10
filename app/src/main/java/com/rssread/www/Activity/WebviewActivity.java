package com.rssread.www.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rssread.www.Adapter.Item.BlogItem;
import com.rssread.www.Adapter.Item.MyBlogItem;
import com.rssread.www.R;
import com.rssread.www.Util.H5WebviewUtils;
import com.rssread.www.SaveUtil.MyBlogListJson;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.SaveUtil.PrefUtils;
import com.rssread.www.Util.SildingFinishLayout;
import com.rssread.www.Util.SwitchUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

/**
 * Created by yy-surface on 2018/3/14.
 */
public class WebviewActivity extends Activity {
    private com.tencent.smtt.sdk.WebView webView;
    private TextView Title_txt;
    private RelativeLayout Showloading_layout;
    private ImageView add_image;

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
        add_image = (ImageView) findViewById(R.id.Webview_Add_image);
    }

    /**********************************************************************************************
     * * 功能说明：侧滑栏驱动
     *********************************************************************************************/
    private void InitSildingFinishLayout() {
        SildingFinishLayout main_sildinglayout = (SildingFinishLayout) findViewById(R.id.main_Slidelayout);
        LinearLayout fire_sildinglayout = (LinearLayout) findViewById(R.id.fire_Slidelayout);
        fire_sildinglayout.bringToFront();
        main_sildinglayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
            @Override
            public void onSildingFinish() {
                SwitchUtil.switchActivity(WebviewActivity.this, MainActivity.class).switchToFinishWithValue(RESULT_CANCELED);
            }
        });
        main_sildinglayout.setTouchView(fire_sildinglayout);
    }

    /**********************************************************************************************
     * * 功能说明：键盘返回按钮监听 事件处理
     *********************************************************************************************/
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

    /**********************************************************************************************
     * * 功能说明：返回按钮事件监听
     *********************************************************************************************/
    public void ClickWebviewBackMethod(View view) {
        if (webView.canGoBack())
            webView.goBack();
        else
            SwitchUtil.switchActivity(WebviewActivity.this, MainActivity.class).switchToFinishWithValue(RESULT_CANCELED);
    }

    /**********************************************************************************************
     * * 功能说明：加载网页
     *********************************************************************************************/
    private void LoadWebview(String url) {
        webView.setVisibility(View.GONE);
        Showloading_layout.setVisibility(View.VISIBLE);
        webView.loadUrl(url);
    }

    /**********************************************************************************************
     * * 功能说明：获取页面跳转携带值
     *********************************************************************************************/
    private void GetIntent() {
        Intent intent = this.getIntent();
        Title_txt.setText(intent.getStringExtra("title"));
        LoadWebview(intent.getStringExtra("url"));
        if(intent.getBooleanExtra("type", false)==false)
            add_image.setVisibility(View.GONE);
    }

    /**********************************************************************************************
     * * 功能说明：webview事件处理
     *********************************************************************************************/
    private void WebviewDealWork() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                PopMessageUtil.Log("Web内部加载=" + url);
                webView.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
            }

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

    private MyBlogListJson myBlogListJson;
    public void ClickSaveUrlMethod(View view) {
        PopMessageUtil.Log("点击收藏网页:" + webView.getTitle() + "|" + webView.getUrl());
        Gson gson = new Gson();
        myBlogListJson = gson.fromJson(PrefUtils.getMemoryMyBlogString("MyBlogListInfo"), MyBlogListJson.class);
        ShowAddBlogDialog();
    }

    private void ShowAddBlogDialog() {
        final AlertDialog myDialog = new AlertDialog.Builder(this, R.style.dialog).create();
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(true);
        myDialog.getWindow().setContentView(R.layout.dialog_addmyblog);
        final EditText Name_etxt = (EditText) myDialog.getWindow().findViewById(R.id.Dialog_MyBlogName_etxt);
        final Spinner MyBlog_spinner = (Spinner) myDialog.getWindow().findViewById(R.id.MyBlog_spinner);
        Name_etxt.setText(webView.getTitle());
        //-----加载类别-----//
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < myBlogListJson.getMyBlogItems().size(); i++) {
            list.add(myBlogListJson.getMyBlogItems().get(i).getBlogCategroy());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MyBlog_spinner.setAdapter(adapter);
        //------加载完毕-----//
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        myDialog.getWindow().findViewById(R.id.Dialog_MyBlog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----存储订阅博客---//
                BlogItem bi = new BlogItem();
                bi.setTitle(Name_etxt.getText().toString());
                bi.setUrl(webView.getUrl());
                myBlogListJson.getMyBlogItems().get(MyBlog_spinner.getSelectedItemPosition()).getBlogInfo().add(bi);
                //-----数据存储-----//
                Gson gson = new Gson();
                PrefUtils.setMemoryString("MyBlogListInfo", gson.toJson(myBlogListJson));
                PopMessageUtil.showToastShort("订阅成功!");
                //-----页面跳转-----//
                SwitchUtil.switchActivity(WebviewActivity.this,MainActivity.class).switchToFinishWithValue(RESULT_OK);
            }
        });
    }

}
