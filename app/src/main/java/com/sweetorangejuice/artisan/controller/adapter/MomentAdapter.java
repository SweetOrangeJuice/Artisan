package com.sweetorangejuice.artisan.controller.adapter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.base.ArtisanApplication;
import com.sweetorangejuice.artisan.base.GlobalVariable;
import com.sweetorangejuice.artisan.controller.MomentsController;
import com.sweetorangejuice.artisan.model.MomentForItem;
import com.sweetorangejuice.artisan.view.Activity.DetailActivity;
import com.sweetorangejuice.artisan.view.Activity.OriginImageActivity;

import java.util.List;

/**
 * Created by as on 2017/5/13.
 */

public class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.ViewHolder>{

    private List<MomentForItem> mMomentForItems;
    private AVUser mCurrentUser;

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
        final ViewHolder holder1=holder;
        Log.d("TAG","bind??"+holder1.toString());
        MomentForItem momentForItem=mMomentForItems.get(position);

        AsyncTask<MomentForItem,Integer,Integer> task=new AsyncTask<MomentForItem, Integer, Integer>() {
            boolean isLike=false;
            boolean isCollected=false;

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if(isLike){
                    holder1.mLike.setBackgroundResource(R.drawable.fragment_seleted_button);
                    holder1.mIsLike=true;
                }else{
                    holder1.mLike.setBackgroundResource(R.drawable.fragment_unseleted_button);
                    holder1.mIsLike=false;
                }

                if(isCollected){
                    holder1.mCollect.setBackgroundResource(R.drawable.fragment_seleted_button);
                    holder1.mIsCollect=true;
                }else{
                    holder1.mCollect.setBackgroundResource(R.drawable.fragment_unseleted_button);
                    holder1.mIsCollect=false;
                }
            }

            @Override
            protected Integer doInBackground(MomentForItem... params) {
                Log.d("haha",GlobalVariable.username+"+"+params[0].getObjectId());
                isLike=MomentsController.isLike(GlobalVariable.username,params[0].getObjectId());
                isCollected=MomentsController.isCollect(GlobalVariable.username,params[0].getObjectId());
                return 100;
            }
        }.execute(momentForItem);

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
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moment_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.mHeadPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:此处待添加点击头像跳转到对应用户个人资料页
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:此处可添加点击整个动态的事件
            }
        });
        holder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                DetailActivity.actionStart(parent.getContext(),momentForItem.getObjectId());
            }
        });
        holder.mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentUser=AVUser.getCurrentUser();
                if(mCurrentUser!=null){
                    if(!holder.mIsLike){
                        holder.mLike.setBackgroundResource(R.drawable.fragment_seleted_button);
                        holder.mIsLike=true;
                        MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                        MomentsController.like(GlobalVariable.username,momentForItem.getObjectId());
                    }else{
                        holder.mLike.setBackgroundResource(R.drawable.fragment_unseleted_button);
                        holder.mIsLike=false;
                        MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                        MomentsController.dislike(GlobalVariable.username,momentForItem.getObjectId());
                    }
                }else{
                    Toast.makeText(parent.getContext(),"你尚未登录，无法点赞。",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentUser=AVUser.getCurrentUser();
                if(mCurrentUser!=null){
                    if(!holder.mIsCollect){
                        holder.mCollect.setBackgroundResource(R.drawable.fragment_seleted_button);
                        holder.mIsCollect=true;
                        MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                        MomentsController.collection(GlobalVariable.username,momentForItem.getObjectId());
                    }else{
                        holder.mCollect.setBackgroundResource(R.drawable.fragment_unseleted_button);
                        holder.mIsCollect=false;
                        MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                        MomentsController.disCollection(GlobalVariable.username,momentForItem.getObjectId());
                    }
                }else{
                    Toast.makeText(parent.getContext(),"你尚未登录，无法收藏。",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.mBigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(0));
            }
        });
        holder.mImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(0));
            }
        });
        holder.mImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(1));
            }
        });
        holder.mImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(2));
            }
        });
        holder.mImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(3));
            }
        });
        holder.mImg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(4));
            }
        });
        holder.mImg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(5));
            }
        });
        holder.mImg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(6));
            }
        });
        holder.mImg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(7));
            }
        });
        holder.mImg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentForItem momentForItem=mMomentForItems.get(holder.getAdapterPosition());
                OriginImageActivity.actionStart(parent.getContext(),momentForItem.getImagesList_1().get(8));
            }
        });

        return holder;
    }
}
