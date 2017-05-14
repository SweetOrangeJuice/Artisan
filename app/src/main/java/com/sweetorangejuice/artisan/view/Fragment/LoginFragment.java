package com.sweetorangejuice.artisan.view.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.base.ArtisanApplication;
import com.sweetorangejuice.artisan.base.BaseActivity;
import com.sweetorangejuice.artisan.controller.LoginController;
import com.sweetorangejuice.artisan.view.Activity.MainActivity;

/**
 * @author 李易沾
 * Created by liyizhan in 2017/5/2
 * 功能简述
 * 登录和注册的Fragment
 */

public class LoginFragment extends Fragment {

    private AVUser mCurrentUser;

    //SharedPreferences相关常量
    private static final String SHARED_PREFERENCES_REMEMBER="remember";
    private static final String SHARED_PREFERENCES_ACCOUNT="account";

    //判断登录还是注册选项卡值,false代表登录，true代表注册
    private boolean mLoginStatus;

    //非登录或注册视图
    private Button mSignInButton;
    private Button mSignUpButton;
    private TextView mSkipTextView;

    //以下为登录的视图
    private LinearLayout mSignInLinearLayout;
    private EditText mSignInAccountEditText;
    private EditText mSignInPasswordEditText;
    private CheckBox mSignInRememberAccountCheckBox;
    private Button mLoginSignInButton;

    //以下为注册的视图
    private LinearLayout mSignUpLinearLayout;
    private EditText mSignUpAccountEditText;
    private EditText mSignUpPasswordEditText;
    private EditText mSignUpEmailEditText;
    private Button mLoginSignUpButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser=AVUser.getCurrentUser();
        if(mCurrentUser!=null){
            BaseActivity.finishAll();
            jump();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);

        //绑定视图
        mSignInLinearLayout=(LinearLayout)view.findViewById(R.id.sign_in_linear_layout);
        mSkipTextView=(TextView)view.findViewById(R.id.sign_in_skip_text_view);
        mSignInAccountEditText=(EditText)view.findViewById(R.id.sign_in_account_edit_text);
        mSignInPasswordEditText=(EditText)view.findViewById(R.id.sign_in_password_edit_text);
        mSignInButton=(Button)view.findViewById(R.id.sign_in_button);
        mSignUpButton=(Button)view.findViewById(R.id.sign_up_button);
        mSignInRememberAccountCheckBox=(CheckBox)view.findViewById(R.id.sign_in_remember_account_check_box);
        mLoginSignInButton=(Button)view.findViewById(R.id.login_sign_in_button);
        mSignUpLinearLayout=(LinearLayout)view.findViewById(R.id.sign_up_linear_layout);
        mSignUpAccountEditText=(EditText)view.findViewById(R.id.sign_up_account_edit_text);
        mSignUpPasswordEditText=(EditText)view.findViewById(R.id.sign_up_password_edit_text);
        mSignUpEmailEditText=(EditText)view.findViewById(R.id.sign_up_email_edit_text);
        mLoginSignUpButton=(Button)view.findViewById(R.id.login_sign_up_button);

