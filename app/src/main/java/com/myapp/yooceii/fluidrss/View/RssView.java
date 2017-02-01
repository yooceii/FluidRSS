package com.myapp.yooceii.fluidrss.View;

import com.myapp.yooceii.fluidrss.model.RssInfo;
import java.util.List;

/**
 * Created by yooceii on 2016/10/29.
 */

public interface RssView extends View {
    void showLoading();

    void hideLoading();

    void showError(String msg);

    void showResult(List<RssInfo> rssInfos);

    void showTitle(String title);

    boolean isNetworkAvailable();
}
