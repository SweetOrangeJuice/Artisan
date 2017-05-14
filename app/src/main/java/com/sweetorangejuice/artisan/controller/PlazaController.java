package com.sweetorangejuice.artisan.controller;

import android.os.AsyncTask;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fortuneliu on 2017/5/12.
 */

public class PlazaController{
    public class momentParamBean
    {
        String category;
        String orderBy;
        int limit;
        int skip;
    }
    public class getPlazaMomentsAsynk extends AsyncTask<momentParamBean, Integer, List<String> > {

        /**
         * doInBackground函数
         *  在后台执行的操作
         * @param params
         * @return
         */
        @Override
        protected List<String> doInBackground(momentParamBean... params) {
            AVQuery<AVObject> query = new AVQuery<>("Moments");
            query.whereEqualTo("tag",params[0].category);
            query.limit(params[0].limit);
            query.skip(params[0].skip);
            query.addDescendingOrder(params[0].orderBy);
            List<AVObject> temp;
            List<String> result = new ArrayList<>();
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
}
