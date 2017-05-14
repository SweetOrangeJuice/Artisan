package com.sweetorangejuice.artisan.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;
import com.sweetorangejuice.artisan.view.Fragment.DetailFragment;

/**
 * Created by as on 2017/5/13.
 */

public class DetailActivity extends SingleFragmentActivity {

    private static final String EXTRA_OBJECT_ID="extra_object_id";

    public static void actionStart(Context context,String objectId){
        Intent intent=new Intent(context,DetailActivity.class);
        intent.putExtra(EXTRA_OBJECT_ID,objectId);
        context.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        String objectId=getIntent().getStringExtra(EXTRA_OBJECT_ID);
        return DetailFragment.newInstance(objectId);
    }
}
