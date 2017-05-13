package com.sweetorangejuice.artisan.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sweetorangejuice.artisan.R;

/**
 * Created by as on 2017/5/13.
 */

public class DetailFragment extends Fragment {

    private ImageView mBackImageView;
    private RecyclerView mRecyclerView;
    private EditText mContentEditText;
    private Button mCommentButton;

    public static Fragment newInstance(){
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail,container,false);

        mBackImageView=(ImageView)view.findViewById(R.id.fragment_detail_back);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.fragment_detail_recycler_view);
        mContentEditText=(EditText)view.findViewById(R.id.fragment_detail_edit_text);
        mCommentButton=(Button)view.findViewById(R.id.fragment_detail_comment);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }
}
