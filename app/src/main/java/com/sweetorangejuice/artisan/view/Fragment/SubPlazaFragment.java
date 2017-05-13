package com.sweetorangejuice.artisan.view.Fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    private TextView mTitleTextView;
    private ImageView mBackImageView;
    private ImageView mSearchImageView;
    private Button mTimeSortButton;
    private Button mHotSortButton;
    private Button mCollectButton;
    private RecyclerView mRecyclerView;

    private List<MomentForItem> mMomentForItems;

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

        switch (mCategoryCode){
            case BUILDING:mTitleTextView.setText(R.string.fragment_image_buildings);
                mMomentForItems=queryByCategory("buildings");
                break;
            case DRAWING:mTitleTextView.setText(R.string.fragment_image_drawings);
                mMomentForItems=queryByCategory("drawings");
                break;
            case HANDWORK:mTitleTextView.setText(R.string.fragment_image_handwork);
                mMomentForItems=queryByCategory("handwork");
                break;
            case SCULPTURE:mTitleTextView.setText(R.string.fragment_image_sculptures);
                mMomentForItems=queryByCategory("sculptures");
                break;
            case GRAPHIC:mTitleTextView.setText(R.string.fragment_image_graphics);
                mMomentForItems=queryByCategory("graphics");
                break;
            default:
                break;
        }

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        final MomentAdapter adapter=new MomentAdapter(mMomentForItems);
        mRecyclerView.setAdapter(adapter);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    private List<MomentForItem> queryByCategory(String category){
        MomentsBean momentsBean;
        List<String> objectIds=PlazaController.getPlazaMoments(category,"createdAt",100,0);
        List<MomentForItem> momentForItems=new ArrayList<>();
        for(int i=0;i<objectIds.size();i++){
            momentsBean= MomentsController.getMomentByObjectId(objectIds.get(i));
            MomentForItem momentForItem=new MomentForItem();
            momentForItem.setAccount(momentsBean.getAuthor());
            momentForItem.setContent(momentsBean.getText());
            List<String> images=momentsBean.getImages();
            for(int j=0;j<images.size();j++){
                byte[] image=FileController.getPicturebyObjectId(images.get(j));
                momentForItem.getImagesList().add(image);
            }

            //以下为暂时替代的头像
            Resources resources=getResources();
            Bitmap bitmap= BitmapFactory.decodeResource(resources,R.drawable.head_portrait);
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            momentForItem.setHeadPortrait(out.toByteArray());
        }

        return momentForItems;
    }

}
