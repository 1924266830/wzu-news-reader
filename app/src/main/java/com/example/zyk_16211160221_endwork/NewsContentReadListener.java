package com.example.zyk_16211160221_endwork;

import java.util.ArrayList;

/**
 * Created by 难宿命 on 2019/1/4.
 */

public interface NewsContentReadListener {
    void onError(Exception e);
    void onNewsContentReadFinished(NewsContent newsContent);

}
