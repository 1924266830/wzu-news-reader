package com.example.zyk_16211160221_endwork;

import java.io.Serializable;

/**
 * Created by 难宿命 on 2019/1/2.
 */

public class NewsItem implements Serializable {
    private String title;
    private String href;
    private String pubTime;

    public NewsItem(String title, String href, String pubTime) {
        this.title = title;
        this.href = href;
        this.pubTime = pubTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }
}
