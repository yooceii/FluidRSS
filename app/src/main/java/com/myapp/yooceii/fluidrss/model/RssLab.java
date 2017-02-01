package com.myapp.yooceii.fluidrss.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by yooceii on 2016/11/14.
 */

public class RssLab {
    private ArrayList<Rssres> rssresArrayList;
    private Context mContext;
    private static RssLab rssLab;
    public RssLab(Context context)
    {
        this.mContext=context;
        rssresArrayList=new ArrayList<Rssres>();
    }
    public static RssLab get(Context context)
    {
        if(rssLab==null)
        {
            rssLab=new RssLab(context.getApplicationContext());
        }
        return rssLab;
    }
    public Rssres getRssres(String siteName)
    {
        for(Rssres r:rssresArrayList)
        {
            if(r.getSiteName().equals(siteName))
                return r;
        }
        return null;
    }
}
