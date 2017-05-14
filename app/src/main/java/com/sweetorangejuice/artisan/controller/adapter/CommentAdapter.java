package com.sweetorangejuice.artisan.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.model.Comment;

import java.util.List;

/**
 * Created by as on 2017/5/14.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<Comment> mComments;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView mAccount;
        TextView mText;

        public ViewHolder(View view){
            super(view);
            mView=view;
            mAccount=(TextView)view.findViewById(R.id.comment_account);
            mText=(TextView)view.findViewById(R.id.comment_text);
        }
    }

    public CommentAdapter(List<Comment> comments){
        mComments=comments;
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Comment comment=mComments.get(position);

        holder.mAccount.setText(comment.getAccount()+":");
        holder.mText.setText(comment.getText());

        holder.mAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:跳转到对应个人资料页
            }
        });
    }
}
