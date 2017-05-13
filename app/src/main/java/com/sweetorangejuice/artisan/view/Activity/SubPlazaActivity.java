package com.sweetorangejuice.artisan.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;
import com.sweetorangejuice.artisan.view.Fragment.SubPlazaFragment;

/**
 * Created by as on 2017/5/12.
 */

public class SubPlazaActivity extends SingleFragmentActivity {

    private static CategoryCode mCategoryCode;

    public enum CategoryCode{
        BUILDING,
        DRAWING,
        HANDWORK,
        SCULPTURE,
        GRAPHIC,
    }

    public static void actionStart(Context context,CategoryCode categoryCode){
        Intent intent = new Intent(context,SubPlazaActivity.class);
        mCategoryCode=categoryCode;
        context.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        return SubPlazaFragment.newInstance(mCategoryCode);
    }
}
