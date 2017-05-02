package com.sweetorangejuice.artisan.view;

import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;

public class LoginActivity extends SingleFragmentActivity {

    /**
     * 创建登录界面Fragment
     * @return  登录界面Fragment
     */
    @Override
    protected Fragment createFragment() {
        return new SignInFragment();
    }
}
