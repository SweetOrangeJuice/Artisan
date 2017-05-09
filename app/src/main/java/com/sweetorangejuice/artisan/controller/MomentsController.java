package com.sweetorangejuice.artisan.controller;

import android.util.Log;
import android.widget.Toast;

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

    private static String timePattern = "yyyy-MM-dd HH:mm:ss";      //时间戳的格式
    private static boolean distributeState = false;                 //暂存发布状态，平时无用

    private final static Object mLock=new Object();

    public static int momentsState = 0;

    public static boolean checkState()
    {
        boolean result = true;
        switch(MomentsController.momentsState){
            case 0:
                break;
            case 1:
                //TODO:图片上传失败
                result = false;
                break;
            case 2:
                //TODO:朋友圈上传失败
                result = false;
                break;
        }
        //在这里写上传失败的toast.
        return result;
    }

    /**
     * checkMoments函数
     *      负责检查圈子内容齐全与否
     *      如果不齐全，则返回false
     * @param moments
     * @return
     */
    private static boolean checkMoments(MomentsBean moments) {
        if (moments.getText() .equals("")  || moments.getText() == null) return false;
        if (moments.getTag() .equals("")  || moments.getTag() == null) return false;
        if (moments.getImages().isEmpty()) return false;

        Log.d("TAG","haha");
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

    public static void distributeMoments(MomentsBean moments) {

        if (checkMoments(moments)) {
            int count = 0;
            ArrayList<AVFile> imageFiles = new ArrayList<>();
                for (String path : moments.getImages()) {
                    try {
                        String[] temp = path.split("/");
                        String fileName = temp[temp.length - 1];
                        String[] temp1 = fileName.split("\\.");
                        AVFile file = AVFile.withAbsoluteLocalPath((count++) + "." + temp1[1], path);
                        if(checkState()) {
                            file.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e != null) {
                                        MomentsController.momentsState = 1;
                                        checkState();
                                        //TODO:failed
                                        Toast.makeText(ArtisanApplication.getContext(), "当前网络不佳，请稍候重试", Toast.LENGTH_SHORT).show();
                                    } else {

                                    }
                                }
                            });
                        }else
                        {
                            break;
                        }
                        imageFiles.add(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                AVObject momentsObject = new AVObject("Moments");
                momentsObject.put("tag", moments.getTag());
                momentsObject.put("author", AVUser.getCurrentUser());
                momentsObject.put("images", imageFiles);
                momentsObject.put("text", moments.getText());

                SimpleDateFormat format = new SimpleDateFormat(timePattern);
                String sendTime = format.format(new Date());

                momentsObject.put("time", sendTime);

                momentsObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            checkState();
                            Log.d("TAG", "succeed");
                        } else {
                            MomentsController.momentsState = 2;
                            checkState();
                            Log.d("TAG", "failed");
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