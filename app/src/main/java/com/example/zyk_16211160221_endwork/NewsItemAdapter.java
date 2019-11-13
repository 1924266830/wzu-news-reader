package com.example.zyk_16211160221_endwork;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 难宿命 on 2019/1/2.
 */

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {
    private Context context;
    private ArrayList<NewsItem> newslist;

    public NewsItemAdapter(Context context, ArrayList<NewsItem> newslist) {
        super(context,android.R.layout.simple_list_item_1,newslist);
        this.context = context;
        this.newslist = newslist;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.listitem_layout,null,false);
        }
        TextView tv_title=convertView.findViewById(R.id.listitem_tv_title);
        TextView tv_pubtime=convertView.findViewById(R.id.listitem_tv_pubtime);

        NewsItem newsItem=newslist.get(position);
        String s=String.format("[%03d]%s",position+1,newsItem.getTitle());
        tv_title.setText(s);
        tv_pubtime.setText(newsItem.getPubTime());

        return convertView;
    }
}
