package com.sweetorangejuice.artisan.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/5/13.
 */

public class MomentForItem {

    private byte[] mHeadPortrait;
    private String mAccount;
    private String mContent;
    private List<byte[]> mImagesList;

    public MomentForItem(){
        mImagesList=new ArrayList<>();
    }

    public byte[] getHeadPortrait() {
        return mHeadPortrait;
    }

    public void setHeadPortrait(byte[] headPortrait) {
        mHeadPortrait = headPortrait;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<byte[]> getImagesList() {
        return mImagesList;
    }

    public void setImagesList(List<byte[]> imagesList) {
        mImagesList = imagesList;
    }
}
