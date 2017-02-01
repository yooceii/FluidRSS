package com.myapp.yooceii.fluidrss.model;

import android.graphics.Bitmap;
import android.icu.text.StringPrepParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by yooceii on 2016/11/14.
 */

public class Rssres {
    private UUID rssId;
    private String rssLink;
    private String siteName;

    private static final String JSON_RSSID="rssId";
    private static final String JSON_RSSLINK="rssLink";
    private static final String JSON_SITENAME="siteName";

    public UUID getRssId() {
        return rssId;
    }

    public void setRssId(UUID rssId) {
        this.rssId = rssId;
    }

    public Rssres()
    {
        rssId=UUID.randomUUID();
    }
    public Rssres(String rssLink,String siteName)
    {
        rssId=UUID.randomUUID();
        this.rssLink=rssLink;
        this.siteName=siteName;
    }

    public Rssres(JSONObject json) throws JSONException
    {
        rssId=UUID.fromString(json.getString(JSON_RSSID));

    }
    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

}
