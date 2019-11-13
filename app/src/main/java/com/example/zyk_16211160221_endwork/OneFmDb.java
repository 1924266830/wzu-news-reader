package com.example.zyk_16211160221_endwork;

/**
 * Created by 难宿命 on 2019/1/2.
 */

public class OneFmDb {
    /***
     * 获得OneFm顶部所有项
     */
    public static String[] getPageText() {
        String[] pages = {"头条","团委公告","校园文化","青年团校","青年之声"};
        return pages;
    }

    public static String[] getUrls(){
        String[] urls={"http://tw.wzu.edu.cn/xbwzlm/twxw2.htm","http://tw.wzu.edu.cn/xbwzlm/twgk/ggl.htm","http://tw.wzu.edu.cn/xbwzlm/xywh2.htm",
                        "http://tw.wzu.edu.cn/xbwzlm/qnzd1/qntx.htm","http://tw.wzu.edu.cn/xbwzlm/qnzd1/qnzs.htm"};
        return urls;
    }
    public static String[] getUrladds(){
        String[] urladds={"http://tw.wzu.edu.cn/xbwzlm/twxw2/","http://tw.wzu.edu.cn/xbwzlm/twgk/ggl/","http://tw.wzu.edu.cn/xbwzlm/xywh2/",
                        "http://tw.wzu.edu.cn/xbwzlm/qnzd1/qntx/","http://tw.wzu.edu.cn/xbwzlm/qnzd1/"};
        return urladds;
    }
    public static  int[] getTotalpages(){
        int[] totalpages={73,11,190,13,1};
        return totalpages;
    }

    public static int[] getPageIds(){
        int[] pageids={R.layout.toutiao_page_layout,R.layout.twgg_page_layout,R.layout.xywh_page_layout,R.layout.qntx_page_layout,R.layout.qnzs_page_layout};
        return pageids;
    }
    public static int[] getlistviewIds(){
        int[] listviewIds={R.id.toutiao_page_listView,R.id.twgg_page_listView,R.id.xywh_page_listView,R.id.qntx_page_listView,R.id.qnzs_page_listView};
        return listviewIds;
    }
}
