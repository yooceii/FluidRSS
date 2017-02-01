package com.myapp.yooceii.fluidrss.model;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yooceii on 2016/10/27.
 */

public interface RssLoadListener {
    void onLoadSuccess(/*ArrayList<RssInfo> rssInfoList*/ List<SyndEntry> syndEntryList);//获取成功
    void onLoadFailed();//获取失败
    void onNetWorkFailed();//网络错误
    void onBeginLoad();//开始任务
}
