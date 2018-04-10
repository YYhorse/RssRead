package com.rssread.www.SaveUtil;

import com.rssread.www.Adapter.Item.BlogItem;

import java.util.List;

/**
 * Created by yy on 2018/3/15.
 * 博客JSON存储数据
 */
public class BlogListJson {
    private List<BlogItem> blogItemList;
    public List<BlogItem> getBlogItemList() {return this.blogItemList;}
    public void setBlogItemList(List<BlogItem> BlogItemList) {this.blogItemList = BlogItemList;}
}
