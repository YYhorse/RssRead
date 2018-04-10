package com.rssread.www.Adapter.Item;

/**
 * Created by yy-surface on 2018/3/16.
 */
public class MyBlogShowItem {
    private String title,type,url;

    public String getTitle() {return this.title;}
    public String getType() {return this.type;}
    public String getUrl() {return this.url;}

    public void setTitle(String Title) {this.title = Title;}
    public void setType(String Type) {this.type = Type;}
    public void setUrl(String Url) {this.url = Url;}
}

/*
{
　　"MyBlogShowItem":[
　　　　{
　　　　　　"title":"技术文章",
          "Url":"",
　　　　　　"type":"title"
　　　　},
　　　　{
　　　　　　"title":"腾讯X5浏览服务",
　　　　　　"Url":"http://x5.tencent.com/",
　　　　　　"type":"context"
　　　　}
　　]
}
 */