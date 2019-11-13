package com.example.zyk_16211160221_endwork;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 难宿命 on 2019/1/4.
 */

public class NewsContentGetThread extends Thread {
    public static final String KEY_HTTP_RC="KEY_HTTP_RC";
    String srcBase="http://tw.wzu.edu.cn";

    String url;
    Activity activity;
    NewsContentReadListener Listener;

    public NewsContentGetThread(String url, Activity activity, NewsContentReadListener listener) {
        this.url = url;
        this.activity = activity;
        Listener = listener;
    }

    public NewsContentGetThread(String url, Activity activity) {
        this.url = url;
        this.activity = activity;
    }

    public void setListener(NewsContentReadListener listener) {
        Listener = listener;
    }

    //获取图片
    public static Bitmap getBitmap(String path) throws IOException{

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
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

            String title= document.getElementsByAttributeValue("class","c193832_title").text();
            String date= document.getElementsByAttributeValue("class","c193832_date").text();
            Log.d("test:","title:"+title+",date:"+date);
            final NewsContent newsContent=new NewsContent();
            newsContent.setTitle(title);
            newsContent.setDate(date);

            Elements elements=document.getElementsByAttributeValue("class","c193832_content");
            Elements pics=document.getElementsByAttributeValue("class", "img_vsb_content");
            Log.d("TEST",""+pics.size());

            List<String> contentlist=new ArrayList<>();
            List<Bitmap> piclist=new ArrayList<>();
            /*****************************************/

            //保存文字内容 Elements els = doc.getElementsByAttributeValue("class", "c193832_content");



            Element el =  elements.get(0);
            Elements ps = el.select("p");
            for (int i = 0; i < ps.size(); i++) {
                Element p = ps.get(i);
                Elements spans = p.select("span");
                if(spans.size()>0)
                {
                    for(int j=0;j<spans.size();j++)
                    {   String text=spans.get(j).text();
                        Element span = spans.get(j);
                        if(text!=""&&text!=null)
                        {
                            contentlist.add(text);
                            break;
                        }
                    }
                }else {
                    if(p.text()!=""&&p.text()!=null)
                        contentlist.add("    "+p.text());
                }

                contentlist.add("\n");
            }

            for(int i=0;i<pics.size();i++)
            {
                String picsrc=pics.get(i).attr("src");
                piclist.add(getBitmap(srcBase+pics.get(i).attr("src")));
            }

            newsContent.setContentlist(contentlist);
            newsContent.setPiclist(piclist);
            Log.d("contentlist内容:",contentlist.size()+",");
            Log.d("piclist内容:",piclist.size()+",");
            newsContent.setPiclist(piclist);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Listener.onNewsContentReadFinished(newsContent);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
