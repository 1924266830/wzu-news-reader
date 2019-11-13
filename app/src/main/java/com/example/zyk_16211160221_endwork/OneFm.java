package com.example.zyk_16211160221_endwork;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 难宿命 on 2018/12/31.
 */

public class OneFm  extends AppCompatActivity{
    List<View> views=new ArrayList<>();
    //用于 获取新闻
    TextView tv;
    Handler toutiao_handler;
    Handler twgg_handler;
    Handler xywh_handler;
    Handler qntx_handler;
    Handler qnzs_handler;
    NewsItemAdapter adapter_toutiao;
    NewsItemAdapter adapter_twgg;
    NewsItemAdapter adapter_xywh;
    NewsItemAdapter adapter_qntx;
    NewsItemAdapter adapter_qnzs;
    ArrayList<NewsItem> toutiao_newslist;
    ArrayList<NewsItem> twgg_newslist;
    ArrayList<NewsItem> xywh_newslist;
    ArrayList<NewsItem> qntx_newslist;
    ArrayList<NewsItem> qnzs_newslist;
    int toutiao_pageCount=0;
    int twgg_pageCount=0;
    int xywh_pageCount=0;
    int qntx_pageCount=0;
    int qnzs_pageCount=0;
    public boolean isAtFoot;


    Button footerBt;
    ProgressBar footerPb;
    public static final int MAX_PAGE=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onefm_layout);
        //头条
        View toutiao_view=inittoutiaoPage(OneFmDb.getPageIds()[0],OneFmDb.getlistviewIds()[0]);
        views.add(toutiao_view);

        //团委公告
        View twgg_view=inittwggPage(OneFmDb.getPageIds()[1],OneFmDb.getlistviewIds()[1]);
        views.add(twgg_view);

        //校园文化
        View xywh_view=initxywhPage(OneFmDb.getPageIds()[2],OneFmDb.getlistviewIds()[2]);
        views.add(xywh_view);

        //青年团校
        View qntx_view=initqntxPage(OneFmDb.getPageIds()[3],OneFmDb.getlistviewIds()[3]);
        views.add(qntx_view);

        //青年之声
        View qnzs_view=initqnzsPage(OneFmDb.getPageIds()[4],OneFmDb.getlistviewIds()[4]);
        views.add(qnzs_view);


        ViewPager viewPager = (ViewPager) findViewById(R.id.id_vp);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(views);
        viewPager.setAdapter(myPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tl);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);



        tabLayout.setupWithViewPager(viewPager);
    }
    /*********************************************头条*************************************************************/
    private View inittoutiaoPage(int pageId,int listId) {
        View viewpage = LayoutInflater.from(this).inflate(pageId, null, false);
        final ListView lv = viewpage.findViewById(listId);

        toutiao_newslist = new ArrayList<>();

        final View footerview = LayoutInflater.from(this).inflate(R.layout.moredata_layout, null, false);
        footerBt = footerview.findViewById(R.id.button_moredata);
        footerPb = footerview.findViewById(R.id.progressBar);
        //把moredata添加到lv末尾
        lv.addFooterView(footerview);

        adapter_toutiao = new NewsItemAdapter(this, toutiao_newslist);
        lv.setAdapter(adapter_toutiao);
        footerBt.setVisibility(View.GONE);
        toutiao_handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Bundle data = message.getData();
                ArrayList<NewsItem> templist = (ArrayList<NewsItem>) data.getSerializable(NewsGetThread.KEY_HTTP_AC);
                toutiao_newslist.addAll(templist);

                adapter_toutiao.notifyDataSetChanged();
                Log.d("adpter:", "" + toutiao_newslist.size());
                footerBt.setVisibility(View.VISIBLE);
                footerPb.setVisibility(View.GONE);
                return false;
            }
        });

        inittoutiaoNews(OneFmDb.getUrls()[0],toutiao_handler);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem itemAtPosition = (NewsItem) lv.getItemAtPosition(position);
                String href = itemAtPosition.getHref();
                Intent intent = new Intent(OneFm.this,NewsContentActivity.class);

                intent.putExtra("href",href);
                startActivity(intent);
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scollState) {
                if (scollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isAtFoot) {
                    toutiao_pageCount++;
                    if(OneFmDb.getTotalpages()[0] - toutiao_pageCount!=0){
                        String url2 = OneFmDb.getUrladds()[0]+(OneFmDb.getTotalpages()[0] - toutiao_pageCount)+".htm";
                        footerBt.setVisibility(View.GONE);
                        footerPb.setVisibility(View.VISIBLE);
                        new NewsGetThread(url2, toutiao_handler).start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /**
                 firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置(下标从0开始)
                 visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
                 totalItemCount表示ListView的ListItem总数
                 **/
                //Log.d("test","在现时屏幕第一个ListItem在整个ListView的位置:"+firstVisibleItem+",表示在现时屏幕可以见到的ListItem总数:"+visibleItemCount+
                // ",ListItem总数:"+totalItemCount);
                isAtFoot = (firstVisibleItem + visibleItemCount) >= totalItemCount;
                if (toutiao_pageCount > MAX_PAGE) {
                    isAtFoot = false;
                    footerBt.setVisibility(View.GONE);
                    footerPb.setVisibility(View.GONE);
                }

            }
        });
        return viewpage;
    }

    /*********************************************团委公告*************************************************************/
    private View inittwggPage(int pageId,int listId) {
        View viewpage = LayoutInflater.from(this).inflate(pageId, null, false);
        final ListView lv = viewpage.findViewById(listId);

        twgg_newslist = new ArrayList<>();

        final View footerview = LayoutInflater.from(this).inflate(R.layout.moredata_layout, null, false);
        footerBt = footerview.findViewById(R.id.button_moredata);
        footerPb = footerview.findViewById(R.id.progressBar);
        //把moredata添加到lv末尾
        //lv.addFooterView(footerview);

        adapter_twgg = new NewsItemAdapter(this, twgg_newslist);
        lv.setAdapter(adapter_twgg);
        footerBt.setVisibility(View.GONE);
        twgg_handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Bundle data = message.getData();
                ArrayList<NewsItem> templist = (ArrayList<NewsItem>) data.getSerializable(NewsGetThread.KEY_HTTP_AC);
                twgg_newslist.addAll(templist);

                adapter_twgg.notifyDataSetChanged();
                Log.d("adpter:", "" + twgg_newslist.size());
                footerBt.setVisibility(View.VISIBLE);
                footerPb.setVisibility(View.GONE);
                return false;
            }
        });

        inittwggNews(OneFmDb.getUrls()[1],twgg_handler);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem itemAtPosition = (NewsItem) lv.getItemAtPosition(position);
                String href = itemAtPosition.getHref();
                Intent intent = new Intent(OneFm.this,NewsContentActivity.class);

                intent.putExtra("href",href);
                startActivity(intent);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scollState) {
                if (scollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isAtFoot) {
                    twgg_pageCount++;
                    if(OneFmDb.getTotalpages()[1] - twgg_pageCount!=0){
                        String url2 = OneFmDb.getUrladds()[1]+(OneFmDb.getTotalpages()[1] - twgg_pageCount)+".htm";
                        footerBt.setVisibility(View.GONE);
                        footerPb.setVisibility(View.VISIBLE);
                        new NewsGetThread(url2, twgg_handler).start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /**
                 firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置(下标从0开始)
                 visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
                 totalItemCount表示ListView的ListItem总数
                 **/
                //Log.d("test","在现时屏幕第一个ListItem在整个ListView的位置:"+firstVisibleItem+",表示在现时屏幕可以见到的ListItem总数:"+visibleItemCount+
                // ",ListItem总数:"+totalItemCount);
                isAtFoot = (firstVisibleItem + visibleItemCount) >= totalItemCount;
                if (twgg_pageCount > MAX_PAGE) {
                    isAtFoot = false;
                    footerBt.setVisibility(View.GONE);
                    footerPb.setVisibility(View.GONE);
                }

            }
        });
        return viewpage;
    }
