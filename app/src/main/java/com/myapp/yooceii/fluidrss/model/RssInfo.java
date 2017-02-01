package com.myapp.yooceii.fluidrss.model;

import java.io.Serializable;

/**
 * Created by yooceii on 2016/10/27.
 */

public class RssInfo implements Serializable {
    private static final long serialVersionUID=1L;
    private String title;//简略描述
    private String description;//文章内容
    private String content;//文章内容
    private String link;//网站链接
    private String category;//rss内容种类
    private String pubdate;//发布日期
    private String img;//内容图片
    private String source;//网站名
    private String icon;//网站icon

    public RssInfo()
    {

    }

    public RssInfo(String title,String description,String link,String category,String pubdate,String img,String source)
    {
        this.title=title;
        this.category=category;
        this.description=description;
        this.link=link;
        this.category=category;
        this.pubdate=pubdate;
        this.img=img;
        this.source=source;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon() {
        this.icon = link+"/favicon.ico ";
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String toString() {
        return  "title: "+getTitle()+"\n link:"+getLink()+
                "\n Img: "+getImg()+"\n Description: "+getDescription()+"\n pubdate: "+getPubdate();
    }
}
