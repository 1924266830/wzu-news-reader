package com.example.zyk_16211160221_endwork;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 难宿命 on 2019/1/4.
 */

public class NewsCursorAdapter extends CursorAdapter {
    Context context;
    TextView tv_title;
    TextView tv_date;


    public NewsCursorAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(R.layout.listitem_layout,null,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        View v=view;
        tv_title=v.findViewById(R.id.listitem_tv_title);
        tv_date=v.findViewById(R.id.listitem_tv_pubtime);
        String title=cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_TITLE));
        String date=cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_DATE));
        tv_title.setText(title);
        tv_date.setText(date);
    }

    public TextView getTv_title() {
        return tv_title;
    }
}
