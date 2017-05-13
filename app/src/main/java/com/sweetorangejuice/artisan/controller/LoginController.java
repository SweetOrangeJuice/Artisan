package com.sweetorangejuice.artisan.controller;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.sweetorangejuice.artisan.base.GlobalVariable;
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

    public static State SignUpState = State.OK;     //用以记录注册的状态
    public static int taskCount = 0;        //所需完成的异步任务的个数
    public static int taskFinished = 0;     //已经完成的异步任务的个数

    public enum State {       //当前的注册状态
        OK,     //注册成功
        FAILED  //注册失败
    }

    private static AVUser currentUser = null;        //当前正在注册的用户
    public static AVObject currentInfo = null;      //当前正在创建的信息

    /**
     * checkState函数
     *      负责在最后一个异步任务完成的时候
     *      检查当前的发送状态
     *      如果发送成功，则执行成功的代码
     *      如果发送不成功，则在云端删除已经上传的内容
     */
    public static void checkState()
    {
        if(taskCount == taskFinished) {     //当所有任务都完成时
            taskCount = 0;                  //任务记录数清零
            taskFinished = 0;               //任务完成数清零
            if (SignUpState != State.OK) {        //当注册不成功时
                LogUtil.d("MomentsController","Sign up Failed.");
                drawBack();                 //执行远端删除操作
                SignUpState = State.OK;
                LogUtil.d("LoginController", "注册失败");
                //Todo:这里写发布不成功的代码
                LoginFragment.onSignUpFailed();// 注册失败
            }else{                          //当注册成功时
                LogUtil.d("MomentsController","Sign up Succeed.");
                //Todo:这里写发布成功的代码
                LoginFragment.onSignUpSucceed();// 注册成功
                LogUtil.d("LoginController", "注册成功");
            }
        }
    }

    /**
     * 当发送出现异常时，删除远端服务器中已经上传的部分内容
     */
    private static void drawBack(){
        if(currentUser!=null){         //删除远端服务器中的用户
            currentUser.deleteInBackground();
        }
        if(currentInfo!=null){         //删除远端服务器中的图片
            currentInfo.deleteInBackground();
        }
    }

    /**
     * SignUp 注册函数
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     */
    public static void signUp(String username,String password,String email)
    {
        taskCount = 2;
        AVUser user = new AVUser();                         // 新建  AVUser 对象实例
        user.setUsername(username);                         // 设置用户名
        user.setPassword(password);                         // 设置密码
        user.setEmail(email);                               // 设置邮箱
        PersonalController.createPersonalInfo(username);  //创建对应用户信息表
        currentUser = user;
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                LoginController.taskFinished += 1;
                if (e == null) {
                    checkState();
                } else {
                    LogUtil.d("LoginController","user failed");
                    SignUpState = State.FAILED;
                    checkState();
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
    public static void signIn(final String username,String password){
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override                                           //后台登录函数
            public void done(AVUser avUser, AVException e) {
                if (e == null) {                                //登录成功
                    GlobalVariable.username = username;
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
        GlobalVariable.username = null;
        currentInfo = null;
        currentUser = null;
    }

}
