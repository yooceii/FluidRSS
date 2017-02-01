package com.myapp.yooceii.fluidrss.View;

import com.myapp.yooceii.fluidrss.model.RssInfo;

/**
 * Created by yooceii on 2016/10/29.
 */

public interface WebView extends View {
    void showLoading();

    void hideLoading();

    void showError(String msg);

    void showResult(RssInfo rssInfo);

    void showTitle(String title);

    boolean isNetworkAvailable();
}
