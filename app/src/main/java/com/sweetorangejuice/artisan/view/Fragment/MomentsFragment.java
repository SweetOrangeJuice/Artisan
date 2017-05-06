package com.sweetorangejuice.artisan.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVUser;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.MomentsController;

/**
 * Created by as on 2017/5/3.
 */

public class MomentsFragment extends Fragment{

    private AVUser mCurrentUser;

    private Button mLoginButton;
    private RelativeLayout mBlockRelativeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser=AVUser.getCurrentUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentUser=AVUser.getCurrentUser();
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_moments,container,false);

        mLoginButton=(Button)view.findViewById(R.id.fragment_moments_login);
        mBlockRelativeLayout=(RelativeLayout)view.findViewById(R.id.fragment_moments_block_layout);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentsController.login();
            }
        });

        updateUI();

        return view;
    }

    /**
     * newInstance:返回MomentsFragment的一个实例
     * @return  MomentsFragment的一个实例
     */
    public static Fragment newInstance(){
        return new MomentsFragment();
    }

    private void updateUI(){
        if(mCurrentUser!=null){
            mBlockRelativeLayout.setVisibility(View.GONE);
        }else{
            mBlockRelativeLayout.setVisibility(View.VISIBLE);
        }
    }
}
