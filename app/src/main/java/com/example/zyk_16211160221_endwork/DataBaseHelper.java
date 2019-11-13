package com.example.zyk_16211160221_endwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 难宿命 on 2019/1/4.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="mycontact.db";
    public static final String NEWS_TABLE="contact";
    public static final String KEY_TITLE="TITLE";
    public static final String KEY_DATE="DATE";
    public static final String KEY_CONTENT="content";
    private static int version=1;
    private Context context;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql=String.format("create table if not exists %s (_id INTEGER PRIMARY KEY,%s String,%s String,%s String)",NEWS_TABLE,KEY_TITLE,KEY_DATE,KEY_CONTENT);
        Log.d("sql:",sql);
        db.execSQL(sql);
    }
    public  static ContentValues getContentValues(String title, String date) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_TITLE,title);
        cv.put(KEY_DATE,date);
        return cv;
    }
    public  static ContentValues getContentValues(String title, String date,String content) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_TITLE,title);
        cv.put(KEY_DATE,date);
        cv.put(KEY_CONTENT,content);
        return cv;
    }

    public static long insert(SQLiteDatabase db,String title, String date,String content){
        ContentValues cv=getContentValues(title,date,content);
        return db.insert(NEWS_TABLE,KEY_TITLE,cv);
    }
    public static long insert(SQLiteDatabase db,String title, String date){
        ContentValues cv=getContentValues(title,date);
        return db.insert(NEWS_TABLE,KEY_TITLE,cv);
    }

    public static int delete(SQLiteDatabase db,String date) {
        return db.delete(NEWS_TABLE,"DATE="+date,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql=String.format("drop if exists %s",NEWS_TABLE);
        db.execSQL(sql);
        onCreate(db);
    }
}
