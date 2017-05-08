package com.sweetorangejuice.artisan.view.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.adapter.ImageProcessor;
import com.sweetorangejuice.artisan.controller.adapter.ImagesPack;
import com.sweetorangejuice.artisan.controller.adapter.ImgFileListAdapter;

/**
* 用于显示文件夹的活动
*/
public class FolderListActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	ListView listView;
	ImageProcessor imageProcessor;
	ImgFileListAdapter listAdapter;
	List<ImagesPack> localImgList;
	private ImageView img_back;

	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
		setContentView(R.layout.activity_folder_list);
		//运行时权限申请
		if(ContextCompat.checkSelfPermission(FolderListActivity.this, android.Manifest.permission.
				READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(FolderListActivity.this,new
					String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
		}else{
			initialize();
		}
	}
	//界面的初始化函数
	private void initialize(){
		listView = (ListView) findViewById(R.id.folder_listview);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setColorFilter(Color.WHITE);
		img_back.setOnClickListener(this);
		imageProcessor = new ImageProcessor(this);
		localImgList = imageProcessor.LocalImgFileList();
		List<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
		if (localImgList != null) {
			for (int i = 0; i < localImgList.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("filecount", localImgList.get(i).folderContent.size() + "张");
				map.put("imgpath",
						localImgList.get(i).folderContent.get(0) == null ? null
								: (localImgList.get(i).folderContent.get(0)));
				map.put("filename", localImgList.get(i).folderName);
				listdata.add(map);
			}
		}
		listAdapter = new ImgFileListAdapter(this, listdata);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,String[] permissions,
										   int[] grantResults){
		switch(requestCode){
			case 1:
				if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
					initialize();
				}else{
					Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
					this.finish();
				}
				break;
			default:
		}
	}

	/**
	 * ListView项目点击处理函数
	 * 		被点击则将对应子目录文件序列化
	 * 		扔进bundle传入第二个activity中
	 * @param parent
	 * @param view
	 * @param position
     * @param id
     */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, ImgsSelectActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("data", localImgList.get(position));		//将对应文件夹的图片信息传入
		intent.putExtras(bundle);
		if (getIntent().getBooleanExtra("isSingled", false)) {
			intent.putExtra("isSingled",
					getIntent().getBooleanExtra("isSingled", false));
		}
		startActivityForResult(intent, 200);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 200 && resultCode == 201) {

			setResult(101, data);		//将数据回传至上一层的Activity
			finish();

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		}

	}

}
