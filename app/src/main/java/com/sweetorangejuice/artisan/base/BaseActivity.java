package com.sweetorangejuice.artisan.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sweetorangejuice.artisan.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李易沾
 * Created by liyizhan on 2017/5/3.
 * 功能简述
 * 负责管理所有活动的基类
 * 拥有一些基本的管理方法
 *
 *     -查看活动名的级别为DEBUG，标签为BaseActivity
 *     -清除所有活动
 *     -增删活动
 */

public class BaseActivity extends AppCompatActivity {

    //Logcat对应的标签
    private static final String TAG="BaseActivity";

    //管理活动的活动池
    private static List<Activity> sActivities=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取活动名
        LogUtil.d(TAG,getClass().getSimpleName());

        //增加当前活动进入活动池
        addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //在活动池中删除当前活动
        removeActivity(this);
    }

    //增加活动进入活动池
    private static void addActivity(Activity activity){
        sActivities.add(activity);
    }

    //从活动池中删除活动
    private static void removeActivity(Activity activity){
        sActivities.remove(activity);
    }

    //public：销毁应用程序中一切活动
    public static void finishAll(){
        for (Activity activity : sActivities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
