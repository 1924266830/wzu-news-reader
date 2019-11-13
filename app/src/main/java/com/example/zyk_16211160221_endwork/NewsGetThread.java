package com.example.zyk_16211160221_endwork;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 难宿命 on 2019/1/2.
 */

public class NewsGetThread extends Thread {
    private String url;
    private Handler handler;
    public static final String KEY_HTTP_AC="KEY_HTTP_AC";
    String urlBase="http://tw.wzu.edu.cn";

    public NewsGetThread( String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            //减慢加载速度
           /* try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            Document document= Jsoup.connect(url).timeout(10000).get();
            Elements elements= document.getElementsByAttributeValue("class","box_right_main");
            Elements lis=elements.select("li");


            Bundle bundle = new Bundle();
            ArrayList<NewsItem> newslist=new ArrayList<NewsItem>();
            for (int i = 0; i < lis.size(); i++) {
                Element a=lis.get(i).select("A").get(0);
                String title=a.text();
                String href="";
                if(a.attr("href").toString().length()>24)
                    href=urlBase+a.attr("href").substring(5);
                else
                    href=urlBase+a.attr("href").substring(2);

                String pubtime=lis.get(i).select("span").get(0).text();
                Log.d("test"+i,"title:"+title+",pubtime:"+pubtime+",href:"+href);
                newslist.add(i,new NewsItem(title,href,pubtime));
            }
            bundle.putSerializable(KEY_HTTP_AC,newslist);
            Message msg=new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
