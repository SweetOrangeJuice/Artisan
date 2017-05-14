package com.sweetorangejuice.artisan.view.Fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.FileController;
import com.sweetorangejuice.artisan.controller.MomentsController;
import com.sweetorangejuice.artisan.controller.PlazaController;
import com.sweetorangejuice.artisan.controller.adapter.MomentAdapter;
import com.sweetorangejuice.artisan.model.MomentForItem;
import com.sweetorangejuice.artisan.model.MomentsBean;
import com.sweetorangejuice.artisan.view.Activity.SubPlazaActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/5/12.
 */

public class SubPlazaFragment extends Fragment {

    private static final String ARG_SUB_CATEGORY_CODE="sub_category_code";

    private SubPlazaActivity.CategoryCode mCategoryCode;

    private MomentAdapter mAdapter;

    private TextView mTitleTextView;
    private ImageView mBackImageView;
    private ImageView mSearchImageView;
    private Button mTimeSortButton;
    private Button mHotSortButton;
    private Button mCollectButton;
    private RecyclerView mRecyclerView;
    private RelativeLayout mLoading;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<MomentForItem> mMomentForItems=new ArrayList<>();

    public static Fragment newInstance(SubPlazaActivity.CategoryCode categoryCode){
        Bundle args=new Bundle();
        args.putSerializable(ARG_SUB_CATEGORY_CODE,categoryCode);

        SubPlazaFragment fragment=new SubPlazaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryCode=(SubPlazaActivity.CategoryCode) getArguments().get(ARG_SUB_CATEGORY_CODE);

        //测试用，写了网络接入momentforitem后删除
        /*
        MomentForItem momentForItem=new MomentForItem();
        momentForItem.setAccount("sb");
        momentForItem.setContent("今天，我要写下第一条动态，测试一下这个子项。话说回来这个东西好好玩啊。");
        Resources resource=getResources();
        Bitmap bmp0= BitmapFactory.decodeResource(resource,R.drawable.head_portrait);
        ByteArrayOutputStream out0=new ByteArrayOutputStream();
        bmp0.compress(Bitmap.CompressFormat.PNG,100,out0);
        momentForItem.setHeadPortrait(out0.toByteArray());
        //测试一张图片
        Bitmap bmp1= BitmapFactory.decodeResource(resource,R.drawable.ic_drawing);
        ByteArrayOutputStream out1=new ByteArrayOutputStream();
        bmp1.compress(Bitmap.CompressFormat.PNG,100,out1);
        momentForItem.getImagesList().add(out1.toByteArray());
        //测试多张图片
        momentForItem.getImagesList().add(out0.toByteArray());
        momentForItem.getImagesList().add(out0.toByteArray());
        momentForItem.getImagesList().add(out1.toByteArray());
        momentForItem.getImagesList().add(out1.toByteArray());
        momentForItem.getImagesList().add(out1.toByteArray());
        momentForItem.getImagesList().add(out1.toByteArray());
        momentForItem.getImagesList().add(out1.toByteArray());
        momentForItem.getImagesList().add(out1.toByteArray());
        mMomentForItems.add(momentForItem);
        */

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sub_plaza,container,false);

        mTitleTextView=(TextView)view.findViewById(R.id.fragment_sub_plaza_title);
        mBackImageView=(ImageView)view.findViewById(R.id.fragment_sub_plaza_back);
        mSearchImageView=(ImageView)view.findViewById(R.id.fragment_sub_plaza_search);
        mTimeSortButton=(Button)view.findViewById(R.id.time_sort);
        mHotSortButton=(Button)view.findViewById(R.id.hot_sort);
        mCollectButton=(Button)view.findViewById(R.id.collect_sort);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.fragment_sub_plaza_recycler_view);
        mLoading=(RelativeLayout) view.findViewById(R.id.fragment_sub_plaza_loading);
        mSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.fragment_swipe_refresh_layout);

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

        switch (mCategoryCode){
            case BUILDING:mTitleTextView.setText(R.string.fragment_image_buildings);
                queryByCategory("buildings");
                break;
            case DRAWING:mTitleTextView.setText(R.string.fragment_image_drawings);
                queryByCategory("drawings");
                break;
            case HANDWORK:mTitleTextView.setText(R.string.fragment_image_handwork);
                queryByCategory("handwork");
                break;
            case SCULPTURE:mTitleTextView.setText(R.string.fragment_image_sculptures);
                queryByCategory("sculptures");
                break;
            case GRAPHIC:mTitleTextView.setText(R.string.fragment_image_graphics);
                queryByCategory("graphics");
                break;
            default:
                break;
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (mCategoryCode){
                    case BUILDING:mTitleTextView.setText(R.string.fragment_image_buildings);
                        queryByCategory("buildings");
                        break;
                    case DRAWING:mTitleTextView.setText(R.string.fragment_image_drawings);
                        queryByCategory("drawings");
                        break;
                    case HANDWORK:mTitleTextView.setText(R.string.fragment_image_handwork);
                        queryByCategory("handwork");
                        break;
                    case SCULPTURE:mTitleTextView.setText(R.string.fragment_image_sculptures);
                        queryByCategory("sculptures");
                        break;
                    case GRAPHIC:mTitleTextView.setText(R.string.fragment_image_graphics);
                        queryByCategory("graphics");
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }

    private void queryByCategory(String category){
        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mLoading.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                mAdapter.notifyDataSetChanged();
                mLoading.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            protected Integer doInBackground(String... params) {

                MomentsBean momentsBean;
                List<String> objectIds= PlazaController.getPlazaMoments(params[0],"createdAt",100,0);
                List<MomentForItem> momentForItems=new ArrayList<>();
                for(int i=0;i<objectIds.size();i++){
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

                    momentForItems.add(momentForItem);
                }
                mMomentForItems.clear();

                for(int i=0;i<momentForItems.size();i++){
                    mMomentForItems.add(momentForItems.get(i));
                }

                return 100;
            }
        }.execute(category);

    }

}
