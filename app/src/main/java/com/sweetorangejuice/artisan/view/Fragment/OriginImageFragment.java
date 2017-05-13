package com.sweetorangejuice.artisan.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sweetorangejuice.artisan.R;

/**
 * Created by as on 2017/5/13.
 */

public class OriginImageFragment extends Fragment {

    private static final String ARG_IMAGE="arg_image";

    private ImageView mBackImageView;
    private ImageView mOriginImageView;

    public static Fragment newInstance(byte[] image) {
        Bundle args=new Bundle();
        args.putByteArray(ARG_IMAGE,image);
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

        byte[] image=getArguments().getByteArray(ARG_IMAGE);
        Glide.with(getActivity()).load(image).into(mOriginImageView);

        return view;
    }
}
