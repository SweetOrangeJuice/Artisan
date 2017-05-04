package com.sweetorangejuice.artisan.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.fe.library.TabContainerView;
import com.fe.library.adapter.BaseAdapter;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.view.Fragment.HomeFragment;
import com.sweetorangejuice.artisan.view.Fragment.MomentsFragment;
import com.sweetorangejuice.artisan.view.Fragment.PersonalFragment;
import com.sweetorangejuice.artisan.view.Fragment.PlazaFragment;
import com.sweetorangejuice.artisan.view.Fragment.ShoppingFragment;

public class MainActivity extends AppCompatActivity {

    public class MainActivityAdapter extends BaseAdapter{

        //使用到的FragmentManager和Fragment数组
        private FragmentManager mFragmentManager;
        private Fragment[] mFragments;

        //用户传入环境中的FragmentManager和自定义的Fragment数组
        public MainActivityAdapter(FragmentManager fragmentManager,Fragment[] fragments) {
            mFragmentManager=fragmentManager;
            mFragments=fragments;
        }

        /**
         * getCount:返回选项卡个数
         * @return  选项卡个数
         */
        @Override
        public int getCount() {
            return 5;
        }

        /**
         * getFragmentArray:获取自定义的Fragment数组
         * @return  获取自定义的Fragment数组
         */
        @Override
        public Fragment[] getFragmentArray() {
            return mFragments;
        }

        /**
         * getFragmentManager:获取环境中的FragmentManager
         * @return  环境中的FragmentManager
         */
        @Override
        public FragmentManager getFragmentManager() {
            return mFragmentManager;
        }

        /**
         * getIconImageArray:获取选项卡的图片id数组
         * @return  选项卡的图片id数组
         */
        @Override
        public int[] getIconImageArray() {
            return new int[]{
                    //在这里确定选项卡每一项用到的图片
                    R.drawable.ic_home,
                    R.drawable.ic_plaza,
                    R.drawable.ic_moments,
                    R.drawable.ic_shopping,
                    R.drawable.ic_personal,
            };
        }

        /**
         * getIconImageArray:获取某一项选中后，选项卡的图片id数组
         * @return  获取某一项选中后，选项卡的图片id数组
         */
        @Override
        public int[] getSelectedIconImageArray() {
            //在这里确定选中某一项，选项卡每一项用到的图片,此处暂时缺图片，故和未选状态一样
            return new int[]{
                    R.drawable.ic_home,
                    R.drawable.ic_plaza,
                    R.drawable.ic_moments,
                    R.drawable.ic_shopping,
                    R.drawable.ic_personal,
            };
        }

        /**
         * getTextArray:获取选项卡中的字符串数组
         * @return  选项卡中的字符串数组
         */
        @Override
        public String[] getTextArray() {
            //在这里确定选项卡中每一项的名字
            return new String[]{
                    getString(R.string.main_activity_home),
                    getString(R.string.main_activity_plaza),
                    getString(R.string.main_activity_moments),
                    getString(R.string.main_activity_shopping),
                    getString(R.string.main_activity_personal)
            };
        }
    }

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

        //绑定视图
        mTabContainerView=(TabContainerView)findViewById(R.id.tab_container_view);

        //构建滑动Fragment和选项卡
        mTabContainerView.setAdapter(new MainActivityAdapter
                (getSupportFragmentManager(),mFragments));
    }

    /**
     * actionStart:单纯启动一个活动
     * @param context   启动源
     */
    public static void actionStart(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
}
