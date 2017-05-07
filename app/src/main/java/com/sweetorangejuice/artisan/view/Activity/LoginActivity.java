package com.sweetorangejuice.artisan.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;
import com.sweetorangejuice.artisan.view.Fragment.LoginFragment;

/**
 * @author 李易沾
 * Created by liyizhan in 2017/5/2
 * 功能简述
 * 登录和注册的Activity
 */

public class LoginActivity extends SingleFragmentActivity {

    public static void startAction(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 创建登录界面Fragment
     * @return  登录界面Fragment
     */
    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }
}