/*********************************************校园文化*************************************************************/
    private View initxywhPage(int pageId,int listId) {
        View viewpage = LayoutInflater.from(this).inflate(pageId, null, false);
        final ListView lv = viewpage.findViewById(listId);

        xywh_newslist = new ArrayList<>();

        final View footerview = LayoutInflater.from(this).inflate(R.layout.moredata_layout, null, false);
        footerBt = footerview.findViewById(R.id.button_moredata);
        footerPb = footerview.findViewById(R.id.progressBar);
        //把moredata添加到lv末尾
        lv.addFooterView(footerview);

        adapter_xywh = new NewsItemAdapter(this, xywh_newslist);
        lv.setAdapter(adapter_xywh);
        footerBt.setVisibility(View.GONE);
        xywh_handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Bundle data = message.getData();
                ArrayList<NewsItem> templist = (ArrayList<NewsItem>) data.getSerializable(NewsGetThread.KEY_HTTP_AC);
                xywh_newslist.addAll(templist);

                adapter_xywh.notifyDataSetChanged();
                Log.d("adpter:", "" + xywh_newslist.size());
                footerBt.setVisibility(View.VISIBLE);
                footerPb.setVisibility(View.GONE);
                return false;
            }
        });

        initxywhNews(OneFmDb.getUrls()[2],xywh_handler);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem itemAtPosition = (NewsItem) lv.getItemAtPosition(position);
                String href = itemAtPosition.getHref();
                Intent intent = new Intent(OneFm.this,NewsContentActivity.class);

                intent.putExtra("href",href);
                startActivity(intent);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scollState) {
                if (scollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isAtFoot) {
                    xywh_pageCount++;
                    if(OneFmDb.getTotalpages()[2] - xywh_pageCount!=0){
                        String url2 = OneFmDb.getUrladds()[2]+(OneFmDb.getTotalpages()[2] - xywh_pageCount)+".htm";
                        footerBt.setVisibility(View.GONE);
                        footerPb.setVisibility(View.VISIBLE);
                        new NewsGetThread(url2, xywh_handler).start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /**
                 firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置(下标从0开始)
                 visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
                 totalItemCount表示ListView的ListItem总数
                 **/
                //Log.d("test","在现时屏幕第一个ListItem在整个ListView的位置:"+firstVisibleItem+",表示在现时屏幕可以见到的ListItem总数:"+visibleItemCount+
                // ",ListItem总数:"+totalItemCount);
                isAtFoot = (firstVisibleItem + visibleItemCount) >= totalItemCount;
                if (xywh_pageCount > MAX_PAGE) {
                    isAtFoot = false;
                    footerBt.setVisibility(View.GONE);
                    footerPb.setVisibility(View.GONE);
                }

            }
        });
        return viewpage;
    }
    /*********************************************青年团校*************************************************************/
    private View initqntxPage(int pageId,int listId) {
        View viewpage = LayoutInflater.from(this).inflate(pageId, null, false);
        final ListView lv = viewpage.findViewById(listId);

        qntx_newslist = new ArrayList<>();

        final View footerview = LayoutInflater.from(this).inflate(R.layout.moredata_layout, null, false);
        footerBt = footerview.findViewById(R.id.button_moredata);
        footerPb = footerview.findViewById(R.id.progressBar);
        //把moredata添加到lv末尾
        lv.addFooterView(footerview);

        adapter_qntx = new NewsItemAdapter(this, qntx_newslist);
        lv.setAdapter(adapter_qntx);
        footerBt.setVisibility(View.GONE);
        qntx_handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Bundle data = message.getData();
                ArrayList<NewsItem> templist = (ArrayList<NewsItem>) data.getSerializable(NewsGetThread.KEY_HTTP_AC);
                qntx_newslist.addAll(templist);

                adapter_qntx.notifyDataSetChanged();
                Log.d("adpter:", "" + qntx_newslist.size());
                footerBt.setVisibility(View.VISIBLE);
                footerPb.setVisibility(View.GONE);
                return false;
            }
        });

        initqntxNews(OneFmDb.getUrls()[3],qntx_handler);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem itemAtPosition = (NewsItem) lv.getItemAtPosition(position);
                String href = itemAtPosition.getHref();
                Intent intent = new Intent(OneFm.this,NewsContentActivity.class);

                intent.putExtra("href",href);
                startActivity(intent);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scollState) {
                if (scollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isAtFoot) {
                    qntx_pageCount++;
                    if(OneFmDb.getTotalpages()[3] - qntx_pageCount!=0){
                        String url2 = OneFmDb.getUrladds()[3]+(OneFmDb.getTotalpages()[3] - qntx_pageCount)+".htm";
                        footerBt.setVisibility(View.GONE);
                        footerPb.setVisibility(View.VISIBLE);
                        new NewsGetThread(url2, qntx_handler).start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /**
                 firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置(下标从0开始)
                 visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
                 totalItemCount表示ListView的ListItem总数
                 **/
                //Log.d("test","在现时屏幕第一个ListItem在整个ListView的位置:"+firstVisibleItem+",表示在现时屏幕可以见到的ListItem总数:"+visibleItemCount+
                // ",ListItem总数:"+totalItemCount);
                isAtFoot = (firstVisibleItem + visibleItemCount) >= totalItemCount;
                if (qntx_pageCount > MAX_PAGE) {
                    isAtFoot = false;
                    footerBt.setVisibility(View.GONE);
                    footerPb.setVisibility(View.GONE);
                }

            }
        });
        return viewpage;
    }
    /*********************************************青年之声*************************************************************/
    private View initqnzsPage(int pageId,int listId) {
        View viewpage = LayoutInflater.from(this).inflate(pageId, null, false);
        final ListView lv = viewpage.findViewById(listId);

        qnzs_newslist = new ArrayList<>();

        final View footerview = LayoutInflater.from(this).inflate(R.layout.moredata_layout, null, false);
        footerBt = footerview.findViewById(R.id.button_moredata);
        footerPb = footerview.findViewById(R.id.progressBar);
        //把moredata添加到lv末尾
        lv.addFooterView(footerview);

        adapter_qnzs = new NewsItemAdapter(this, qnzs_newslist);
        lv.setAdapter(adapter_qnzs);
        footerBt.setVisibility(View.GONE);
        qnzs_handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Bundle data = message.getData();
                ArrayList<NewsItem> templist = (ArrayList<NewsItem>) data.getSerializable(NewsGetThread.KEY_HTTP_AC);
                qnzs_newslist.addAll(templist);

                adapter_qnzs.notifyDataSetChanged();
                Log.d("adpter:", "" + qnzs_newslist.size());
                footerBt.setVisibility(View.VISIBLE);
                footerPb.setVisibility(View.GONE);
                return false;
            }
        });

        initqnzsNews(OneFmDb.getUrls()[4],qnzs_handler);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem itemAtPosition = (NewsItem) lv.getItemAtPosition(position);
                String href = itemAtPosition.getHref();
                Intent intent = new Intent(OneFm.this,NewsContentActivity.class);

                intent.putExtra("href",href);
                startActivity(intent);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scollState) {
                footerBt.setVisibility(View.GONE);
                footerPb.setVisibility(View.GONE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /**
                 firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置(下标从0开始)
                 visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
                 totalItemCount表示ListView的ListItem总数
                 **/
                //Log.d("test","在现时屏幕第一个ListItem在整个ListView的位置:"+firstVisibleItem+",表示在现时屏幕可以见到的ListItem总数:"+visibleItemCount+
                // ",ListItem总数:"+totalItemCount);
                isAtFoot = (firstVisibleItem + visibleItemCount) >= totalItemCount;
                if (qnzs_pageCount > MAX_PAGE) {
                    isAtFoot = false;
                    footerBt.setVisibility(View.GONE);
                    footerPb.setVisibility(View.GONE);
                }

            }
        });
        return viewpage;
    }
    private void inittoutiaoNews(String url,Handler handler ) {
        toutiao_pageCount=0;

        new NewsGetThread(url,handler).start();
        Log.d("头条","启动");
    }
    private void inittwggNews(String url,Handler handler ) {
        twgg_pageCount=0;

        new NewsGetThread(url,handler).start();
        Log.d("团委公告","启动");
    }
    private void initxywhNews(String url,Handler handler ) {
        xywh_pageCount=0;

        new NewsGetThread(url,handler).start();
        Log.d("校园文化","启动");
    }
    private void initqntxNews(String url,Handler handler ) {
        qntx_pageCount=0;

        new NewsGetThread(url,handler).start();
        Log.d("青年团校","启动");
    }
    private void initqnzsNews(String url,Handler handler ) {
        qnzs_pageCount=0;

        new NewsGetThread(url,handler).start();
        Log.d("青年之声","启动");
    }
}
