package com.sweetorangejuice.artisan.view;

import android.graphics.Color;
import android.os.Bundle;
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

import com.sweetorangejuice.artisan.R;

/**
 * @author 李易沾
 * Created by liyizhan in 2017/5/2
 * 功能简述
 * 登录和注册的Fragment
 */

public class LoginFragment extends Fragment {

    //判断登录还是注册的值,false代表登录，true代表注册
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

        //首次打开，显示登陆视图
        updateLoginUI(false);

        return view;
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
            mSignInButton.setBackgroundColor(0xFF6495ED);
            mSignUpButton.setBackgroundColor(Color.TRANSPARENT);
        }else{
            mSignInLinearLayout.setVisibility(View.GONE);
            mSignUpLinearLayout.setVisibility(View.VISIBLE);
            mSignInButton.setBackgroundColor(Color.TRANSPARENT);
            mSignUpButton.setBackgroundColor(0xFF6495ED);
        }
    }
}
