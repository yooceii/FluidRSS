package com.myapp.yooceii.fluidrss.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.yooceii.fluidrss.R;
import com.myapp.yooceii.fluidrss.Utils.DensityUtils;
import com.myapp.yooceii.fluidrss.Utils.DomainUtils;
import com.myapp.yooceii.fluidrss.Utils.TimeUtils;
import com.rometools.rome.feed.synd.SyndEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yooceii on 2016/10/30.
 */

public class RssinfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RssInfo> rssInfos;
    private ArrayList<SyndEntry> syndEntryArrayList;
    private Context context;
    private LayoutInflater inflater;
    private Handler handle;
    private OnItemClickListener mOnItemClickListener;

    public RssinfoAdapter(Context context, ArrayList<RssInfo> list,ArrayList<SyndEntry> syndEntryArrayList) {
        this.context = context;
        this.rssInfos = list;
        this.syndEntryArrayList=syndEntryArrayList;
        System.out.println(syndEntryArrayList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder holder1 = (MyViewHolder) holder;
        final SyndEntry syndEntry=syndEntryArrayList.get(position);
        System.out.println(getImgFromContent(syndEntry.getDescription().getValue().trim()));
        Picasso.with(holder1.imageView.getContext()).load(getImgFromContent(syndEntry.getDescription().getValue().trim())).noFade().resize(DensityUtils.dp2px(holder1.imageView.getContext(),400),DensityUtils.dp2px(holder1.imageView.getContext(),250))
                .centerCrop().config(Bitmap.Config.RGB_565).into(holder1.imageView);
        try {
            Picasso.with(holder1.icon.getContext()).load("https://icons.better-idea.org/icon?url="+DomainUtils.getTopDomainWithoutSubdomain(syndEntry.getLink())+"&size=20").noFade().resize(DensityUtils.dp2px(holder1.icon.getContext(),16),DensityUtils.dp2px(holder1.icon.getContext(),16)).into(holder1.icon);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        holder1.title.setText(Html.fromHtml(syndEntry.getTitle()));
        holder1.pubdate.setText(TimeUtils.timadifference(TimeUtils.string2Calendar(syndEntry.getPublishedDate().toString()),context));
    }

    @Override
    public int getItemCount() {
        return syndEntryArrayList.size();
    }
    private class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;
        private TextView pubdate;
        private ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.pic);
            title = (TextView) itemView.findViewById(R.id.name);
            pubdate=(TextView) itemView.findViewById(R.id.pubdate);
            icon=(ImageView)itemView.findViewById(R.id.icon);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    private String getImgFromContent(String description){
        String img = null;
        if(description.contains("img") && img == null) {
            img = description.split("src=\"")[1];
            img = img.split("\"")[0];
        }
        return img;
    }
}
