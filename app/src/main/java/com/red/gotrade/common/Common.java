package com.red.gotrade.common;

/**
 * @author fyi.
 * @date 2017/12/3.
 * <p>
 * 常用且不变的参数
 */

public class Common {
    public interface Constance {
        //! 手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";
        //管理员大佬的token
        String SUPER_TOKEN = "0393ced4-df62-4111-8362-081c53fa5c51";
        // 基础的网络请求地址

        String IP = "自己的主机ip";//
        // String API_URL = "http://"+IP+":8080/qk/";//"qk/"
        String API_URL = "http://"+IP+":8080/"+"sjk/";// 主机地址
//        String API_URL = "http://"+IP+":8080/";//"qk/"

        // 最大的上传图片大小860kb
        long MAX_UPLOAD_IMAGE_LENGTH = 860 * 1024;
    }
}
