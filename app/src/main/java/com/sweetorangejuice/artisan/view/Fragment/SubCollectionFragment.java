package com.sweetorangejuice.artisan.view.Fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.FileController;
import com.sweetorangejuice.artisan.controller.MomentsController;
import com.sweetorangejuice.artisan.controller.PersonalController;
import com.sweetorangejuice.artisan.controller.adapter.MomentAdapter;
import com.sweetorangejuice.artisan.model.MomentForItem;
import com.sweetorangejuice.artisan.model.MomentsBean;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/5/14.
 */

public class SubCollectionFragment extends Fragment {

    private ImageView mBackImageView;
    private RecyclerView mRecyclerView;
    private RelativeLayout mLoadingRelativeLayout;

    private MomentAdapter mAdapter;
    private List<MomentForItem> mMomentForItems=new ArrayList<>();

    public static Fragment newInstance(){
        return new SubCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sub_collection,container,false);

        mBackImageView=(ImageView)view.findViewById(R.id.fragment_sub_collection_back);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.fragment_sub_collection_recycler_view);
        mLoadingRelativeLayout=(RelativeLayout)view.findViewById(R.id.fragment_sub_collection_loading);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new MomentAdapter(mMomentForItems);
        mRecyclerView.setAdapter(mAdapter);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        loadData();

        return view;
    }

    private void loadData(){
        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                mLoadingRelativeLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(String... params) {
                MomentsBean momentsBean;
                List<String> objectIds= PersonalController.getMyCollection(100,0);
                List<MomentForItem> result=new ArrayList<>();
                for (int i=0;i<objectIds.size();i++){
                    momentsBean= MomentsController.getMomentByObjectId(objectIds.get(i));
                    MomentForItem momentForItem=new MomentForItem();
                    momentForItem.setAccount(momentsBean.getAuthor());
                    momentForItem.setContent(momentsBean.getText());
                    momentForItem.setObjectId(objectIds.get(i));
                    List<String> images=momentsBean.getImages();
                    for(int j=0;j<images.size();j++){
                        String img_obj_id=images.get(j);
                        byte[] image= FileController.getThumbnailbyObjectId(img_obj_id,150,150);
                        byte[] image_1=FileController.getPicturebyObjectId(img_obj_id);
                        momentForItem.getImagesList().add(image);
                        momentForItem.getImagesList_1().add(image_1);   //原图
                    }

                    //以下为暂时替代的头像
                    Resources resources=getResources();
                    Bitmap bitmap= BitmapFactory.decodeResource(resources,R.drawable.head_portrait);
                    ByteArrayOutputStream out=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
                    momentForItem.setHeadPortrait(out.toByteArray());

                    result.add(momentForItem);
                }
                mMomentForItems.clear();
                for(int i=0;i<result.size();i++){
                    mMomentForItems.add(result.get(i));
                }
                return 100;
            }
        }.execute();
    }
}