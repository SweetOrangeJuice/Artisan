package com.sweetorangejuice.artisan.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweetorangejuice.artisan.R;

/**
 * Created by as on 2017/5/3.
 */

public class PlazaFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plaza,container,false);

        return view;
    }

    /**
     * newInstance:返回PlazaFragment的一个实例
     * @return  PlazaFragment的一个实例
     */
    public static Fragment newInstance(){
        return new PlazaFragment();
    }
}
