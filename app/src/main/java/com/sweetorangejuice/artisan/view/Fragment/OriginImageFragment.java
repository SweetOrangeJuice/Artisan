package com.sweetorangejuice.artisan.view.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.bumptech.glide.Glide;
import com.sweetorangejuice.artisan.R;

import java.util.List;

/**
 * Created by as on 2017/5/13.
 */

public class OriginImageFragment extends Fragment {

    private static final String ARG_IMAGE="arg_image";

    private ImageView mBackImageView;
    private ImageView mOriginImageView;

    public static Fragment newInstance(String image) {
        Bundle args=new Bundle();
        args.putString(ARG_IMAGE,image);
        OriginImageFragment fragment=new OriginImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_origin_image,container,false);

        mBackImageView=(ImageView)view.findViewById(R.id.fragment_origin_image_back);
        mOriginImageView=(ImageView)view.findViewById(R.id.fragment_origin_image_view);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        String objectId=getArguments().getString(ARG_IMAGE);
        setImage(objectId);

        return view;
    }

    private void setImage(final String objectId){
        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {
            byte[] bytes;

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                Glide.with(getActivity()).load(bytes).into(mOriginImageView);
            }

            @Override
            protected Integer doInBackground(String... params) {
                AVQuery<AVObject> query=new AVQuery<AVObject>("_File");
                query.whereEqualTo("objectId",objectId);
                List<AVObject> result;
                try {
                    result=query.find();
                    AVObject image=result.get(0);
                    AVFile file=AVFile.withAVObject(image);
                    bytes=file.getData();
                }catch (AVException e){
                    e.printStackTrace();
                }
                return 100;
            }
        }.execute(objectId);
    }
}
