package com.sweetorangejuice.artisan.view.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.sweetorangejuice.artisan.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by as on 2017/5/15.
 */

public class OthersProfilesFragment extends Fragment {

    private static final String ARG_USERNAME="username";

    private ImageView mBackImageView;
    private CircleImageView mHeadPortraitImageView;
    private TextView mAccountTextView;
    private TextView mGenderTextView;
    private TextView mAgeTextView;
    private TextView mSchoolTextView;
    private TextView mLabelTextView;
    private RelativeLayout mLoadingRelativeLayout;
    private LinearLayout mDataLinearLayout;

    public static Fragment newInstance(String username){
        Bundle args=new Bundle();
        args.putString(ARG_USERNAME,username);
        Fragment fragment=new OthersProfilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_others_profiles,container,false);

        mBackImageView=(ImageView)view.findViewById(R.id.fragment_others_profiles_back);
        mHeadPortraitImageView=(CircleImageView)view.findViewById(R.id.fragment_sub_others_head_portrait);
        mAccountTextView=(TextView)view.findViewById(R.id.fragment_sub_others_name);
        mGenderTextView=(TextView)view.findViewById(R.id.fragment_sub_others_profiles_gender_value);
        mAgeTextView=(TextView)view.findViewById(R.id.fragment_sub_others_profiles_age_value);
        mSchoolTextView=(TextView)view.findViewById(R.id.fragment_sub_personal_others_school_value);
        mLabelTextView=(TextView)view.findViewById(R.id.fragment_sub_others_profiles_tag_value);
        mLoadingRelativeLayout=(RelativeLayout)view.findViewById(R.id.fragment_sub_others_loading);
        mDataLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_others_profiles_data);

        loadingProfiles();
        mAccountTextView.setText(getArguments().getString(ARG_USERNAME));

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void loadingProfiles(){
        String username=getArguments().getString(ARG_USERNAME);
        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {
            String gender="NULL";
            String age="NULL";
            String school="NULL";
            String label="NULL";
            byte[] headPortrait;

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                mGenderTextView.setText(gender);
                mAgeTextView.setText(age);
                mSchoolTextView.setText(school);
                mLabelTextView.setText(label);
                //TODO:获取了头像数据流后，解除下方注释
                //Glide.with(getActivity()).load(headPortrait).into(mHeadPortraitImageView);

                mLoadingRelativeLayout.setVisibility(View.GONE);
                mDataLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(String... params) {
                AVQuery<AVObject> query=new AVQuery<AVObject>("Person");
                query.whereEqualTo("username",params[0]);
                List<AVObject> result;
                try {
                    result=query.find();
                    //TODO:获取gender，age，school，label,head portrait；并解除下方注释
                    //headPortrait=FileController.getPicturebyObjectId();
                }catch (AVException e){
                    e.printStackTrace();
                }

                return 100;
            }
        }.execute(username);
    }
}
