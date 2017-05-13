package com.sweetorangejuice.artisan.model;

import com.sweetorangejuice.artisan.view.Activity.SubPlazaActivity;

/**
 * Created by as on 2017/5/12.
 */

public class Category {

    private int mNameResId;
    private int mImageResId;
    private SubPlazaActivity.CategoryCode mCategoryCode;

    public int getNameResId() {
        return mNameResId;
    }

    public void setNameResId(int nameResId) {
        mNameResId = nameResId;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public void setImageResId(int imageResId) {
        mImageResId = imageResId;
    }

    public SubPlazaActivity.CategoryCode getCategoryCode() {
        return mCategoryCode;
    }

    public void setCategoryCode(SubPlazaActivity.CategoryCode categoryCode) {
        mCategoryCode = categoryCode;
    }
}
