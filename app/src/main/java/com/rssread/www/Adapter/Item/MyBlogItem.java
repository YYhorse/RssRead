package com.rssread.www.Adapter.Item;

import java.util.List;

/**
 * Created by yy on 2018/3/16.
 * {"MyblogList":[{"blogCategroy":"技术文章","blogInfo":[{"Title":"腾讯X5浏览服务","Url":"http://x5.tencent.com/"}]},{"blogCategroy":"Blog主","blogInfo":[]},{"blogCategroy":"明星消息","blogInfo":[]},{"blogCategroy":"体坛风云","blogInfo":[]},{"blogCategroy":"奇闻怪事","blogInfo":[]}]}
 */
public class MyBlogItem {
    public String blogCategroy;
    public List<BlogItem> blogInfo;

    public String getBlogCategroy() {return this.blogCategroy;}
    public void setBlogCategroy(String BlogCategroy) {this.blogCategroy = BlogCategroy;}

    public List<BlogItem> getBlogInfo() {return this.blogInfo;}
    public void setBlogInfo(List<BlogItem> BlogInfo) {this.blogInfo = BlogInfo;}
}
