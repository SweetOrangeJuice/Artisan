package com.sweetorangejuice.artisan.view.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.FileController;
import com.sweetorangejuice.artisan.controller.PersonalController;
import com.sweetorangejuice.artisan.model.PersonalBean;
import com.sweetorangejuice.artisan.util.FileUtils;
import com.sweetorangejuice.artisan.util.LogUtil;
import com.sweetorangejuice.artisan.view.Activity.FolderListActivity;
import com.sweetorangejuice.artisan.view.Activity.ModifyPersonalActivity;

import java.io.File;
import java.io.IOException;

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
        schoolTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_profiles_age_value);
        ageTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_profiles_age_value);
        tagTextView = (TextView)view.findViewById(R.id.fragment_sub_personal_profiles_tag_edit);
        mBackImageView=(ImageView)view.findViewById(R.id.fragment_sub_profiles_back);
        usernameTextView.setText(AVUser.getCurrentUser().getUsername());
        AVObject personalObject = PersonalController.showPersonalInfo();
        String imageObjectId = (String)personalObject.get("headImage");
        genderTextView.setText((String)personalObject.get("gender"));
        ageTextView.setText((String)personalObject.get("age"));
        schoolTextView.setText((String)personalObject.get("school"));
        tagTextView.setText((String)personalObject.get("tag"));
        FileController.getThumbnailbyObjectId(imageObjectId,100,100);
        //这里写显示图片

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
        if (requestCode == addPhotoRequest) {//从选择图片返回的图片url
            dismissSelectDialog();
            if (data != null) {
                //Todo 在这里写上传头像的后台代码
                try {
                    String filePath = (String) data.getExtras().get("url");
                    Log.d("TAG",filePath);
                    String[] temp = filePath.split("/");    //获得图片的名称和类型
                    String fileName = temp[temp.length - 1];
                    final AVFile file = AVFile.withAbsoluteLocalPath(fileName, filePath);
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {                            //当该上传操作成功时
                                //Todo 这里写上传头像成功的代码
                                objectId = file.getObjectId();
                                Log.d("TAG",objectId);
                                //Todo 这里写更新用户信息表中头像的代码
                                AVQuery.doCloudQueryInBackground("update Person set headImage='" + objectId + "' where username='" + AVUser.getCurrentUser().getUsername() + "'",
                                        new CloudQueryCallback<AVCloudQueryResult>() {
                                            @Override
                                            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                                // 如果 e 为空，说明保存成功
                                                if(e==null)
                                                {
                                                    Log.d("TAG","保存成功");
                                                }else
                                                    e.printStackTrace();
                                            }
                                        });
                            } else {
                                //Todo 这里写上传头像失败的代码
                            }
                        }
                    });
                }catch(IOException ex){

                }
            }

        } else if (requestCode == takePhotoRequest
                && resultCode == Activity.RESULT_OK) {
            //Todo 在这写上传头像的后台代码
            try {
                String filePath = outputFileUri.getPath();
                String[] temp = filePath.split("/");    //获得图片的名称和类型
                String fileName = temp[temp.length - 1];
                final AVFile file = AVFile.withAbsoluteLocalPath(fileName, filePath);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {                            //当该上传操作成功时
                            //Todo 这里写上传头像成功的代码
                            Log.d("TAG","SUCCESS");
                            //Todo 这里写更新用户信息表中头像的代码
                            objectId = file.getObjectId();
                            PersonalController.UploadheadImage(objectId);
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
    }
}
