package com.sweetorangejuice.artisan.controller;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.sweetorangejuice.artisan.util.LogUtil;
import com.sweetorangejuice.artisan.view.Fragment.LoginFragment;

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
    private static boolean isModified = false;

    /**
     * SignUp 注册函数
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     */
    public static void signUp(String username,String password,String email)
    {
        AVUser user = new AVUser();                         // 新建  AVUser 对象实例
        user.setUsername(username);                         // 设置用户名
        user.setPassword(password);                         // 设置密码
        user.setEmail(email);                               // 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    LoginFragment.onSignUpSucceed();// 注册成功
                    LogUtil.d("LoginController","注册成功");
                } else {
                    LoginFragment.onSignUpFailed();// 注册失败
                    LogUtil.d("LoginController","注册失败");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * SignIn函数
     * @param username
     * @param password
     */
    public static void signIn(String username,String password){
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override                                           //后台登录函数
            public void done(AVUser avUser, AVException e) {
                if (e == null) {                                //登录成功
                    LoginFragment.onSignInSucceed();
                    LogUtil.d("LoginController","登录成功");
                } else {                                        //登录失败
                    LoginFragment.onSignInFailed();
                    LogUtil.d("LoginController","登录失败");
                }
            }
        });

    }

    /**
     * LogOut 退出登录
     */
    public static void LogOut(){
        AVUser.getCurrentUser().logOut();
    }

}
