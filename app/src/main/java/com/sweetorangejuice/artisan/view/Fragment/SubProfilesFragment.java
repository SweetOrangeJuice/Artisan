package com.sweetorangejuice.artisan.view.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.FileController;
import com.sweetorangejuice.artisan.model.PersonalBean;
import com.sweetorangejuice.artisan.util.FileUtils;
import com.sweetorangejuice.artisan.view.Activity.FolderListActivity;
import com.sweetorangejuice.artisan.view.Activity.ModifyPersonalActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by as on 2017/5/14.
 */

public class SubProfilesFragment extends Fragment{
    private String objectId;
    private boolean isImageSet = false;
    private int addPhotoRequest = 200;
    private int takePhotoRequest = 300;
    private AlertDialog mySelectDialog;
    private Button modifyPersonalProfilesButton;
    private CircleImageView headCircleImageView;
    private ImageView mBackImageView;
    private TextView usernameTextView;
    private TextView genderTextView;
    private TextView ageTextView;
    private TextView schoolTextView;
    private TextView tagTextView;
    private RelativeLayout mLoadingRelativeLayout;
    private LinearLayout mDataLinearLayout;
    private PersonalBean personalInfo;
    private Uri outputFileUri;// 照相机照片缓存位置
    public static Fragment newInstance(){
        return new SubProfilesFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sub_profiles,container,false);
        modifyPersonalProfilesButton = (Button)view.findViewById(R.id.fragment_personal_modify_profiles);
        headCircleImageView = (CircleImageView)view.findViewById(R.id.fragment_sub_personal_head_portrait);
        usernameTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_name);
        genderTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_profiles_gender_value);
        schoolTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_profiles_school_value);
        ageTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_profiles_age_value);
        tagTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_profiles_tag_value);
        mBackImageView=(ImageView)view.findViewById(R.id.fragment_sub_profiles_back);
        mDataLinearLayout=(LinearLayout)view.findViewById(R.id.fragment_sub_profiles_data);
        mLoadingRelativeLayout=(RelativeLayout)view.findViewById(R.id.fragment_sub_profiles_loading);

        usernameTextView.setText(AVUser.getCurrentUser().getUsername());
        /*
        AVObject personalObject = PersonalController.showPersonalInfo();
        String imageObjectId = (String)personalObject.get("headImage");
        genderTextView.setText((String)personalObject.get("gender"));
        ageTextView.setText((String)personalObject.get("age"));
        schoolTextView.setText((String)personalObject.get("school"));
        tagTextView.setText((String)personalObject.get("tag"));
        FileController.getThumbnailbyObjectId(imageObjectId,100,100);
        //这里写显示图片
        */

        loadProfiles(AVUser.getCurrentUser().getUsername());

        modifyPersonalProfilesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ModifyPersonalActivity.class);
                getContext().startActivity(intent);
            }
        });
        headCircleImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("TAG","Click");
                if (isImageSet == false) {
                    showmySelectDialog("拍照", "从相册选择");
                }
            }
        });
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    private void showmySelectDialog(String one, String two) {
        Log.d("TAG","ShowDialog");
        mySelectDialog = new AlertDialog.Builder(getContext()).create();
        mySelectDialog.setView(LayoutInflater.from(getContext()).inflate(
                R.layout.dialog_select, null));
        mySelectDialog.show();
        mySelectDialog.getWindow().setContentView(R.layout.dialog_select);
        mySelectDialog.getWindow().setGravity(Gravity.BOTTOM);
        mySelectDialog.getWindow().setLayout(
                android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv_one = (TextView) mySelectDialog.getWindow().findViewById(
                R.id.tv_one);
        TextView tv_three = (TextView) mySelectDialog.getWindow().findViewById(
                R.id.tv_three);
        TextView tv_cancel = (TextView) mySelectDialog.getWindow()
                .findViewById(R.id.tv_cancel);
        tv_one.setText(one);
        tv_three.setText(two);
        tv_three.setVisibility(View.VISIBLE);
        tv_one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.
                        CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new
                            String[]{Manifest.permission.CAMERA},1);
                }else{
                    startCamera();
                }
                dismissSelectDialog();
            }
        });
        tv_three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), FolderListActivity.class);
                intent.putExtra("isSingled", true);    //单图片模式
                startActivityForResult(intent, addPhotoRequest);
                dismissSelectDialog();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismissSelectDialog();
            }
        });
    }

    public void dismissSelectDialog() {
        if (mySelectDialog != null) {
            mySelectDialog.dismiss();
        }
    }

    public void startCamera()
    {
        if(Build.VERSION.SDK_INT >= 24){
            outputFileUri = FileProvider.getUriForFile(getContext(),
                    "com.sweetorangejuice.artisan.fileprovider",getOutputFile());
        }else
            outputFileUri = Uri.fromFile(getOutputFile());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, takePhotoRequest);
    }
    private File getOutputFile() {
        Uri outputFileUri = null;
        File file = new File(FileUtils.getInstance(getContext())
                .getStorageDirectory());
        int length = 0;
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.list() != null && file.list().length > 0) {

            length = file.list().length;
            file = new File(file.getPath(), "photo" + length + ".jpg");
        } else {
            file = new File(file.getPath(), "photo0.jpg");
        }
        return file;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Intent data0=data;
        final int requestCode0=requestCode;
        final int resultCode0=resultCode;
        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mDataLinearLayout.setVisibility(View.GONE);
                mLoadingRelativeLayout.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(String... params) {
                if (requestCode0 == addPhotoRequest) {//从选择图片返回的图片url
                    dismissSelectDialog();
                    if (data0 != null) {
                        //Todo 在这里写上传头像的后台代码
                        try {
                            String filePath = (String) data0.getExtras().get("url");
                            Log.d("TAG",filePath);
                            String[] temp = filePath.split("/");    //获得图片的名称和类型
                            String fileName = temp[temp.length - 1];
                            final AVFile file = AVFile.withAbsoluteLocalPath(fileName, filePath);
                            file.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {                            //当该上传操作成功时
                                        AsyncTask<String,Integer,Integer> task1=new AsyncTask<String, Integer, Integer>() {
                                            @Override
                                            protected Integer doInBackground(String... params) {
                                                //Todo 这里写上传头像成功的代码
                                                objectId = file.getObjectId();
                                                Log.d("TAG",objectId);
                                                //Todo 这里写更新用户信息表中头像的代码
                                                AVQuery<AVObject> query=new AVQuery<AVObject>("Person");
                                                query.whereEqualTo("username",AVUser.getCurrentUser().getUsername());
                                                final AVObject user;
                                                try{
                                                    user=query.getFirst();
                                                    Log.d("TAG","user:"+(user==null));
                                                    AVQuery.doCloudQueryInBackground("update Person set headImage='" + objectId + "' where objectId='" + user.getObjectId() + "'",
                                                            new CloudQueryCallback<AVCloudQueryResult>() {
                                                                @Override
                                                                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                                                    // 如果 e 为空，说明保存成功
                                                                    if(e==null)
                                                                    {
                                                                        loadProfiles((String) user.get("username"));
                                                                        Log.d("TAG","保存成功");
                                                                    }else
                                                                        e.printStackTrace();
                                                                }
                                                            });
                                                }catch (AVException ex){
                                                    ex.printStackTrace();
                                                }

                                                return 100;
                                            }
                                        }.execute();
                                    } else {
                                        //Todo 这里写上传头像失败的代码
                                    }
                                }
                            });
                        }catch(IOException ex){

                        }
                    }

                } else if (requestCode0 == takePhotoRequest
                        && resultCode0 == Activity.RESULT_OK) {
                    //Todo 在这写上传头像的后台代码
                    try {
                        String filePath = outputFileUri.getPath();
                        Log.d("Test",filePath);
                        String[] temp = filePath.split("/");    //获得图片的名称和类型
                        String fileName = temp[temp.length - 1];
                        Log.d("TAG","hahahahhaha:"+fileName+" bbbb:"+filePath);
                        File imgFile;
                        //imgFile=new File(new URI(outputFileUri.toString()));
                        final AVFile file = AVFile.withAbsoluteLocalPath(fileName, filePath);
                        //final AVFile file=AVFile.withFile(fileName,imgFile);

                        file.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {                            //当该上传操作成功时
                                    AsyncTask<String,Integer,Integer> task2=new AsyncTask<String, Integer, Integer>() {
                                        @Override
                                        protected Integer doInBackground(String... params) {
                                            //Todo 这里写上传头像成功的代码
                                            Log.d("TAG","SUCCESS");
                                            //Todo 这里写更新用户信息表中头像的代码
                                            objectId = file.getObjectId();
                                            //PersonalController.UploadheadImage(objectId);

                                            AVQuery<AVObject> query=new AVQuery<AVObject>("Person");
                                            query.whereEqualTo("username",AVUser.getCurrentUser().getUsername());
                                            final AVObject user;
                                            try{
                                                user=query.getFirst();
                                                Log.d("TAG","user:"+(user==null));
                                                AVQuery.doCloudQueryInBackground("update Person set headImage='" + objectId + "' where objectId='" + user.getObjectId() + "'",
                                                        new CloudQueryCallback<AVCloudQueryResult>() {
                                                            @Override
                                                            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                                                // 如果 e 为空，说明保存成功
                                                                if(e==null)
                                                                {
                                                                    loadProfiles((String) user.get("username"));
                                                                    Log.d("TAG","保存成功");
                                                                }else
                                                                    e.printStackTrace();
                                                            }
                                                        });
                                            }catch (AVException ex){
                                                ex.printStackTrace();
                                            }

                                            loadProfiles(AVUser.getCurrentUser().getUsername());
                                            return 100;
                                        }
                                    }.execute();
                                } else {
                                    //Todo 这里写上传头像失败的代码
                                    Log.d("TAG","FAILED");
                                }
                            }
                        });
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }


                return 100;
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoadingRelativeLayout.setVisibility(View.VISIBLE);
        mDataLinearLayout.setVisibility(View.GONE);
        loadProfiles(AVUser.getCurrentUser().getUsername());
    }

    private void loadProfiles(String username){
        AsyncTask<String,Integer,Integer> task=new AsyncTask<String, Integer, Integer>() {
            String gender;
            String age;
            String school;
            String label;
            byte[] headPortrait;

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                if(gender!=null){
                    genderTextView.setText(gender);
                }else {
                    genderTextView.setText("空");
                }
                if(age!=null){
                    ageTextView.setText(age);
                }else {
                    ageTextView.setText("空");
                }
                if(school!=null){
                    schoolTextView.setText(school);
                }else {
                    schoolTextView.setText("空");
                }
                if(label!=null){
                    tagTextView.setText(label);
                }else {
                    tagTextView.setText("空");
                }
                if(headPortrait!=null){
                    Glide.with(getActivity()).load(headPortrait).into(headCircleImageView);
                }else {

                }

                mLoadingRelativeLayout.setVisibility(View.GONE);
                mDataLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(String... params) {
                AVQuery<AVObject> query=new AVQuery<AVObject>("Person");
                query.whereEqualTo("username",params[0]);
                List<AVObject> result;
                try {
                    result=query.find();
                    AVObject object=result.get(0);
                    gender=(String)object.get("gender");
                    age=(String)object.get("age");
                    school=(String)object.get("school");
                    label=(String)object.get("tag");
                    headPortrait= FileController.getPicturebyObjectId((String)object.get("headImage"));
                }catch (AVException e){
                    e.printStackTrace();
                }

                return 100;
            }
        }.execute(username);

    }
}
