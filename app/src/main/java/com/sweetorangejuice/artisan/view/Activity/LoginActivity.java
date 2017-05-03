package com.sweetorangejuice.artisan.view.Activity;

import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.view.Fragment.LoginFragment;
import com.sweetorangejuice.artisan.view.base.SingleFragmentActivity;

/**
 * @author 李易沾
 * Created by liyizhan in 2017/5/2
 * 功能简述
 * 登录和注册的Activity
 */

public class LoginActivity extends SingleFragmentActivity {

    /**
     * 创建登录界面Fragment
     * @return  登录界面Fragment
     */
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
