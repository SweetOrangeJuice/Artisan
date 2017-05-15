package com.sweetorangejuice.artisan.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;
import com.sweetorangejuice.artisan.view.Fragment.OthersProfilesFragment;
import com.sweetorangejuice.artisan.view.Fragment.SubCollectionFragment;
import com.sweetorangejuice.artisan.view.Fragment.SubFollowerFragment;
import com.sweetorangejuice.artisan.view.Fragment.SubIdolFragment;
import com.sweetorangejuice.artisan.view.Fragment.SubMessageFragment;
import com.sweetorangejuice.artisan.view.Fragment.SubMomentFragment;
import com.sweetorangejuice.artisan.view.Fragment.SubProfilesFragment;

/**
 * Created by as on 2017/5/14.
 */

public class SubPersonalActivity extends SingleFragmentActivity{

    public static final int ACTION_MOMENTS=1;
    public static final int ACTION_COLLECTIONS=2;
    public static final int ACTION_IDOLS=3;
    public static final int ACTION_FOLLOWERS=4;
    public static final int ACTION_MESSAGES=5;
    public static final int ACTION_PROFILES=6;
    public static final int ACTION_OTHER_PROFILES=7;

    private static final String EXTRA_ACTION_CODE="action_code";
    private static final String EXTRA_USERNAME="username";

    public static void actionStart(Context context,int actionCode){
        Intent intent=new Intent(context,SubPersonalActivity.class);
        intent.putExtra(EXTRA_ACTION_CODE,actionCode);
        context.startActivity(intent);
    }

    public static void actionStart(Context context,int actionCode,String username){
        Intent intent=new Intent(context,SubPersonalActivity.class);
        intent.putExtra(EXTRA_ACTION_CODE,actionCode);
        intent.putExtra(EXTRA_USERNAME,username);
        context.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        int actionCode=getIntent().getIntExtra(EXTRA_ACTION_CODE,0);
        switch (actionCode){
            case 1:
                return SubMomentFragment.newInstance();
            case 2:
                return SubCollectionFragment.newInstance();
            case 3:
                return SubIdolFragment.newInstance();
            case 4:
                return SubFollowerFragment.newInstance();
            case 5:
                return SubMessageFragment.newInstance();
            case 6:
                return SubProfilesFragment.newInstance();
            case 7:
                String username=getIntent().getStringExtra(EXTRA_USERNAME);
                return OthersProfilesFragment.newInstance(username);
            default:
                return null;
        }

    }
}
