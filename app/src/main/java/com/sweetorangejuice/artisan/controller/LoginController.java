package com.sweetorangejuice.artisan.controller;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * @author fortuneliu
 * @version v0.1
 * LoginController类
 *      -登录功能
 *      -注册功能
 * Created by fortuneliu on 2017/5/2.
 */

public class LoginController {

    private static boolean SignUpState = false;             // 暂存注册情况，平时无用
    private static boolean SignInState = false;             // 暂存登录情况，平时无用
    private static AVUser user = new AVUser();              // 新建  AVUser 对象实例

    public static boolean SignUp(String username,String password,String email)
    {
        user.setUsername(username);                         // 设置用户名
        user.setPassword(password);                         // 设置密码
        user.setEmail(email);                               // 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    LoginController.SignUpState = true;     // 注册成功
                    Log.d("LoginController","注册成功");
                } else {
                    LoginController.SignUpState = false;    // 注册失败
                    Log.d("LoginController","注册失败");
                    e.printStackTrace();
                }
            }
        });
        if(LoginController.SignUpState) {
            LoginController.SignUpState = false;
            return true;
        }else
            return false;
    }

    public static boolean SignIn(String username,String password){
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    //登录成功
                    LoginController.SignInState = true;
                    Log.d("LoginController","登录成功");
                } else {
                    //登录失败
                    LoginController.SignInState = false;
                    Log.d("LoginController","登录失败");
                }
            }
        });
        if(LoginController.SignInState) {
            LoginController.SignInState = false;
            return true;
        }else
            return false;
    }

}
