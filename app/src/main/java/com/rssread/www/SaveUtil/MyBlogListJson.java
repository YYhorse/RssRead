package com.rssread.www.SaveUtil;

import com.rssread.www.Adapter.Item.MyBlogItem;

import java.util.List;

/**
 * Created by yy on 2018/3/15.
 *  * 我订阅博客JSON存储数据
 */
public class MyBlogListJson {
    private List<MyBlogItem> MyblogList;
    public List<MyBlogItem> getMyBlogItems() {return this.MyblogList;}
    public void setMyBlogItems(List<MyBlogItem> MyBlogItem) {this.MyblogList = MyBlogItem;}
}
