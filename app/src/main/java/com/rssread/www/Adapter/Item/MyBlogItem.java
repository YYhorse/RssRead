package com.rssread.www.Adapter.Item;

import java.util.List;

/**
 * Created by yy on 2018/3/16.
 * {"MyblogList":[{"blogCategroy":"技术文章","blogInfo":[{"Title":"腾讯X5浏览服务","Url":"http://x5.tencent.com/"}]},{"blogCategroy":"Blog主","blogInfo":[]},{"blogCategroy":"明星消息","blogInfo":[]},{"blogCategroy":"体坛风云","blogInfo":[]},{"blogCategroy":"奇闻怪事","blogInfo":[]}]}
 */
public class MyBlogItem {
    String blogCategroy;
    List<BlogInfo> blogInfo;

    public String getBlogCategroy() {return this.blogCategroy;}
    public void setBlogCategroy(String BlogCategroy) {this.blogCategroy = BlogCategroy;}

    public List<BlogInfo> getBlogInfo() {return this.blogInfo;}
    public void setBlogInfo(List<BlogInfo> BlogInfo) {this.blogInfo = BlogInfo;}

    public class BlogInfo{
        String Title,Url;

        public String getTitle() {return this.Title;}
        public void setTitle(String title) {this.Title = title;}

        public String getUrl() {return this.Url;}
        public void setUrl(String url) {this.Url = url;}
    }
}
