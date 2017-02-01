package com.myapp.yooceii.fluidrss.model;

import android.content.Context;

import com.myapp.yooceii.fluidrss.Utils.NetworkUtils;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yooceii on 2016/11/29.
 */

public class RssLoadModel2 implements RssLoad {
    private Context context;
    private String url;
    public RssLoadModel2(Context context,String url)
    {
        this.context=context;
        this.url=url;
    }
    @Override
    public void excute(final RssLoadListener loadListener) {
        if(!NetworkUtils.isNetworkAvailable(context)){
            loadListener.onNetWorkFailed();
            return ;
        }
        loadListener.onBeginLoad();
        try
        {
            SyndFeed syndFeed=new SyndFeedInput().build(new XmlReader(new URL(url)));

            loadListener.onLoadSuccess(syndFeed.getEntries());
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
        }
    }
}
