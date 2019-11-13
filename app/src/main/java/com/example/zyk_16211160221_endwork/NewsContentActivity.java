package com.example.zyk_16211160221_endwork;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 难宿命 on 2019/1/4.
 */
public class NewsContentActivity extends AppCompatActivity{
    String url;
    TextView tv_title;
    TextView tv_date;
    LinearLayout ll;
    List<String> contentlist;
    List<Bitmap> piclist;
    int textsize;
    ScrollView sv;
    String[] check={"（","）","、","(",")","?"};
    List<TextView> SizeChanglist;

    String allcontent;

    private static final int IMAGE_PICK_NODE = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsread_layout);

        allcontent="";


        Intent i=getIntent();
        url=i.getStringExtra("href");
        SizeChanglist=new ArrayList<>();
        tv_title=findViewById(R.id.newsread_tv_title);
        tv_date=findViewById(R.id.newsread_tv_pubtime);
        ll=findViewById(R.id.newsread_main_ll);
        sv=findViewById(R.id.newsread_scrollview);
        //初始化整个界面
        initMain();


    }
    //选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.download:
                download();
             return true;
            case R.id.size_small:
                Sizechange(1);
                return true;
            case R.id.size_middle:
                Sizechange(2);
                return true;
            case R.id.size_large:
                Sizechange(3);
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }



    public  void download() {
        String sqlcheck=String.format("select * from %s where %s='%s'",DataBaseHelper.NEWS_TABLE,DataBaseHelper.KEY_TITLE,tv_title.getText().toString());
        Cursor check=TwoFm.db.rawQuery(sqlcheck,null);

        check.moveToFirst();
        Log.d("Test:","checknum:"+check.getCount());
        if(check.getCount()==0)
        {
            DataBaseHelper.insert(TwoFm.db,tv_title.getText().toString(),tv_date.getText().toString(),allcontent);
            String sql=String.format("select * from %s",DataBaseHelper.NEWS_TABLE);
            Cursor c=TwoFm.db.rawQuery(sql,null);
            TwoFm.cursor=c;
            TwoFm.adapter.swapCursor(c);
        }


    }
    //上下文菜单



    private void Sizechange(int size) {
        if(size==1)
        {
            for (int i = 0; i <SizeChanglist.size() ; i++) {
                SizeChanglist.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            }
        }
        if(size==2)
        {
            for (int i = 0; i <SizeChanglist.size() ; i++) {
                SizeChanglist.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            }
        }
        if(size==3)
        {
            for (int i = 0; i <SizeChanglist.size() ; i++) {
                SizeChanglist.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            }
        }
    }

    private void initMain() {
        NewsContentGetThread ncg=new NewsContentGetThread(url,this);
        ncg.setListener(new NewsContentReadListener() {
            @Override
            public void onError(Exception e) {
                Toast.makeText(NewsContentActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNewsContentReadFinished(NewsContent newsContent) {
                tv_title.setText(newsContent.getTitle());
                tv_date.setText(newsContent.getDate());
                contentlist=newsContent.getContentlist();
                piclist=newsContent.getPiclist();
                int picnum=0;
                for (int i = 0; i < contentlist.size(); i++) {


                    if(contentlist.get(i)!="\n")
                    {
                       String content=contentlist.get(i);
                        if(isContent(content))
                        {
                            TextView tv_new=new TextView(NewsContentActivity.this);
                            tv_new.setText("    "+content);
                            tv_new.setPadding(50,20,50,20);
                            tv_new.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                            SizeChanglist.add(tv_new);
                            ll.addView(tv_new);
                            allcontent+=content+"\n";
                            if(picnum<piclist.size())
                            {
                                ImageView iv_new=new ImageView(NewsContentActivity.this);
                                iv_new.setAdjustViewBounds(true);
                                iv_new.setMaxHeight(400);
                                iv_new.setMaxWidth(250);
                                iv_new.setImageBitmap(piclist.get(picnum));
                                ll.addView(iv_new);

                            }
                            picnum++;
                        }else{
                            content+=contentlist.get(i);
                        }
                    }

                }
                download();
            }
        });
        ncg.start();

    }

    private boolean isContent(String content) {
        for (int i = 0; i < check.length; i++) {
            if(content.substring(0,0).equals(check[i])||content.length()<10)
                return false;
        }

        return true;
    }
}
