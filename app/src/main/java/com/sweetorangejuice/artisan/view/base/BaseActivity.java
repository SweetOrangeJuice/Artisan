package com.sweetorangejuice.artisan.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李易沾
 * Created by liyizhan on 2017/5/3.
 * 功能简述
 * 负责管理所有活动的基类
 * 拥有一些基本的管理方法
 * 如：利用Logcat查看活动、清除所有活动
 *     、获取活动数量
 */

public class BaseActivity extends AppCompatActivity {

    //管理活动的活动池
    private static List<Activity> sActivities=new ArrayList<>();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
