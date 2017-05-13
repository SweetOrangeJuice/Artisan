package com.sweetorangejuice.artisan.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;
import com.sweetorangejuice.artisan.view.Fragment.ProfilesFragment;

/**
 * Created by as on 2017/5/11.
 */

public class PersonalActivity extends SingleFragmentActivity {

    public static void startAction(Context context){
        Intent intent=new Intent(context,ProfilesFragment.class);
        context.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        return ProfilesFragment.newInstance();
    }
}
