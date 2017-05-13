package com.sweetorangejuice.artisan.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.base.ArtisanApplication;
import com.sweetorangejuice.artisan.model.MomentForItem;

import java.util.List;

/**
 * Created by as on 2017/5/13.
 */

public class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.ViewHolder>{

    private List<MomentForItem> mMomentForItems;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        ImageView mHeadPortrait;
        TextView mAccount;
        TextView mContent;
        RelativeLayout mImageCanvas;
        RelativeLayout mImagesCanvas;
        ImageView mBigImage;
        ImageView mImg1;
        ImageView mImg2;
        ImageView mImg3;
        ImageView mImg4;
        ImageView mImg5;
        ImageView mImg6;
        ImageView mImg7;
        ImageView mImg8;
        ImageView mImg9;
        TextView mComment;
        TextView mLike;
        TextView mCollect;
        boolean mIsLike=false;
        boolean mIsCollect=false;

        public ViewHolder(View view){
            super(view);
            mView=view;
            mHeadPortrait=(ImageView)view.findViewById(R.id.head_portrait);
            mAccount=(TextView)view.findViewById(R.id.account);
            mContent=(TextView)view.findViewById(R.id.content);
            mImageCanvas=(RelativeLayout)view.findViewById(R.id.image_canvas);
            mImagesCanvas=(RelativeLayout)view.findViewById(R.id.images_canvas);
            mBigImage=(ImageView)view.findViewById(R.id.big_image);
            mImg1=(ImageView)view.findViewById(R.id.img_1);
            mImg2=(ImageView)view.findViewById(R.id.img_2);
            mImg3=(ImageView)view.findViewById(R.id.img_3);
            mImg4=(ImageView)view.findViewById(R.id.img_4);
            mImg5=(ImageView)view.findViewById(R.id.img_5);
            mImg6=(ImageView)view.findViewById(R.id.img_6);
            mImg7=(ImageView)view.findViewById(R.id.img_7);
            mImg8=(ImageView)view.findViewById(R.id.img_8);
            mImg9=(ImageView)view.findViewById(R.id.img_9);
            mComment=(TextView)view.findViewById(R.id.comment);
            mLike=(TextView)view.findViewById(R.id.like);
            mCollect=(TextView)view.findViewById(R.id.collect);
        }
    }

    public MomentAdapter(List<MomentForItem> momentForItems){
        mMomentForItems=momentForItems;
    }

    @Override
    public int getItemCount() {
        return mMomentForItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MomentForItem momentForItem=mMomentForItems.get(position);
        Glide.with(ArtisanApplication.getContext()).load(momentForItem.getHeadPortrait()).into(holder.mHeadPortrait);
        holder.mAccount.setText(momentForItem.getAccount());
        holder.mContent.setText(momentForItem.getContent());
        if(momentForItem.getImagesList().size()<=0){
            holder.mImageCanvas.setVisibility(View.GONE);
            holder.mImagesCanvas.setVisibility(View.GONE);
        }else if(momentForItem.getImagesList().size()==1){
            holder.mImageCanvas.setVisibility(View.VISIBLE);
            holder.mImagesCanvas.setVisibility(View.GONE);
            holder.mBigImage.setVisibility(View.VISIBLE);
            Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(0)).into(holder.mBigImage);
        }else {
            holder.mImageCanvas.setVisibility(View.GONE);
            holder.mImagesCanvas.setVisibility(View.VISIBLE);
            switch (momentForItem.getImagesList().size()){
                case 9:
                    holder.mImg9.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(8)).into(holder.mImg9);
                case 8:
                    holder.mImg8.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(7)).into(holder.mImg8);
                case 7:
                    holder.mImg7.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(6)).into(holder.mImg7);
                case 6:
                    holder.mImg6.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(5)).into(holder.mImg6);
                case 5:
                    holder.mImg5.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(4)).into(holder.mImg5);
                case 4:
                    holder.mImg4.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(3)).into(holder.mImg4);
                case 3:
                    holder.mImg3.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(2)).into(holder.mImg3);
                case 2:
                    holder.mImg2.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(1)).into(holder.mImg2);
                    holder.mImg1.setVisibility(View.VISIBLE);
                    Glide.with(ArtisanApplication.getContext()).load(momentForItem.getImagesList().get(0)).into(holder.mImg1);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moment_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:此处可添加点击整个动态的事件
            }
        });
        holder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:此处待添加点击评论跳转至详情页的事件
            }
        });
        holder.mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.mIsLike){
                    holder.mLike.setBackgroundResource(R.drawable.fragment_seleted_button);
                    holder.mIsLike=true;
                    //TODO:此处待添加后台增加动态的点赞数
                    MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                }else{
                    holder.mLike.setBackgroundResource(R.drawable.fragment_unseleted_button);
                    holder.mIsLike=false;
                    //TODO:此处待添加后台减少动态的点赞数
                    MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                }
            }
        });
        holder.mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.mIsCollect){
                    holder.mCollect.setBackgroundResource(R.drawable.fragment_seleted_button);
                    holder.mIsCollect=true;
                    //TODO:此处待添加后台增加动态的点赞数
                    MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                }else{
                    holder.mCollect.setBackgroundResource(R.drawable.fragment_unseleted_button);
                    holder.mIsCollect=false;
                    //TODO:此处待添加后台减少动态的点赞数
                    MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                }
            }
        });

        //TODO:以下的图片待添加点击查看大图事件

        return holder;
    }
}
