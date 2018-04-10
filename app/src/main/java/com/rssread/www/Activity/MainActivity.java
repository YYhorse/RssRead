package com.rssread.www.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rssread.www.Adapter.Function.BlogListAdapter;
import com.rssread.www.Adapter.Function.MyBlogListAdapter;
import com.rssread.www.Adapter.Item.BlogItem;
import com.rssread.www.Adapter.Item.MyBlogItem;
import com.rssread.www.Adapter.Item.MyBlogShowItem;
import com.rssread.www.R;
import com.rssread.www.SaveUtil.BlogListJson;
import com.rssread.www.Util.ColorUtils;
import com.rssread.www.SaveUtil.MyBlogListJson;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.SaveUtil.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends Activity {
    private ImageView Bloglist_image, Myblog_image;
    private TextView Bloglist_txt, Myblog_txt;
    private int NowPostion = 0;                                             //0->博客大全  1订阅博客
    private BlogListAdapter blogListAdapter;
    private List<BlogItem> blogItemList;
    private ListView blogList_listview;
    private BlogListJson blogListJson;                                      //博客大全  本地存储格式

    private MyBlogListAdapter myBlogListAdapter;
    private List<MyBlogItem> myBlogItemList;
    private ListView myblogList_listview;
    private MyBlogListJson myBlogListJson;                                  //我订阅博客 本地存储格式
    private List<MyBlogShowItem> myBlogShowList;                            //我订阅博客 显示展示格式

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
        myBlogShowList = new ArrayList<MyBlogShowItem>();
        myBlogListAdapter = new MyBlogListAdapter(this,myBlogShowList);
        myblogList_listview.setAdapter(myBlogListAdapter);


        blogListJson = new BlogListJson();
        myBlogListJson = new MyBlogListJson();
        myBlogItemList = new ArrayList<MyBlogItem>();
    }

    /******************************************************************************************
     * * 功能说明：获取用户存储的博客信息
     ******************************************************************************************/
    private void GetSaveBlogList() {
        Gson gson = new Gson();
        PopMessageUtil.Log("博客大全信息="+PrefUtils.getMemoryBlogString("BlogListInfo"));
        PopMessageUtil.Log("我的博客信息=" + PrefUtils.getMemoryMyBlogString("MyBlogListInfo"));
        if (PrefUtils.getMemoryBlogString("BlogListInfo").compareTo("")!=0) {
            blogListJson = gson.fromJson(PrefUtils.getMemoryBlogString("BlogListInfo"), BlogListJson.class);
            blogItemList = blogListJson.getBlogItemList();
            blogListAdapter.UpdataBlogInfo(blogItemList);
        }
        if (PrefUtils.getMemoryMyBlogString("MyBlogListInfo").compareTo("")!=0){
            myBlogListJson = gson.fromJson(PrefUtils.getMemoryMyBlogString("MyBlogListInfo"), MyBlogListJson.class);
            myBlogItemList = myBlogListJson.getMyBlogItems();

            ChangeMyBlogShowList();
        }
    }

    /**********************************************************************************************
     * * 功能说明：订阅博客 格式化显示listview
     *********************************************************************************************/
    private void ChangeMyBlogShowList(){
        myBlogShowList.clear();
        for(int i=0;i<myBlogItemList.size();i++){
            MyBlogShowItem mbsi = new MyBlogShowItem();
            mbsi.setTitle(myBlogItemList.get(i).getBlogCategroy());
            mbsi.setUrl("");
            mbsi.setType("title");
            myBlogShowList.add(mbsi);
            for(int j=0;j<myBlogItemList.get(i).getBlogInfo().size();j++){
                MyBlogShowItem mbsi2 = new MyBlogShowItem();
                mbsi2.setTitle(myBlogItemList.get(i).getBlogInfo().get(j).getTitle());
                mbsi2.setUrl(myBlogItemList.get(i).getBlogInfo().get(j).getUrl());
                mbsi2.setType("context");
                myBlogShowList.add(mbsi2);
            }
        }
        myBlogListAdapter.UpdataBlogInfo(myBlogShowList);
    }

    /******************************************************************************************
     * * 功能说明：点击博客大全
     ******************************************************************************************/
    public void ClickBlogListMethod(View view) {
        if(NowPostion !=0 ) {
            NowPostion = 0;
            Bloglist_image.setImageResource(R.drawable.boke_on);
            Bloglist_txt.setTextColor(ColorUtils.getColor(R.color.red_dark));
            Myblog_image.setImageResource(R.drawable.my_off);
            Myblog_txt.setTextColor(ColorUtils.getColor(R.color.gray));
            blogList_listview.setVisibility(View.VISIBLE);
            blogList_listview.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_left));
            myblogList_listview.setVisibility(View.GONE);
            myblogList_listview.setAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_right));
        }
    }

    /******************************************************************************************
     * * 功能说明：点击我的订阅
     ******************************************************************************************/
    public void ClickMyBlogMethod(View view) {
        if(NowPostion !=1 ) {
            NowPostion = 1;
            Bloglist_image.setImageResource(R.drawable.boke_off);
            Bloglist_txt.setTextColor(ColorUtils.getColor(R.color.gray));
            Myblog_image.setImageResource(R.drawable.my_on);
            Myblog_txt.setTextColor(ColorUtils.getColor(R.color.red_dark));
            blogList_listview.setVisibility(View.GONE);
            blogList_listview.setAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_left));
            myblogList_listview.setVisibility(View.VISIBLE);
            myblogList_listview.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_right));
        }
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
        final TextView Title_txt = (TextView) myDialog.getWindow().findViewById(R.id.Dialog_Title_txt);
        final TextView Name_txt = (TextView) myDialog.getWindow().findViewById(R.id.Dialog_BlogName_txt);
        final TextView Url_txt  = (TextView) myDialog.getWindow().findViewById(R.id.Dialog_BlogUrl_txt);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        if(NowPostion==1){
            Url_etxt.setVisibility(View.GONE);
            Url_txt.setVisibility(View.GONE);
            Title_txt.setText("新增订阅类型");
            Name_txt.setText("订阅类型:");
        }
        myDialog.getWindow().findViewById(R.id.Dialog_Blog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name_etxt.getText().length() != 0) {
                    if(NowPostion==0 && Url_etxt.getText().length() != 0) {
                        //新增博客大全
                        BlogItem bi = new BlogItem();
                        bi.setTitle(Name_etxt.getText().toString());
                        bi.setUrl(Url_etxt.getText().toString());
                        blogItemList.add(bi);
                        SaveBlogListMethod();
                        myDialog.dismiss();
                    }
                    else{
                        //新增订阅类型
                        MyBlogItem mbi = new MyBlogItem();
                        List<BlogItem> bi = new ArrayList<BlogItem>();
                        mbi.setBlogCategroy(Name_etxt.getText().toString());
                        mbi.setBlogInfo(bi);
                        myBlogItemList.add(mbi);
                        SaveMyBlogListMethod();
                        ChangeMyBlogShowList();
                        myDialog.dismiss();
                    }
                }
                else
                    PopMessageUtil.showToastShort("请填完成信息");
            }
        });
    }

    private SweetAlertDialog PopWindowsDialog;
    public int DelItemPostion = 0;
    public int DelItemType = 0;     //0 博客大全  1订阅博客
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
            if(DelItemType == 0) {
                blogItemList.remove(DelItemPostion);
                SaveBlogListMethod();
            }
            else{
                for(int i=0;i<myBlogItemList.size();i++){
                    for(int j=0;j<myBlogItemList.get(i).getBlogInfo().size();j++){
                        if(myBlogShowList.get(DelItemPostion).getUrl().compareTo(myBlogItemList.get(i).getBlogInfo().get(j).getUrl())==0){
                            myBlogItemList.get(i).getBlogInfo().remove(j);
                        }
                    }
                }
                SaveMyBlogListMethod();
                ChangeMyBlogShowList();
            }
        }
    }

    /**********************************************************************************************
     * * 功能说明：存储博客大全信息
     *********************************************************************************************/
    public void SaveBlogListMethod(){
        //------存储信息-------//
        blogListJson.setBlogItemList(blogItemList);
        Gson gson = new Gson();
        PrefUtils.setMemoryString("BlogListInfo", gson.toJson(blogListJson));
        //------刷新显示-------//
        blogListAdapter.notifyDataSetChanged();
    }

    /**********************************************************************************************
     * * 功能说明：存储订阅博客信息
     *********************************************************************************************/
    public void SaveMyBlogListMethod(){
        myBlogListJson.setMyBlogItems(myBlogItemList);
        Gson gson = new Gson();
        PrefUtils.setMemoryString("MyBlogListInfo", gson.toJson(myBlogListJson));
    }

    /**********************************************************************************************
     * * 功能说明：回调函数返回值
     *********************************************************************************************/
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //-----1 处理博客大全返回------//
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //用户增加了订阅信息
                GetSaveBlogList();      //刷新显示
            }
        }
    }
}
