package com.red.gotrade;

import android.text.TextUtils;

/**
 * Created by Red on 2017/12/8.
 */

public class Utils {
    public static String getSortName(int sort) {
        String sortString = "分类1";
        switch (sort) {
            case 1:
                sortString = "电子产品";
                break;
            case 2:
                sortString = "服装首饰";
                break;
            case 3:
                sortString = "书籍";
                break;
            default:
                sortString = "其他";
                break;
        }
        return sortString;
    }


    public static int getSortId(String sort) {
        int sortId = 4;
        if (TextUtils.equals(sort, "电子产品")){
            sortId = 1;
        }else if (TextUtils.equals(sort, "服装首饰")) {
            sortId = 2;
        }else if (TextUtils.equals(sort, "书籍")) {
            sortId = 3;
        }else {
            sortId = 4;
        }
        return sortId;
    }


    public static String getRootName(int root) {
        String rootString = "分类1";
        switch (root) {
            case 1:
                rootString = "管理员： ";
                break;
            case 2:
                rootString = "商品发布者： ";
                break;
            case 3:
                rootString = "普通会员： ";
                break;
            case 4:
                rootString = "访客： ";
                break;
        }
        return rootString;
    }
}
