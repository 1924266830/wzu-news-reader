package com.example.zyk_16211160221_endwork;

/**
 * Created by 难宿命 on 2018/12/28.
 */

public class TabDb {
    /***
     * 获得底部所有项
     */
    public static String[] getTabsTxt() {
        String[] tabs = {"首页","我的"};
        return tabs;
    }
    /***
     * 获得所有碎片
     */
   public static Class[] getFramgent(){
        Class[] cls = {OneFm.class,TwoFm.class};
        return cls ;
    }
    /***
     * 获得所有点击前的图片
     */
     public static int[] getTabsImg(){
        int[] img = {R.drawable.home1,R.drawable.mine1};
        return img ;
    }
    /***
     * 获得所有点击后的图片
*/
    public static int[] getTabsImgLight(){
        int[] img = {R.drawable.home2,R.drawable.mine2};
        return img ;
    }

}
