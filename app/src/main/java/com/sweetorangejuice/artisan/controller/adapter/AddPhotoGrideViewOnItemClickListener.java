package com.sweetorangejuice.artisan.controller.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.util.FileUtils;
import com.sweetorangejuice.artisan.view.Activity.FolderListActivity;

import java.io.File;
import java.util.List;

public class AddPhotoGrideViewOnItemClickListener implements
		OnItemClickListener, OnClickListener {
	private Context context;
	private AddPhotoGrideViewAdapter adapter;
	private Fragment fragment;
	private List<String> listfile;
	private int addPhotoRequest;// 打开添加图片activity的请求码.
	private int takePhotoRequest;// 添加拍照图片activity的请求码.
	private AlertDialog mySelectDialog;
	private Uri outputFileUri;// 照相机照片缓存位置

	public AddPhotoGrideViewOnItemClickListener(Context context,
												Fragment fragment, AddPhotoGrideViewAdapter adapter,
												List<String> listfile, int addPhotoRequest, int takePhotoRequest) {
		this.context = context;
		this.adapter = adapter;
		this.fragment = fragment;
		this.listfile = listfile;
		this.addPhotoRequest = addPhotoRequest;
		this.takePhotoRequest = takePhotoRequest;
	}

	public void requestNotifyDataSetChanged(List<String> list,
			AddPhotoGrideViewAdapter adapter) {
		this.adapter = adapter;
		this.listfile = list;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (adapter != null) {
			if ((adapter.getCount() - 1) == position) {
				if (listfile.size() >= 9) {
					Toast.makeText(context, "最多只能添加九张照片!", Toast.LENGTH_LONG)
							.show();
				} else {
					showmySelectDialog("拍照", "从相册选择");
				}
			} else {
				dialog(position);
			}
		}
	}

	/**
	 * 
	 * @param one 上面的选项
	 * @param two 下面的选项
	 */
	private void showmySelectDialog(String one, String two) {

		mySelectDialog = new Builder(context).create();

		mySelectDialog.setView(LayoutInflater.from(context).inflate(
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
		tv_one.setOnClickListener(this);
		tv_three.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);

	}

	public void dismissSelectDialog() {
		if (mySelectDialog != null) {
			mySelectDialog.dismiss();
		}
	}

	private void dialog(int p) {
		final int position = p;
		Builder builder = new Builder(context);
		builder.setMessage("删除此图片？");
		builder.setTitle("删除图片");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				listfile.remove(position);
				if (adapter != null) {
					adapter.requestNotifyDataSetChanged(listfile);
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private File getOutputFile() {
		Uri outputFileUri = null;
		File file = new File(FileUtils.getInstance(context)
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

	public String getPhotoPath() {
		Log.d("Test",outputFileUri.getPath());
		return outputFileUri.getPath();
	}
	public void startCamera()
	{
		if(Build.VERSION.SDK_INT >= 24){
			outputFileUri = FileProvider.getUriForFile(context,
					"com.sweetorangejuice.artisan.fileprovider",getOutputFile());
		}else
			outputFileUri = Uri.fromFile(getOutputFile());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		fragment.startActivityForResult(intent, takePhotoRequest);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_one:
			if(ContextCompat.checkSelfPermission(context, Manifest.permission.
					CAMERA)!= PackageManager.PERMISSION_GRANTED){
				ActivityCompat.requestPermissions(fragment.getActivity(),new
						String[]{Manifest.permission.CAMERA},1);
			}else{
				startCamera();
			}
			dismissSelectDialog();
			break;
		case R.id.tv_three:
			Intent intent = new Intent();
			intent.setClass(context, FolderListActivity.class);
			// intent.putExtra("isSingled", false);
			fragment.startActivityForResult(intent, addPhotoRequest);
			dismissSelectDialog();
			break;
		case R.id.tv_cancel:
			dismissSelectDialog();
			break;
		}
	}
}
