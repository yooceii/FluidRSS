package com.myapp.yooceii.fluidrss.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

/**
 * Created by yooceii on 2016/11/1.
 */

public class RecyclerImageView extends ImageView {

    public RecyclerImageView(Context context)
    {
        super(context);
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}
