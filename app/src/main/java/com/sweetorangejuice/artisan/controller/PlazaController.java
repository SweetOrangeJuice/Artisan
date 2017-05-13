package com.sweetorangejuice.artisan.controller;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.sweetorangejuice.artisan.base.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fortuneliu on 2017/5/12.
 */

public class PlazaController{
    /**
     * 获得广场上的朋友圈
     * @param category 要查找的朋友圈的目录类别
     * @param orderBy  朋友圈的排序方式
     * @param limit    一次查找的数量
     * @param skip     已经显示的数量
     * @return
     */
    public static ArrayList<String> getPlazaMoments(String category,String orderBy,int limit, int skip)
    {
        AVQuery<AVObject> query = new AVQuery<>("Moments");
        query.whereEqualTo("tag",category);
        query.limit(limit);
        query.skip(skip);
        query.addDescendingOrder(orderBy);
        List<AVObject> temp;
        ArrayList<String> result = new ArrayList<>();
        try {
            temp =  query.find();
            for(AVObject moment:temp){
                result.add(moment.getObjectId());
            }
        }catch(AVException e){
            e.printStackTrace();
        }
        return result;
    }
}
