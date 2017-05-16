package com.sweetorangejuice.artisan.view.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.MomentsController;
import com.sweetorangejuice.artisan.controller.adapter.CommentAdapter;
import com.sweetorangejuice.artisan.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/5/13.
 */

public class DetailFragment extends Fragment {

    private static final String ARG_OBJECT_ID="arg_object_id";

    private AVUser mCurrentUser;
    private String mObjectId;
    private List<Comment> mComments=new ArrayList<>();
    private CommentAdapter mAdapter;

    private ImageView mBackImageView;
    private RecyclerView mRecyclerView;
    private EditText mContentEditText;
    private Button mCommentButton;
    private RelativeLayout mLoadingLayout;
    private LinearLayout mBottomLayout;

    public static Fragment newInstance(String objectId){
        Bundle args=new Bundle();
        args.putString(ARG_OBJECT_ID,objectId);
        Fragment fragment=new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mObjectId=getArguments().getString(ARG_OBJECT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail,container,false);

        mBackImageView=(ImageView)view.findViewById(R.id.fragment_detail_back);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.fragment_detail_recycler_view);
        mContentEditText=(EditText)view.findViewById(R.id.fragment_detail_edit_text);
        mCommentButton=(Button)view.findViewById(R.id.fragment_detail_comment);
        mLoadingLayout=(RelativeLayout)view.findViewById(R.id.fragment_detail_loading);
        mBottomLayout=(LinearLayout)view.findViewById(R.id.fragment_detail_linear_layout);

        mCurrentUser=AVUser.getCurrentUser();
        if(mCurrentUser==null){
            mContentEditText.setHint("你尚未登录");
            mContentEditText.setEnabled(false);
            mCommentButton.setEnabled(false);
        }else {
            mContentEditText.setHint("在此处评论");
            mContentEditText.setEnabled(true);
            mCommentButton.setEnabled(true);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new CommentAdapter(mComments);
        mRecyclerView.setAdapter(mAdapter);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContentEditText.length()<=0||mContentEditText.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"文本框不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    String text=mContentEditText.getText().toString();
                    MomentsController.Comments(AVUser.getCurrentUser().getUsername(),mObjectId,text);
                    mContentEditText.setText("");
                    refresh(text);
                }
            }
        });

        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {
            List<Comment> result;
            @Override
            protected Integer doInBackground(String... params) {
                result=MomentsController.getComments(params[0]);
                return 100;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                mComments.clear();
                for(int i=0;i<result.size();i++){
                    mComments.add(result.get(i));
                }
                mAdapter.notifyDataSetChanged();
                mLoadingLayout.setVisibility(View.GONE);
                mBottomLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }.execute(mObjectId);

        return view;
    }

    private void refresh(String text){
        if(text.equals("")||text.length()<=0){
            Toast.makeText(getActivity(),"文本框不能为空",Toast.LENGTH_SHORT).show();
        }else{
            Comment comment=new Comment();
            comment.setText(text);
            comment.setAccount(AVUser.getCurrentUser().getUsername());
            mComments.add(comment);
            mAdapter.notifyDataSetChanged();
        }
    }
}
