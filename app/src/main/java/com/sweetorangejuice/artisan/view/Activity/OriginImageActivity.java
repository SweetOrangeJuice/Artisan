package com.sweetorangejuice.artisan.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;
import com.sweetorangejuice.artisan.view.Fragment.OriginImageFragment;

/**
 * Created by as on 2017/5/13.
 */

public class OriginImageActivity extends SingleFragmentActivity {

    private static final String EXTRA_IMAGE="extra_image";

    public static void actionStart(Context context,String objectId){
        Intent intent=new Intent(context,OriginImageActivity.class);
        intent.putExtra(EXTRA_IMAGE,objectId);
        context.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        return OriginImageFragment.newInstance(getIntent().getStringExtra(EXTRA_IMAGE));
    }
}
