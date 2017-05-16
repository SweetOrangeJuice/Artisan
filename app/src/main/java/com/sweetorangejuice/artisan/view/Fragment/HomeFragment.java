package com.sweetorangejuice.artisan.view.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/5/3.
 */

public class HomeFragment extends Fragment{

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    private ConvenientBanner mConvenientBanner;
    private List<Integer> mImageResIds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        mConvenientBanner=(ConvenientBanner)view.findViewById(R.id.convenientBanner);

        initialImageResIds();

        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        },mImageResIds)
                .setPageIndicator(new int[]{R.drawable.ic_not_current,R.drawable.ic_current});

        return view;
    }

    private void initialImageResIds(){
        mImageResIds=new ArrayList<>();
        //字符串为资源名字
        mImageResIds.add(ResourceUtil.getResId("ear_phone",R.drawable.class));
        mImageResIds.add(ResourceUtil.getResId("pot",R.drawable.class));
        mImageResIds.add(ResourceUtil.getResId("handwork_star",R.drawable.class));
    }

    /**
     * newInstance:返回HomeFragment的一个实例
     * @return  HomeFragment的一个实例
     */
    public static Fragment newInstance(){
        return new HomeFragment();
    }
}