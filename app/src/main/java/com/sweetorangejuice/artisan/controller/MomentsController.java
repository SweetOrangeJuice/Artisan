package com.sweetorangejuice.artisan.controller;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.sweetorangejuice.artisan.base.ArtisanApplication;
import com.sweetorangejuice.artisan.base.BaseActivity;
import com.sweetorangejuice.artisan.model.MomentsBean;
import com.sweetorangejuice.artisan.util.LogUtil;
import com.sweetorangejuice.artisan.view.Activity.LoginActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author fortuneliu
 * @version v0.1
 * MomentsController类
 *      负责圈子内容的上传功能
 * Created by fortuneliu on 2017/5/3.
 */

public class MomentsController {
    /**
     * checkMoments函数
     *      负责检查圈子内容齐全与否
     *      如果不齐全，则返回false
     * @param moments
     * @return
     */
    private static boolean checkMoments(MomentsBean moments) {
        if (moments.getText() == "" || moments.getText() == null) return false;
        if (moments.getTag() == "" || moments.getTag() == null) return false;
        if (moments.getImages().isEmpty()) return false;
        return true;
    }

    /**
     * distributeMoments函数
     *      负责发布圈子内容
     *      发布成功则返回True
     *      发布失败则返回False
     * @param moments
     * @return
     */
    public static void distributeMoments(MomentsBean moments){
        if(checkMoments(moments)) {
            int count = 0;
            ArrayList<AVFile> imageFiles = new ArrayList<>();
            for(String path:moments.getImages()){
                try {
                    AVFile file = AVFile.withAbsoluteLocalPath(count + ".jpg", path);
                    imageFiles.add(file);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }finally {
                    /**
                     * 在这里写发布失败的回调
                     */
                    LogUtil.d("MomentsController","Distribute Failed.");
                }
            }
            AVObject momentsObject = new AVObject("Moments");
            momentsObject.put("tag",moments.getTag());
            momentsObject.put("author", AVUser.getCurrentUser().getObjectId());
            momentsObject.put("images",imageFiles);
            momentsObject.put("text",moments.getText());
            momentsObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        /**
                         * 在这里写发布成功的回调
                         */
                        LogUtil.d("MomentsController","Distribute Success.");
                    } else {
                        /**
                         * 在这里写发布失败的回调
                         */
                        LogUtil.d("MomentsController","Distribute Failed.");
                    }
                }
            });
        }
    }

    public static void login(){
        BaseActivity.finishAll();
        LoginActivity.startAction(ArtisanApplication.getContext());
    }
}