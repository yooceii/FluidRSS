package com.myapp.yooceii.fluidrss.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.yooceii.fluidrss.R;
import com.myapp.yooceii.fluidrss.Utils.DensityUtils;
import com.myapp.yooceii.fluidrss.Utils.DomainUtils;
import com.myapp.yooceii.fluidrss.model.RssInfo;
import com.rometools.rome.feed.synd.SyndEntry;
import com.squareup.picasso.Picasso;

import java.io.Console;

public class DetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar detailtoolbar;
    private RssInfo rssInfo;
    private SyndEntry syndEntry;
    private WebView detailtext;
    private TextView detailtitle;
    private ImageView detailimage;
    private ImageView icon;
    private String codePrefixOne = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" +
            "<html>" +
            "<head>" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=\">" +
            "<style type=\"text/css\">";
    private String codePrefixTwo = "</style>" + "</head>" + "<body></body>" + "</html>";
    private String codeSubfix = "</body></html>";
    public static final String POSITION="com.myapp.yooceii.fluidrss.activity.DetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);


        detailtoolbar=(Toolbar)findViewById(R.id.detailtoolbar);
        setSupportActionBar(detailtoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if(getSupportActionBar() != null) {
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        detailtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        detailtoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                System.out.print(item.getTitle());
                switch (item.getItemId())
                {
                    case R.id.share:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT,syndEntry.getLink());
                        startActivity(Intent.createChooser(share, "Share Link!"));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        syndEntry=(SyndEntry) getIntent().getSerializableExtra(POSITION);
        Log.i("a","description"+syndEntry.getDescription().getValue());
        Log.i("a","content"+syndEntry.getContents());
        Log.i("a","link"+" "+syndEntry.getLink());
        System.out.println(syndEntry.getDescription().getValue());
        System.out.println(syndEntry.getContents());
        detailimage=(ImageView)findViewById(R.id.detailimage);
        detailtext=(WebView)findViewById(R.id.detailtext);

        WebSettings webSettings=detailtext.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        if(getImgFromContent(syndEntry.getDescription().getValue().trim())!=null)
        {
            Picasso.with(DetailActivity.this).load(getImgFromContent(syndEntry.getDescription().getValue().trim())).fit().noFade().config(Bitmap.Config.RGB_565).into(detailimage);
            if(getImgFromContent(syndEntry.getDescription().getValue().trim())!=null)
            {
                Log.d("webview","des");
                detailtext.loadData("<meta name=\"viewport\" content=\"user-scalable=0, width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1\" />"+"<style>img, object { max-width: 100%;}</style>" + syndEntry.getDescription().getValue().replace("<img src=\""+getImgFromContent(syndEntry.getDescription().getValue().trim())+"\" />","") ,"text/html; charset=UTF-8", null);
            }
            else
            {
                Log.d("webview","con");
                detailtext.loadData("<meta name=\"viewport\" content=\"user-scalable=0, width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1\" />"+"<style>img, object { max-width: 100%;}</style>" + syndEntry.getDescription().getValue() ,"text/html; charset=UTF-8", null);
            }

        }
        else if(getImgFromContent(syndEntry.getContents().toString().trim())!=null)
        {
            Picasso.with(DetailActivity.this).load(getImgFromContent(syndEntry.getContents().get(0).getValue())).fit().noFade().config(Bitmap.Config.RGB_565).into(detailimage);
            detailtext.loadData("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />"+"<style>img, object { max-width: 100%;}</style>"  + syndEntry.getContents().get(0).getValue().replace("<img src=\""+getImgFromContent(syndEntry.getContents().get(0).getValue().trim())+"\" />","") ,"text/html; charset=UTF-8", null);
        }
        detailtext.setBackgroundColor(0);
        icon=(ImageView)findViewById(R.id.detailicon);
        try {
            //System.out.println("https://icons.better-idea.org/icon?url="+ DomainUtils.getTopDomainWithoutSubdomain(syndEntry.getLink())+"&size=20");
            Picasso.with(icon.getContext()).load("https://icons.better-idea.org/icon?url=https://"+ DomainUtils.getTopDomainWithoutSubdomain(syndEntry.getLink())+"&size=20").noFade().resize(DensityUtils.dp2px(icon.getContext(),16),DensityUtils.dp2px(icon.getContext(),16)).into(icon);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        detailtitle=(TextView)findViewById(R.id.detailtitle);
        detailtitle.setMovementMethod(LinkMovementMethod.getInstance());
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.N)
        {
            detailtitle.setText(Html.fromHtml(syndEntry.getTitle(),Html.FROM_HTML_MODE_LEGACY));
        }
        else
            detailtitle.setText(Html.fromHtml(syndEntry.getTitle()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_datail_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private String getImgFromContent(String description){
        String img = null;
        if(description.contains("img") && img == null) {
            img = description.split("src=\"")[1];
            img = img.split("\"")[0];
        }
        Log.i("img",img);
        return img;
    }
}
