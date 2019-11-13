package com.example.zyk_16211160221_endwork;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by 难宿命 on 2019/1/4.
 */

public class WithoutInternetReadActivity extends AppCompatActivity {
    NewsCursorAdapter adapter;
    TextView tv_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withoutinternet_layout);


        TextView tv_title=findViewById(R.id.withoutI_tv_title);
        TextView tv_date=findViewById(R.id.withoutI_tv_pubtime);
        tv_content=findViewById(R.id.withoutI_tv_content);

        tv_title.setText(TwoFm.title);
        tv_date.setText(TwoFm.date);
        tv_content.setText(TwoFm.content);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt2_menu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.opt2_size_small:
                tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                return true;
            case R.id.opt2_size_middle:
                tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                return true;
            case R.id.opt2_size_large:
                tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void changebg() {

    }
}
