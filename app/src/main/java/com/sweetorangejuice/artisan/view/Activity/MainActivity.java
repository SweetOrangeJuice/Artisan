package com.sweetorangejuice.artisan.view.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.fe.library.TabContainerView;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.view.Fragment.HomeFragment;
import com.sweetorangejuice.artisan.view.Fragment.MomentsFragment;
import com.sweetorangejuice.artisan.view.Fragment.PersonalFragment;
import com.sweetorangejuice.artisan.view.Fragment.PlazaFragment;
import com.sweetorangejuice.artisan.view.Fragment.ShoppingFragment;

public class MainActivity extends AppCompatActivity {

    //视图
    private TabContainerView mTabContainerView;

    //Fragment数组，存放的是选项卡对应的Fragment
    private Fragment[] mFragments=new Fragment[]{
            HomeFragment.newInstance(),
            PlazaFragment.newInstance(),
            MomentsFragment.newInstance(),
            ShoppingFragment.newInstance(),
            PersonalFragment.newInstance(),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
