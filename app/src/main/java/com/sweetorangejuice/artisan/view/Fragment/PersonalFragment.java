package com.sweetorangejuice.artisan.view.Fragment;

import android.os.AsyncTask;
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
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.FileController;
import com.sweetorangejuice.artisan.controller.PersonalController;
import com.sweetorangejuice.artisan.view.Activity.SubPersonalActivity;

import pl.droidsonroids.gif.GifTextView;

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
    private GifTextView mLoadingTextView;

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
        mIdolsLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_idols);
        mFollowersLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_followers);
        mMessagesLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_messages);
        mRedDotImageView=(ImageView)view.findViewById(R.id.fragment_personal_red_dot);
        mProfilesLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_personal_profiles);
        mLogOutButton=(Button)view.findViewById(R.id.fragment_personal_log_out);
        mLoadingTextView=(GifTextView)view.findViewById(R.id.fragment_personal_loading);

        //绑定事件
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalController.logOut(mCurrentUser);
            }
        });
        mMomentsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubPersonalActivity.actionStart(getActivity(),SubPersonalActivity.ACTION_MOMENTS);
            }
        });
        mCollectionsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubPersonalActivity.actionStart(getActivity(),SubPersonalActivity.ACTION_COLLECTIONS);
            }
        });
        mIdolsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubPersonalActivity.actionStart(getActivity(),SubPersonalActivity.ACTION_IDOLS);
            }
        });
        mFollowersLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubPersonalActivity.actionStart(getActivity(),SubPersonalActivity.ACTION_FOLLOWERS);
            }
        });
        mMessagesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"此功能暂未开放！", Toast.LENGTH_SHORT).show();
                //SubPersonalActivity.actionStart(getActivity(),SubPersonalActivity.ACTION_MESSAGES);
            }
        });
        mProfilesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubPersonalActivity.actionStart(getActivity(),SubPersonalActivity.ACTION_PROFILES);
            }
        });

        //视图初始化
        //updateUI();

        //loadHeadPortrait();

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
            loadHeadPortrait();
        }else{
            mLogOutButton.setText(R.string.fragment_personal_login);
            mNameTextView.setText(R.string.tourist);
            Glide.with(getActivity()).load(R.drawable.head_portrait).into(mHeadPortraitImageView);
            mHeadPortraitImageView.setVisibility(View.VISIBLE);
            mLoadingTextView.setVisibility(View.GONE);
        }
    }

    private void loadHeadPortrait(){
        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {
            byte[] img;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mLoadingTextView.setVisibility(View.VISIBLE);
                mHeadPortraitImageView.setVisibility(View.GONE);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                Glide.with(getActivity()).load(img).into(mHeadPortraitImageView);
                mLoadingTextView.setVisibility(View.GONE);
                mHeadPortraitImageView.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(String... params) {
                AVQuery<AVObject> query=new AVQuery<AVObject>("Person");
                query.whereEqualTo("username",AVUser.getCurrentUser().getUsername());
                AVObject result;
                try {
                    result=query.getFirst();
                    img=FileController.getThumbnailbyObjectId((String)result.get("headImage"),100,100);
                }catch (AVException e){
                    e.printStackTrace();
                }

                return 100;
            }
        }.execute();
    }
}
