package com.myapp.yooceii.fluidrss.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.yooceii.fluidrss.R;
import com.myapp.yooceii.fluidrss.model.RecyclerItemClickListener;
import com.myapp.yooceii.fluidrss.model.RssInfo;
import com.myapp.yooceii.fluidrss.model.RssLoadListener;
import com.myapp.yooceii.fluidrss.model.RssLoadModel;
import com.myapp.yooceii.fluidrss.model.RssLoadModel2;
import com.myapp.yooceii.fluidrss.model.RssinfoAdapter;
import com.rometools.rome.feed.synd.SyndEntry;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener,AddSourceFragment.Callback{

    private ArrayList<RssInfo> rssInfos=new ArrayList<RssInfo>();
    private ArrayList<SyndEntry> syndEntries=new ArrayList<SyndEntry>();
    //private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RssinfoAdapter rssinfoAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int RESQUEST_SOURCE_LINK=0;
    private int mScrollThreshold=0;
    private String source_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initdata(source_link);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        //Toast.makeText(this,source_link,Toast.LENGTH_SHORT).show();
        initdata(source_link);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_action) {
            FragmentManager fm=getSupportFragmentManager();
            AddSourceFragment addSourceFragment=new AddSourceFragment();
            addSourceFragment.setCallback(this);
            addSourceFragment.show(fm,null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK)
            return;
        if(requestCode==RESQUEST_SOURCE_LINK)
        {
            Toast.makeText(getApplicationContext(),(String)data.getSerializableExtra(AddSourceFragment.SOURCE_LINK),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getString(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        source_link=msg;
        onRefresh();
    }

    private void initview()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout_grid);
        swipeRefreshLayout.setOnRefreshListener(this);

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        rssinfoAdapter=new RssinfoAdapter(this,null,syndEntries);
        recyclerView.setAdapter(rssinfoAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

/*            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (dy > 0) {
                        fab.hide();
                    } else {
                        fab.show();
                    }
                }
                mScrollThreshold = dy;

            }*/
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplication().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i=new Intent(MainActivity.this,DetailActivity.class);
                i.putExtra(DetailActivity.POSITION,(Serializable)syndEntries.get(position));
                startActivity(i);
            }
        }));
    }

    private void initdata(String link)
    {
        System.out.println(link);
        if(link==null) {
            //link="http://www.engadget.com/rss.xml";
            //link="http://unsplash.com/rss";
            link="https://www.zhihu.com/rss";
        }
        final RssLoadModel2 rssLoadModel2=new RssLoadModel2(this,link);
        new Thread(new Runnable() {
            @Override
            public void run() {
                rssLoadModel2.excute(new RssLoadListener() {
                    @Override
                    public void onLoadSuccess(/*ArrayList<RssInfo> rssInfoList*/List<SyndEntry> syndEntryList) {
/*                rssInfos.clear();
                rssInfos.addAll(syndEntryList);
                rssinfoAdapter.notifyDataSetChanged();*/
                        syndEntries.clear();
                        syndEntries.addAll(syndEntryList);
                        new notifydata(null).start();
                    }

                    @Override
                    public void onLoadFailed() {

                    }

                    @Override
                    public void onNetWorkFailed() {
                        Toast.makeText(getApplicationContext(),"aa",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onBeginLoad() {

                    }
                });
            }
        }).start();
        new notifydata(null).start();
    }
    private class notifydata extends Thread
    {
        private String link;
        public notifydata(String link)
        {
            this.link=link;
        }
        public void run()
        {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.print("aa"+syndEntries.size());
                    rssinfoAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
