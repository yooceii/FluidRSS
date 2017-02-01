package com.myapp.yooceii.fluidrss.model;

import android.content.Context;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.myapp.yooceii.fluidrss.Utils.NetworkUtils;
import com.myapp.yooceii.fluidrss.Utils.XMLRequest;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by yooceii on 2016/10/28.
 */

public class RssLoadModel implements RssLoad
{
    private final static String TAG = "RssLoadModel";
    private final static String ITEM = "item";
    private final static String TITLE = "title";
    private final static String LINK = "link";
    private final static String DESCRIPTION = "description";
    private final static String CONTENT="content";
    private final static String PUBDATE="pubDate";


    private String url;
    private ArrayList<RssInfo> rssInfos;
    private RequestQueue mQueue;
    private Context context;

    public RssLoadModel(Context context, String url){
        this.context = context;
        this.url=url;
        rssInfos = new ArrayList<RssInfo>();
        mQueue = Volley.newRequestQueue(context);

    }

    public void excute(final RssLoadListener loadListener){
        if(!NetworkUtils.isNetworkAvailable(context)){
            loadListener.onNetWorkFailed();
            return ;
        }
        loadListener.onBeginLoad();
        XMLRequest xmlRequest = new XMLRequest(url,
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser response) {
                        parserXML(response);
                        //SyndFeed syndFeed=new SyndFeedInput().build(new XmlReader(new URL("http://cn.engadget.com/rss.xml")));
                        //loadListener.onLoadSuccess(rssInfos);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadListener.onLoadFailed();
            }
        });
        mQueue.add(xmlRequest);
/*        try
        {
            SyndFeed syndFeed=new SyndFeedInput().build(new XmlReader(new URL("http://cn.engadget.com/rss.xml")));
            loadListener.onLoadSuccess(syndFeed);
        }catch(FeedException e)
        {
            loadListener.onLoadFailed();
        }
        catch(MalformedURLException e)
        {
            loadListener.onLoadFailed();
        }
        catch(IOException e)
        {
            loadListener.onLoadFailed();
        }*/
    }

    //解析xml文件
    private void parserXML(XmlPullParser response){
        try {
            rssInfos.clear();
            RssInfo rssInfo = null;
            String pubdate = null;
            String title = null;
            String link = null;
            String description = null;
            String content=null;
            int eventType = response.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String nodeName = response.getName();
                        switch (nodeName){
                            case ITEM:
                                rssInfo = new RssInfo();
                                break;
                            case TITLE:
                                title= response.nextText();
                                break;
                            case LINK:
                                link = response.nextText();
                                break;
                            case DESCRIPTION:
                                description = response.nextText();
                                break;
                            case CONTENT:
                                content=response.nextText();
                                break;
                            case PUBDATE:
                                pubdate = response.nextText();
                                if(title != null) {
                                    rssInfo.setTitle(title);
                                    rssInfo.setLink(link);
                                    if(description==null) {
                                        rssInfo.setContent(content.replace("<img src=\""+getImgFromContent(content)+"\" />",""));
                                        rssInfo.setImg(getImgFromContent(content));
                                    }
                                    else
                                    {
                                        rssInfo.setDescription(description.replace("<img src=\""+getImgFromContent(description)+"\" />",""));
                                        rssInfo.setImg(getImgFromContent(description));
                                    }
                                    rssInfo.setPubdate(pubdate);
                                    Log.e(TAG, rssInfo.toString());
                                    rssInfos.add(rssInfo);
                                }
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        break;
                }
                eventType = response.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //从content中获取第一个图片的链接作为img的内容
    private String getImgFromContent(String description){
        String img = null;
        if(description.contains("img") && img == null) {
            img = description.split("src=\"")[1];
            img = img.split("\"")[0];
        }
        return img;
    }
}
