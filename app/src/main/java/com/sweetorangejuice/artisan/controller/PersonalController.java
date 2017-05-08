package com.sweetorangejuice.artisan.controller;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.sweetorangejuice.artisan.base.ArtisanApplication;
import com.sweetorangejuice.artisan.base.BaseActivity;
import com.sweetorangejuice.artisan.model.MomentsBean;
import com.sweetorangejuice.artisan.model.PersonalBean;
import com.sweetorangejuice.artisan.util.LogUtil;
import com.sweetorangejuice.artisan.view.Activity.LoginActivity;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/5/5.
 */

public class PersonalController {
    /**
     * 退出登录
     * @param currentUser
     */
    public static void logOut(AVUser currentUser) {
        if (currentUser != null) {
            LoginController.LogOut();
        }
        BaseActivity.finishAll();
        LoginActivity.startAction(ArtisanApplication.getContext());
    }

    /**
     * 用户在注册的时候生成相应个人资料表
     * @param objectId
     */
    public static void createPersonalInfo(String objectId){
        AVObject personalObject = new AVObject("person");
        personalObject.put("User", objectId);
        try {
            personalObject.save();
        }catch(AVException e){
            if(e==null){
            /**
             * 这里写成功的回调
             */
            }else{
                /**
                 * 这里写失败的回调
                 */
            }
        }
    }

    /**
     * 用户获取个人资料
     * @return
     */
    public static AVObject showPersonalInfo() {
        AVQuery<AVObject> avQuery = new AVQuery<>("person");
        avQuery.whereEqualTo("user", AVUser.getCurrentUser().getObjectId());
        AVObject personalObject = null;
        try {
            personalObject = avQuery.getFirst();
        } catch (AVException e) {
            if (e == null) {
                return personalObject;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 传入已经修改好的personalBean
     * 用于更新个人资料
     * @param personalBean
     */
    public static void modifyPersonalInfo(PersonalBean personalBean) {
        AVObject personalObject = showPersonalInfo();
        if (personalObject != null) {
            try {
                personalObject.put("gender", personalBean.getGender());
                personalObject.put("age", personalBean.getAge());
                personalObject.put("school", personalBean.getSchool());
                personalObject.put("tag", personalBean.getTag());
                personalObject.put("headImage", AVFile.withAbsoluteLocalPath("headImg.jpg", personalBean.getHeadImage()));
                personalObject.save();
            } catch (AVException ex) {
                if (ex == null) {
                    /**
                     * 在这里写发布成功的回调
                     */
                    LogUtil.d("MomentsController", "Distribute Success.");
                } else {
                    /**
                     * 在这里写发布失败的回调
                     */
                    LogUtil.d("MomentsController", "Distribute Failed.");
                }
            } catch (FileNotFoundException ex) {
                /**
                 * 在这里写发布失败的回调
                 */
                LogUtil.d("MomentsController", "Distribute Failed.");
            }
        } else {
            /**
             * 在这里写发布失败的回调
             */
            LogUtil.d("MomentsController", "Distribute Failed.");
        }
    }
    public static ArrayList<MomentsBean> getMyMoments(int limit,int skip)
    {
        AVQuery<AVObject> query = new AVQuery<>("Todo");
        query.whereEqualTo("author",AVUser.getCurrentUser().getObjectId());
        query.limit(limit);
        query.skip(skip);
        query.addDescendingOrder("createdAt");
        List<AVObject> result = null;
        try {
            result =  query.find();
        }catch(AVException e){

        }
        return (ArrayList)result;
    }
    public static void putCollection(String objectId){
        AVObject collectionObject = new AVObject("collection");
        collectionObject.put("user",AVUser.getCurrentUser().getObjectId());
        collectionObject.put("moments",objectId);
        try {
            collectionObject.save();
        }catch(AVException e){
            if(e==null){

            }else{

            }
        }
    }
}
