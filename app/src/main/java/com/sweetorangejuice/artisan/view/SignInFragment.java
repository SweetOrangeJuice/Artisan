package com.sweetorangejuice.artisan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sweetorangejuice.artisan.R;

/**
 * Created by as on 2017/5/2.
 */

public class SignInFragment extends Fragment {

    private TextView mSkipTextView;
    private EditText mSignInAccountEditText;
    private EditText mSignInPasswordEditText;
    private Button mSignInButton;
    private Button mSignUpButton;
    private CheckBox mSignInRememberAccountCheckBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_in,container,false);

        //绑定视图
        mSkipTextView=(TextView)view.findViewById(R.id.sign_in_skip_text_view);
        mSignInAccountEditText=(EditText)view.findViewById(R.id.sign_in_account_edit_text);
        mSignInPasswordEditText=(EditText)view.findViewById(R.id.sign_in_password_edit_text);
        mSignInButton=(Button)view.findViewById(R.id.sign_in_button);
        mSignUpButton=(Button)view.findViewById(R.id.sign_up_button);
        mSignInRememberAccountCheckBox=(CheckBox)view.findViewById(R.id.sign_in_remember_account_check_box);


        //测试，以下正式发布时注释掉
        mSkipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"跳过 记住账号："+mSignInRememberAccountCheckBox.isChecked(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"登录 账号："+mSignInAccountEditText.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"注册 密码："+mSignInPasswordEditText.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
