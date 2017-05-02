package com.sweetorangejuice.artisan.controller;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * @author fortuneliu
 * @version v0.1
 * 功能简述
 * 初始化应用时需要做的一些工作
 *      -初始化AVOSCloud
 *      -设置AVOSCloud的debug属性
 * Created by fortuneliu on 2017/5/2.
 */

public class ArtisanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        // LeanCloud初始化
        AVOSCloud.initialize(this,"pm4CQDf9p2qq9uQloCWHrnma-9Nh9j0Va","H2syTkO74toNQkup9S5eP3mS");
        // 打开LeanCloud的Debug模式
        AVOSCloud.setDebugLogEnabled(true);
    }
}
