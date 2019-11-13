package com.example.zyk_16211160221_endwork;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 难宿命 on 2019/1/3.
 */

public class NewsContent implements Serializable {
    String title;
    String date;
    List<Bitmap> piclist;
    List<String> contentlist;

    public NewsContent(String title, String date, List<Bitmap> piclist, List<String> contentlist) {
        this.title = title;
        this.date = date;
        this.piclist = piclist;
        this.contentlist = contentlist;
    }

    public NewsContent() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Bitmap> getPiclist() {
        return piclist;
    }

    public void setPiclist(List<Bitmap> piclist) {
        this.piclist = piclist;
    }

    public List<String> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<String> contentlist) {
        this.contentlist = contentlist;
    }
}
