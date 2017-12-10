package com.red.gotrade;

import android.app.Application;
import android.util.Log;

import com.red.gotrade.common.Common;
import com.red.gotrade.db.DbHelper;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;
import com.vise.utils.assist.SSLUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.interceptor.HttpLogInterceptor;
import com.vise.xsnow.loader.GlideLoader;
import com.vise.xsnow.loader.LoaderManager;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author fyi.
 * @date 2017/12/3.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initLog();
        initToasty();
        initNet();

        DbHelper.getInstance().init(this);
        LoaderManager.setLoader(new GlideLoader());//可定制图片加载库Glide
        LoaderManager.getLoader().init(this);
    }

    private void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("VLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }

    private void initToasty() {
        Toasty.Config.getInstance()
                //.setErrorColor(Color.parseColor("#D50000")) // optional
                //.setInfoColor(@ColorInt int infoColor) // optional
                //.setSuccessColor(@ColorInt int successColor) // optional
                //.setWarningColor(@ColorInt int warningColor) // optional
                //.setTextColor(@ColorInt int textColor) // optional
                //.tintIcon(boolean tintIcon) // optional (apply textColor also to the icon)
                //.setToastTypeface(@NonNull Typeface typeface) // optional
                //.setTextSize(int sizeInSp) // optional
                .apply(); // required
    }

    private void initNet() {
        ViseHttp.init(this);

        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(Common.Constance.API_URL)
                //配置全局请求头
                .globalHeaders(new HashMap<String, String>())
                //配置全局请求参数
                .globalParams(new HashMap<String, String>())
                //配置读取超时时间，单位秒
                .readTimeout(30)
                //配置写入超时时间，单位秒
                .writeTimeout(30)
                //配置连接超时时间，单位秒
                .connectTimeout(30)
                //配置请求失败重试次数
                .retryCount(3)
                //配置请求失败重试间隔时间，单位毫秒
                .retryDelayMillis(1000)
                //配置是否使用cookie
                .setCookie(true)
                //配置自定义cookie
//                .apiCookie(new ApiCookie(this))
                //配置是否使用OkHttp的默认缓存
                .setHttpCache(false)//true
                //配置OkHttp缓存路径
//                .setHttpCacheDirectory(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
                //配置自定义OkHttp缓存
//                .httpCache(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义离线缓存
//                .cacheOffline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义在线缓存
//                .cacheOnline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置开启Gzip请求方式，需要服务器支持
//                .postGzipInterceptor()
                //配置应用级拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY))
                //配置网络拦截器
//                .networkInterceptor(new NoCacheInterceptor())
                //配置转换工厂
                .converterFactory(GsonConverterFactory.create())
                //配置适配器工厂
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
                //配置请求工厂
//                .callFactory(new Call.Factory() {
//                    @Override
//                    public Call newCall(Request request) {
//                        return null;
//                    }
//                })
                //配置连接池
//                .connectionPool(new ConnectionPool())
                //配置主机证书验证
                .hostnameVerifier(new SSLUtil.UnSafeHostnameVerifier("http://192.168.199.100/"))
                //配置SSL证书验证
                .SSLSocketFactory(SSLUtil.getSslSocketFactory(null, null, null));
        //配置主机代理
//                .proxy(new Proxy(Proxy.Type.HTTP, new SocketAddress() {}))

    }

}


