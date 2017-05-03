package com.sweetorangejuice.artisan.base;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

/**
 * @author fortuneliu,liyizhan
 * @version v0.1
 * 功能简述
 * 初始化应用时需要做的一些工作
 *      -初始化AVOSCloud
 *      -设置AVOSCloud的debug属性
 *
 *      -全局获取应用程序中的context
 * Created by fortuneliu on 2017/5/2.
 * Modified by liyizhan on 2017/5/3.
 */

public class ArtisanApplication extends Application {

    //整个应用程序中的context
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        // LeanCloud初始化
        AVOSCloud.initialize(this,"pm4CQDf9p2qq9uQloCWHrnma-9Nh9j0Va","H2syTkO74toNQkup9S5eP3mS");
        // 打开LeanCloud的Debug模式
        AVOSCloud.setDebugLogEnabled(true);

        //获取整个应用程序中的context
        sContext=getApplicationContext();
    }

    //获取整个应用程序中的context
    public static Context getContext(){
        return sContext;
    }
}
