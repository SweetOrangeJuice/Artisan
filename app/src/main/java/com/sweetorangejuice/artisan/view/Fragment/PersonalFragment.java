package com.sweetorangejuice.artisan.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.PersonalController;

/**
 * Created by as on 2017/5/3.
 */

public class PersonalFragment extends Fragment {

    //视图组件
    private ImageView mSettingImageView;
    private ImageView mHeadPortraitImageView;
    private TextView mNameTextView;
    private LinearLayout mMomentsLinearLayout;
    private LinearLayout mCollectionsLinearLayout;
    private LinearLayout mIdolsLinearLayout;
    private LinearLayout mFollowersLinearLayout;
    private LinearLayout mMessagesLinearLayout;
    private ImageView mRedDotImageView;
    private LinearLayout mProfilesLinearLayout;
    private Button mLogOutButton;

    //当前用户
    private AVUser mCurrentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser=AVUser.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_personal,container,false);

        //绑定视图
        mSettingImageView=(ImageView)view.findViewById(R.id.fragment_personal_setting);
        mHeadPortraitImageView=(ImageView)view.findViewById(R.id.fragment_personal_head_portrait);
        mNameTextView=(TextView)view.findViewById(R.id.fragment_personal_name);
        mMomentsLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_moments);
        mCollectionsLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_collections);
        mMessagesLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_messages);
        mRedDotImageView=(ImageView)view.findViewById(R.id.fragment_personal_red_dot);
        mProfilesLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_profiles);
        mLogOutButton=(Button)view.findViewById(R.id.fragment_personal_log_out);

        //绑定事件

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalController.logOut(mCurrentUser);
            }
        });

        //视图初始化
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //恢复到前台可见的时候重新获取一次mCurrentUser
        mCurrentUser=AVUser.getCurrentUser();
        updateUI();
    }

    /**
     * newInstance:返回PersonalFragment的一个实例
     * @return  PersonalFragment的一个实例
     */
    public static Fragment newInstance(){
        return new PersonalFragment();
    }

    private void updateUI(){
        if(mCurrentUser!=null){
            mLogOutButton.setText(R.string.fragment_personal_log_out);
            String name=mCurrentUser.getUsername();
            mNameTextView.setText(name);
        }else{
            mLogOutButton.setText(R.string.fragment_personal_login);
            mNameTextView.setText(R.string.tourist);
        }
    }
}
