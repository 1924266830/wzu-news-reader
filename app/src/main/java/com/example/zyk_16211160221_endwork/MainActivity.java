package com.example.zyk_16211160221_endwork;


import android.app.ActivityGroup;

import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;

import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity   extends ActivityGroup{

    private TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //初始化FragmentTabHost
        initHost();
        //初始化底部导航栏
        initTab();
        //默认选中


    }

    private void initHost() {
        mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setup();
        mTabHost.setup(this.getLocalActivityManager());
        //调用setup方法 设置view
        mTabHost.setCurrentTab(0);
        mTabHost.clearAllTabs();
        //去除分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        //监听事件
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                TabWidget tabw = mTabHost.getTabWidget();

                for (int i = 0; i < tabw.getChildCount(); i++) {
                    View v = tabw.getChildAt(i);
                    TextView tv = (TextView) v.findViewById(R.id.foot_tv);
                    ImageView iv = (ImageView) v.findViewById(R.id.foot_iv);

                    //修改当前的界面按钮颜色图片
                    if (i == mTabHost.getCurrentTab()) {
                        tv.setTextColor(getResources().getColor(R.color.colorAccent));
                        iv.setImageResource(TabDb.getTabsImgLight()[i]);
                    }else{
                        tv.setTextColor(getResources().getColor(R.color.tab_color));
                        iv.setImageResource(TabDb.getTabsImg()[i]);
                    }
                }


            }
        });

    }

    private void initTab() {
        String[] tabs = TabDb.getTabsTxt();
        for (int i = 0; i < tabs.length; i++) {
            //新建TabSpec
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(TabDb.getTabsTxt()[i]);
            //设置view
            View view = LayoutInflater.from(this).inflate(R.layout.tab_foot_layout, null);
            ((TextView) view.findViewById(R.id.foot_tv)).setText(TabDb.getTabsTxt()[i]);
            ((ImageView) view.findViewById(R.id.foot_iv)).setImageResource(TabDb.getTabsImg()[i]);
            tabSpec.setIndicator(view);
            tabSpec.setContent(new Intent(this,TabDb.getFramgent()[i]));
            //加入TabSpec
            mTabHost.addTab(tabSpec);

        }


    }
}
