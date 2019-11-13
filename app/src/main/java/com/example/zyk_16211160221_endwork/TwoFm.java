package com.example.zyk_16211160221_endwork;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by 难宿命 on 2018/12/31.
 */

public class TwoFm extends AppCompatActivity {
    public static SQLiteDatabase db;
    ListView lv;
    private long context_id;
    public static Cursor cursor;
    public static NewsCursorAdapter adapter;
    public static String title;
    public static String date;
    public static String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twofm_layout);

        lv=findViewById(R.id.twofm_listview);

        db=new DataBaseHelper(this).getWritableDatabase();
        String sql=String.format("select * from %s",DataBaseHelper.NEWS_TABLE);
        cursor=db.rawQuery(sql,null);
        adapter=new NewsCursorAdapter(this,cursor);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                title=cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_TITLE));
                date=cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_DATE));
                content=cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_CONTENT));
                Log.d("test:","title:"+title+",   date"+date+",   content"+content);
                send();
            }
        });

        SearchView searchView=findViewById(R.id.SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    String sql=String.format("select * from %s",DataBaseHelper.NEWS_TABLE);
                    Cursor c=db.rawQuery(sql,null);
                    cursor=c;
                    adapter.swapCursor(c);
                }
                else{
                    String sql=String.format("select * from %s where %s like ?",DataBaseHelper.NEWS_TABLE,DataBaseHelper.KEY_TITLE);
                    Cursor c=db.rawQuery(sql,new String[]{"%"+newText+"%"});
                    cursor=c;
                    adapter.swapCursor(c);
                }
                return false;
            }
        });


    }




    private void send() {
        Intent intent = new Intent(TwoFm.this,WithoutInternetReadActivity.class);
        startActivity(intent);
    }
}
