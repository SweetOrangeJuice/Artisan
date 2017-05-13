package com.sweetorangejuice.artisan.view.Activity;

import android.support.v4.app.Fragment;

import com.sweetorangejuice.artisan.base.SingleFragmentActivity;
import com.sweetorangejuice.artisan.view.Fragment.DetailFragment;

/**
 * Created by as on 2017/5/13.
 */

public class DetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return DetailFragment.newInstance();
    }
}
