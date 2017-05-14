package com.sweetorangejuice.artisan.controller;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.sweetorangejuice.artisan.base.ArtisanApplication;
import com.sweetorangejuice.artisan.base.BaseActivity;
import com.sweetorangejuice.artisan.model.Comment;
import com.sweetorangejuice.artisan.model.MomentsBean;
import com.sweetorangejuice.artisan.util.LogUtil;
import com.sweetorangejuice.artisan.view.Activity.LoginActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fortuneliu
 * @version v0.1
 * MomentsController类
 *      负责圈子内容的上传功能
 * Created by fortuneliu on 2017/5/3.
 */

public class MomentsController {

    private static State momentsState = State.OK;     //用以记录朋友圈的发布状态
    private static int taskCount = 0;        //所需完成的异步任务的个数
    private static int taskFinished = 0;     //已经完成的异步任务的个数

    private enum State {       //当前朋友圈的发布状态
        OK,     //发送成功
        FAILED  //发送失败
    }

    private static AVObject currentMoments = null;        //当前正在发送的朋友圈
    private static ArrayList<AVFile> currentFiles = null; //当前正在发送的图片列表

    /**
     * checkState函数
     *      负责在最后一个异步任务完成的时候
     *      检查当前的发送状态
     *      如果发送成功，则执行成功的代码
     *      如果发送不成功，则在云端删除已经上传的内容
     */
    public static void checkState(final MomentsBean moments, final List<String> objectIds)
    {
        if(taskCount == taskFinished) {     //当所有任务都完成时
            taskCount = 0;                  //任务记录数清零
            taskFinished = 0;               //任务完成数清零
            if (momentsState != State.OK) {        //当朋友圈发布不成功时
                LogUtil.d("MomentsController","Distribute Moments Failed.");
                drawBack();                 //执行远端删除操作
                Toast.makeText(ArtisanApplication.getContext(), "当前网络不佳，请稍候重试", Toast.LENGTH_SHORT).show();
                //Todo:这里写发布不成功的代码
                momentsState = State.OK;
            }else{                          //当朋友圈发布成功时
                 LogUtil.d("MomentsController","Distribute Moments Succeed.");
                 Toast.makeText(ArtisanApplication.getContext(),"发布成功！",Toast.LENGTH_SHORT).show();
                //Todo:这里写发布成功的代码
            }
        }else if(taskCount==taskFinished+1){
            AVObject momentsObject = new AVObject("Moments");           //建立朋友圈对象
            momentsObject.put("tag", moments.getTag());                 //标签
            momentsObject.put("author", AVUser.getCurrentUser().getUsername());       //作者
            momentsObject.put("images",objectIds);
            //momentsObject.put("images", imageFiles);                    //图片列表
            momentsObject.put("text", moments.getText());               //文字内容
            //currentFiles = imageFiles;                                  //当前的图片列表
            currentMoments = momentsObject;                             //当前发送的朋友圈

            momentsObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    taskFinished += 1;                                  //已完成任务数+1
                    if (e == null) {
                        checkState(moments,objectIds);                   //发送成功，默认检查状态
                        LogUtil.d("MomentsController", "Momenets AVObject Succeed.");
                    } else {
                        MomentsController.momentsState = State.FAILED;
                        checkState(moments,objectIds);                   //发送失败，默认检查状态
                        LogUtil.d("MomentsController", "Moments AVObject Failed.");
                    }
                }
            });
        }
    }

    /**
     * 当发送出现异常时，删除远端服务器中已经上传的部分内容
     */
    private static void drawBack(){
        if(currentMoments!=null){       //删除远端服务器中的朋友圈
            currentMoments.deleteInBackground();
        }
        if(currentFiles!=null){         //删除远端服务器中的图片
            for(AVFile file:currentFiles){
                file.deleteInBackground();
            }
        }
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

        LogUtil.d("MomentsController","Checking Moments Content.");
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

    public static void distributeMoments(final MomentsBean moments) {
        taskCount = (moments.getImages()).size()+1;     //初始化异步任务数
        if (checkMoments(moments)) {                    //当朋友圈内容无误时
            int count = 0;                              //建立一个AVFile图片列表
            ArrayList<AVFile> imageFiles = new ArrayList<>();
            final List<String> objectIds=new ArrayList<>();
            for (String path : moments.getImages()) {   //将每一个图片上传至云端
                try {
                    String[] temp = path.split("/");    //获得图片的名称和类型
                    String fileName = temp[temp.length - 1];
                    String[] temp1 = fileName.split("\\.");
                    final AVFile file = AVFile.withAbsoluteLocalPath((count++) + "." + temp1[1], path);
                    imageFiles.add(file);               //将文件添加到列表中
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            MomentsController.taskFinished += 1;        //当完成操作时，已完成任务数+1
                            if (e == null) {                            //当该上传操作成功时
                                objectIds.add(file.getObjectId());
                                Log.d("TAG",""+file.getObjectId());
                                Log.d("TAG",""+objectIds.toString());
                                checkState(moments,objectIds);                           //因为不知道是不是最后一个完成，因此默认执行checkState();
                            } else {
                                MomentsController.momentsState = State.FAILED;     //设置momentsState为Failed
                                //TODO:failed
                                Toast.makeText(ArtisanApplication.getContext(), "当前网络不佳，请稍候重试", Toast.LENGTH_SHORT).show();
                                checkState(moments,objectIds);                           //因为不知道是不是最后一个完成，因此默认执行checkState();
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    MomentsController.momentsState = State.FAILED;      //图片查找失败
                    e.printStackTrace();                                //打印错误栈
                }
            }

            /*
            AVObject momentsObject = new AVObject("Moments");           //建立朋友圈对象
            momentsObject.put("tag", moments.getTag());                 //标签
            momentsObject.put("author", GlobalVariable.username);       //作者
            momentsObject.put("images",objectIds);
            //momentsObject.put("images", imageFiles);                    //图片列表
            momentsObject.put("text", moments.getText());               //文字内容
            currentFiles = imageFiles;                                  //当前的图片列表
            currentMoments = momentsObject;                             //当前发送的朋友圈

            momentsObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    taskFinished += 1;                                  //已完成任务数+1
                    if (e == null) {
                        checkState();                   //发送成功，默认检查状态
                        LogUtil.d("MomentsController", "Momenets AVObject Succeed.");
                    } else {
                        MomentsController.momentsState = State.FAILED;
                        checkState();                   //发送失败，默认检查状态
                        LogUtil.d("MomentsController", "Moments AVObject Failed.");
                    }
                }
            });
            */
        }
    }
    public static MomentsBean getMomentByObjectId(String objectId)
    {
        MomentsBean moment = new MomentsBean();
        AVQuery<AVObject> query = new AVQuery<>("Moments");
        try{
            AVObject object = query.get(objectId);
            moment.setAuthor((String)object.get("author"));
            moment.setCreateTime((Date)object.get("createdAt"));
            moment.setText((String)object.get("text"));
            moment.setTag((String)object.get("text"));
            //List<String> imagesList = (ArrayList<String>) object.get("images");
            List<String> imagesList=new ArrayList<>();
            List<String> objects=(ArrayList<String>)object.get("images");
            for(int i=0;i<objects.size();i++){
                imagesList.add((String)objects.get(i));
                //imagesList.add((String)objects.get(i));
            }

            moment.setImages(imagesList);
        }catch(AVException e){

        }
        return moment;
    }
    public static void login(){
        BaseActivity.finishAll();
        LoginActivity.startAction(ArtisanApplication.getContext());
    }

    public static boolean isLike(String username,String momentsId){
        AVQuery<AVObject> query=new AVQuery<>("Like");
        query.whereEqualTo("username",username);
        query.whereEqualTo("momentsId",momentsId);
        List<AVObject> result;
        try {
            result=query.find();
            if(result.size()>0){
                return true;
            }else {
                return false;
            }
        }catch (AVException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isCollect(String username,String momentsId){
        AVQuery<AVObject> query=new AVQuery<>("Collection");
        query.whereEqualTo("username",username);
        query.whereEqualTo("momentsId",momentsId);
        List<AVObject> result;
        try {
            result=query.find();
            if(result.size()>0){
                return true;
            }else {
                return false;
            }
        }catch (AVException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void like(String username,String momentsId){
        AVObject likeObject = new AVObject("Like");
        likeObject.put("username",username);
        likeObject.put("momentsId",momentsId);
        likeObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                //在此处写点赞成功的代码
            }
        });
    }

    public static void dislike(String username,String momentsId){
        //在这里写取消点赞的代码
        AsyncTask<String,Integer,Integer> asyncTask=new AsyncTask<String, Integer, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                AVQuery<AVObject> query=new AVQuery<>("Like");
                query.whereEqualTo("username",params[0]);
                query.whereEqualTo("momentsId",params[1]);
                List<AVObject> result;
                try {
                    result=query.find();
                    AVObject target=result.get(0);
                    target.deleteInBackground();
                }catch (AVException e){
                    e.printStackTrace();
                }
                return 100;
            }
        }.execute(username,momentsId);
    }

    public static void collection(String username,String momentsId){
        AVObject likeObject = new AVObject("Collection");
        likeObject.put("username",username);
        likeObject.put("momentsId",momentsId);
        likeObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                //在此处写收藏成功的代码
            }
        });
    }

    public static void disCollection(String username,String momentsId){
        //在这里写取消收藏的代码
        AsyncTask<String,Integer,Integer> asyncTask=new AsyncTask<String, Integer, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                AVQuery<AVObject> query=new AVQuery<>("Collection");
                query.whereEqualTo("username",params[0]);
                query.whereEqualTo("momentsId",params[1]);
                List<AVObject> result;
                try {
                    result=query.find();
                    AVObject target=result.get(0);
                    target.deleteInBackground();
                }catch (AVException e){
                    e.printStackTrace();
                }
                return 100;
            }
        }.execute(username,momentsId);
    }

    public static void Comments(String username,String momentsId,String content){
        AVObject CommentsObject = new AVObject("Comments");
        CommentsObject.put("username",username);
        CommentsObject.put("momentsId",momentsId);
        CommentsObject.put("Content",content);
        CommentsObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                //在此处发送评论成功的代码
            }
        });
    }

    public static List<Comment> getComments(String momentsId){
        AVQuery<AVObject> query=new AVQuery<>("Comments");
        query.whereEqualTo("momentsId",momentsId);
        List<AVObject> result;
        List<Comment> comments=new ArrayList<>();
        try {
            result=query.find();
            for(int i=0;i<result.size();i++){
                AVObject avObject=result.get(i);
                Comment comment=new Comment();
                comment.setAccount((String)avObject.get("username"));
                comment.setText((String)avObject.get("Content"));
                comments.add(comment);
            }
        }catch (AVException e){
            e.printStackTrace();
        }
        return comments;
    }
}