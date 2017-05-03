package com.sweetorangejuice.artisan.controller;

import com.sweetorangejuice.artisan.model.MomentsBean;

/**
 * @author fortuneliu
 * @version v0.1
 * MomentsController类
 *      负责圈子内容的上传功能
 * Created by fortuneliu on 2017/5/3.
 */

public class MomentsController {

    public boolean checkMoments(MomentsBean moments) {
        if (moments.getText() == "" || moments.getText() == null) return false;
        if (moments.getAuthor() == "" || moments.getAuthor() == null) return false;
        if (moments.getTag() == "" || moments.getTag() == null) return false;
        if (moments.getImages().isEmpty()) return false;
        return true;
    }
}