        //监听事件
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLoginUI(false);
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLoginUI(true);
            }
        });
        mLoginSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mLoginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        mSkipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.finishAll();
                skip();
            }
        });

        //首次打开，显示登陆视图
        updateLoginUI(false);

        //若用户记住了账户，自动填写账户
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isRemember=sharedPreferences.getBoolean(SHARED_PREFERENCES_REMEMBER,false);
        if(isRemember){
            mSignInRememberAccountCheckBox.setChecked(true);
            mSignInAccountEditText.setText(sharedPreferences.getString(SHARED_PREFERENCES_ACCOUNT,""));
        }

        return view;
    }

    /**
     * skip:用户点击跳过后触发的方法
     */
    private void skip(){
        jump();
    }

    /**
     * signUp:注册相关函数
     */
    private void signUp(){
        //boolean isSignUpSuccess;
        String username=mSignUpAccountEditText.getText().toString();
        String password=mSignUpPasswordEditText.getText().toString();
        String email=mSignUpEmailEditText.getText().toString();
        //isSignUpSuccess= LoginController.signUp(username,password,email);
        //LogUtil.d("TAG",isSignUpSuccess+"");
        LoginController.signUp(username,password,email);
        rememberAccount(true,username);
        /*
        if(isSignUpSuccess){
            jump();
        }else{
            //日后注册失败可以替换成别的提示语句
            Toast.makeText(getActivity(),getString(R.string.sign_up_failed),Toast.LENGTH_SHORT).show();
        }
        */
    }

    /**
     * sign:登录相关函数
     */
    private void signIn(){
        //boolean isSignInSuccess;
        String username=mSignInAccountEditText.getText().toString();
        String password=mSignInPasswordEditText.getText().toString();
        //isSignInSuccess=LoginController.signIn(username,password);
        LoginController.signIn(username,password);
        rememberAccount(mSignInRememberAccountCheckBox.isChecked(),username);
        /*
        if(isSignInSuccess){
            //检查是否记住了账号，是则保存在SharedPreferences中，否则清除记住状态
            rememberAccount(mSignInRememberAccountCheckBox.isChecked(),username);

            jump();
        }else{
            //日后登录失败可以替换成别的提示语句
            Toast.makeText(getActivity(),getString(R.string.sign_in_failed),Toast.LENGTH_SHORT).show();
        }
        */
    }

    /**
     * rememberAccount:检查是否记住了账号，是则保存在SharedPreferences中，否则清除记住状态
     * @param isChecked 记住账号框的勾选状态
     * @param account   用户名
     */
    private void rememberAccount(boolean isChecked,String account){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isChecked){
            editor.putBoolean(SHARED_PREFERENCES_REMEMBER,true);
            editor.putString(SHARED_PREFERENCES_ACCOUNT,account);
        }else{
            editor.clear();
        }
        editor.apply();
    }


    /**
     * updateLoginUI 当用户点击登录或者注册按钮的时候，更新各自视图
     * @param loginStatus 现在的登录或者注册状态
     */
    private void updateLoginUI(boolean loginStatus){
        mLoginStatus=loginStatus;
        if(!mLoginStatus){
            mSignInLinearLayout.setVisibility(View.VISIBLE);
            mSignUpLinearLayout.setVisibility(View.GONE);
            mSignInButton.setBackgroundColor(Color.BLACK);
            mSignInButton.setTextColor(Color.WHITE);
            mSignUpButton.setBackgroundColor(Color.TRANSPARENT);
            mSignUpButton.setTextColor(Color.BLACK);
        }else{
            mSignInLinearLayout.setVisibility(View.GONE);
            mSignUpLinearLayout.setVisibility(View.VISIBLE);
            mSignInButton.setBackgroundColor(Color.TRANSPARENT);
            mSignInButton.setTextColor(Color.BLACK);
            mSignUpButton.setBackgroundColor(Color.BLACK);
            mSignUpButton.setTextColor(Color.WHITE);
        }
    }

    /**
     * jump跳转到主活动并终结登录相关页面
     */
    private static void jump(){
        BaseActivity.finishAll();
        MainActivity.actionStart(ArtisanApplication.getContext());
    }

    /**
     * newInstance:返回LoginFragment的一个实例
     * @return  LoginFragment的一个实例
     */
    public static Fragment newInstance(){
        return new LoginFragment();
    }

    public static void onSignUpSucceed(){
        BaseActivity.finishAll();
        jump();
    }

    public static void onSignUpFailed(){
        Toast.makeText(ArtisanApplication.getContext(),
                "注册失败",Toast.LENGTH_SHORT).show();
    }

    public static void onSignInSucceed(){
        BaseActivity.finishAll();
        jump();
    }

    public static void onSignInFailed(){
        Toast.makeText(ArtisanApplication.getContext(),
                "登录失败",Toast.LENGTH_SHORT).show();
    }
}
