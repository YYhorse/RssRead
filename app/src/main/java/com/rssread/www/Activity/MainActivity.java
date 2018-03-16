package com.rssread.www.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rssread.www.Adapter.Function.BlogListAdapter;
import com.rssread.www.Adapter.Function.MyBlogListAdapter;
import com.rssread.www.Adapter.Item.BlogItem;
import com.rssread.www.Adapter.Item.MyBlogItem;
import com.rssread.www.R;
import com.rssread.www.Util.BlogListJson;
import com.rssread.www.Util.ColorUtils;
import com.rssread.www.Util.MyBlogListJson;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.Util.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends Activity {
    private ImageView Bloglist_image, Myblog_image;
    private TextView Bloglist_txt, Myblog_txt;

    private BlogListAdapter blogListAdapter;
    private List<BlogItem> blogItemList;
    private ListView blogList_listview;
    private BlogListJson blogListJson;                                      //博客大全  本地存储格式


    private MyBlogListAdapter myBlogListAdapter;
    private List<MyBlogItem> myBlogItemList;
    private ListView myblogList_listview;
    private MyBlogListJson myBlogListJson;                                  //我订阅博客 本地存储格式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitUi();
        GetSaveBlogList();
    }

    private void InitUi() {
        Bloglist_image = (ImageView) findViewById(R.id.Main_Bloglist_image);
        Myblog_image = (ImageView) findViewById(R.id.Main_Myblog_image);
        Bloglist_txt = (TextView) findViewById(R.id.Main_Bloglist_txt);
        Myblog_txt = (TextView) findViewById(R.id.Main_Myblog_txt);

        blogList_listview = (ListView) findViewById(R.id.Main_BlogList_listView);
        blogItemList = new ArrayList<BlogItem>();
        blogListAdapter = new BlogListAdapter(this, blogItemList);
        blogList_listview.setAdapter(blogListAdapter);

        myblogList_listview = (ListView) findViewById(R.id.Main_MyBlogList_listView);
        myBlogItemList = new ArrayList<MyBlogItem>();
        myBlogListAdapter = new MyBlogListAdapter(this,myBlogItemList);
        myblogList_listview.setAdapter(myBlogListAdapter);


        blogListJson = new BlogListJson();
        myBlogListJson = new MyBlogListJson();
    }

    /******************************************************************************************
     * * 功能说明：获取用户存储的博客信息
     ******************************************************************************************/
    private void GetSaveBlogList() {
        Gson gson = new Gson();
        PopMessageUtil.Log("博客大全信息="+PrefUtils.getMemoryBlogString("BlogListInfo"));
        PopMessageUtil.Log("我的博客信息="+PrefUtils.getMemoryMyBlogString("MyBlogListInfo"));
        if (PrefUtils.getMemoryBlogString("BlogListInfo").compareTo("")!=0) {
            blogListJson = gson.fromJson(PrefUtils.getMemoryBlogString("BlogListInfo"), BlogListJson.class);
            blogItemList = blogListJson.getBlogItemList();
            blogListAdapter.UpdataBlogInfo(blogItemList);
        }
        if (PrefUtils.getMemoryMyBlogString("MyBlogListInfo").compareTo("")!=0){
            myBlogListJson = gson.fromJson(PrefUtils.getMemoryMyBlogString("MyBlogListInfo"),MyBlogListJson.class);
            myBlogItemList = myBlogListJson.getMyBlogItems();
            myBlogListAdapter.UpdataBlogInfo(myBlogItemList);
        }
    }

    /******************************************************************************************
     * * 功能说明：点击博客大全
     ******************************************************************************************/
    public void ClickBlogListMethod(View view) {
        Bloglist_image.setImageResource(R.drawable.boke_on);
        Bloglist_txt.setTextColor(ColorUtils.getColor(R.color.red_dark));
        Myblog_image.setImageResource(R.drawable.my_off);
        Myblog_txt.setTextColor(ColorUtils.getColor(R.color.gray));
        blogList_listview.setVisibility(View.VISIBLE);
        myblogList_listview.setVisibility(View.GONE);
    }

    /******************************************************************************************
     * * 功能说明：点击我的订阅
     ******************************************************************************************/
    public void ClickMyBlogMethod(View view) {
        Bloglist_image.setImageResource(R.drawable.boke_off);
        Bloglist_txt.setTextColor(ColorUtils.getColor(R.color.gray));
        Myblog_image.setImageResource(R.drawable.my_on);
        Myblog_txt.setTextColor(ColorUtils.getColor(R.color.red_dark));
        blogList_listview.setVisibility(View.GONE);
        myblogList_listview.setVisibility(View.VISIBLE);
    }

    /******************************************************************************************
     * * 功能说明：新增博客
     ******************************************************************************************/
    public void ClickAddBlogMethod(View view) {
        final AlertDialog myDialog = new AlertDialog.Builder(this,R.style.dialog).create();
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(true);
        myDialog.getWindow().setContentView(R.layout.dialog_addblog);
        final EditText Name_etxt = (EditText) myDialog.getWindow().findViewById(R.id.Dialog_BlogName_etxt);
        final EditText Url_etxt = (EditText) myDialog.getWindow().findViewById(R.id.Dialog_BlogUrl_etxt);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        myDialog.getWindow().findViewById(R.id.Dialog_Blog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name_etxt.getText().length() != 0 && Url_etxt.getText().length() != 0) {
                    BlogItem bi = new BlogItem();
                    bi.setTitle(Name_etxt.getText().toString());
                    bi.setUrl(Url_etxt.getText().toString());
                    blogItemList.add(bi);
                    SaveBlogListMethod();
                    myDialog.dismiss();
                }
                else
                    PopMessageUtil.showToastShort("请填完成信息");
            }
        });
    }

    private SweetAlertDialog PopWindowsDialog;
    public int DelItemPostion = 0;
    public void DelBlogInfoMessage(String title,String context){
        if (PopWindowsDialog == null)
            PopWindowsDialog = new SweetAlertDialog(MainActivity.this,1);
        PopWindowsDialog.setTitleText(title)
                .setContentText(context)
                .setConfirmText("删除")
                .setCancelText("取消")
                .setConfirmClickListener(new ClickSweetAlertDelMethod())
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                        PopWindowsDialog = null;
                    }
                })
                .changeAlertType(1);
        PopWindowsDialog.setCanceledOnTouchOutside(true);
        PopWindowsDialog.show();
    }
    class ClickSweetAlertDelMethod implements SweetAlertDialog.OnSweetClickListener{
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.cancel();
            PopWindowsDialog = null;
            //-----删除item----//
            blogItemList.remove(DelItemPostion);
            SaveBlogListMethod();
        }
    }

    public void SaveBlogListMethod(){
        //------存储信息-------//
        blogListJson.setBlogItemList(blogItemList);
        Gson gson = new Gson();
        PrefUtils.setMemoryString("BlogListInfo", gson.toJson(blogListJson));
        //------刷新显示-------//
        blogListAdapter.notifyDataSetChanged();
    }
}
