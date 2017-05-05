package com.sweetorangejuice.artisan.controller;

import com.avos.avoscloud.AVUser;
import com.sweetorangejuice.artisan.base.ArtisanApplication;
import com.sweetorangejuice.artisan.base.BaseActivity;
import com.sweetorangejuice.artisan.view.Activity.LoginActivity;

/**
 * Created by as on 2017/5/5.
 */

public class PersonalController {


    public static void logOut(AVUser currentUser){
        if(currentUser!=null){
            LoginController.LogOut();
        }
        BaseActivity.finishAll();
        LoginActivity.startAction(ArtisanApplication.getContext());
    }

}
