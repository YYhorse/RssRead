package com.rssread.www.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rssread.www.Adapter.Function.BlogListAdapter;
import com.rssread.www.Adapter.Item.BlogItem;
import com.rssread.www.R;
import com.rssread.www.Util.ColorUtils;
import com.rssread.www.Util.H5WebviewUtils;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.Util.PrefUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity{
    private ImageView Bloglist_image,Myblog_image;
    private TextView  Bloglist_txt,Myblog_txt;
    private BlogListAdapter blogListAdapter;
    private List<BlogItem> blogItemList;
    private ListView blogList_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitUi();
    }

    private void InitUi() {
        Bloglist_image = (ImageView) findViewById(R.id.Main_Bloglist_image);
        Myblog_image   = (ImageView) findViewById(R.id.Main_Myblog_image);
        Bloglist_txt   = (TextView)  findViewById(R.id.Main_Bloglist_txt);
        Myblog_txt     = (TextView)  findViewById(R.id.Main_Myblog_txt);

        blogList_listview = (ListView) findViewById(R.id.Main_BlogList_listView);
        blogItemList = new ArrayList<BlogItem>();
        blogListAdapter = new BlogListAdapter(this,blogItemList);
        blogList_listview.setAdapter(blogListAdapter);
    }

    public void ClickBlogListMethod(View view){
        Bloglist_image.setImageResource(R.drawable.boke_on);
        Bloglist_txt.setTextColor(ColorUtils.getColor(R.color.red_dark));
        Myblog_image.setImageResource(R.drawable.my_off);
        Myblog_txt.setTextColor(ColorUtils.getColor(R.color.gray));
    }

    public void ClickMyBlogMethod(View view){
        Bloglist_image.setImageResource(R.drawable.boke_off);
        Bloglist_txt.setTextColor(ColorUtils.getColor(R.color.gray));
        Myblog_image.setImageResource(R.drawable.my_on);
        Myblog_txt.setTextColor(ColorUtils.getColor(R.color.red_dark));
    }

    /******************************************************************************************
     * * 功能说明：新增博客
     ******************************************************************************************/
    public void ClickAddBlogMethod(View view){
        final AlertDialog myDialog = new AlertDialog.Builder(this).create();
        myDialog.show();
        myDialog.getWindow().setContentView(R.layout.dialog_addblog);
        final EditText Name_etxt = (EditText) myDialog.getWindow().findViewById(R.id.Dialog_BlogName_etxt);
        final EditText Url_etxt  = (EditText) myDialog.getWindow().findViewById(R.id.Dialog_BlogUrl_etxt);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        myDialog.getWindow().findViewById(R.id.Dialog_Blog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Name_etxt.getText().length()!=0&&Url_etxt.getText().length()!=0){
                    BlogItem bi = new BlogItem();
                    bi.setTitle(Name_etxt.getText().toString());
                    bi.setUrl(Url_etxt.getText().toString());
                    blogItemList.add(bi);
                    //------存储信息-------//
                    Gson gson = new Gson();
                    PrefUtils.setMemoryString("BlogListInfo", gson.toJson(blogItemList));
                    //------刷新显示-------//
                    blogListAdapter.notifyDataSetChanged();
                    myDialog.dismiss();
                }
                else
                    PopMessageUtil.showToastShort("请填完成信息");
            }
        });
    }
}
