package com.sweetorangejuice.artisan.view.Activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.sweetorangejuice.artisan.controller.adapter.ImageProcessor;
import com.sweetorangejuice.artisan.controller.adapter.ImagesPack;
import com.sweetorangejuice.artisan.controller.adapter.ImgCallBack;
import com.sweetorangejuice.artisan.controller.adapter.ImgsAdapter;
import com.sweetorangejuice.artisan.controller.adapter.ImgsAdapter.OnItemClickClass;
import com.sweetorangejuice.artisan.R;

@SuppressLint("UseSparseArrays")
public class ImgsSelectActivity extends Activity implements OnClickListener {

	Bundle bundle;
	ImagesPack imagesPack;
	GridView imgGridView;
	ImgsAdapter imgsAdapter;
	LinearLayout select_layout;
	ImageProcessor imageProcessor;
	LinearLayout relativeLayout2;
	HashMap<Integer, ImageView> hashImage;
	TextView choise_button;
	ArrayList<String> filelist;
	ImageView img_back;
	ImageView img_ok;
	Context context;
	private boolean isSingled;// 是否只能选择一张图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
		setContentView(R.layout.activity_imgs_select);
		context = ImgsSelectActivity.this;
		img_back = (ImageView) findViewById(R.id.img_back);
		img_ok = (ImageView) findViewById(R.id.img_ok);
		img_back.setColorFilter(Color.WHITE);
		img_back.setOnClickListener(this);
		img_ok.setOnClickListener(this);
		imgGridView = (GridView) findViewById(R.id.gridView1);
		bundle = getIntent().getExtras();
		imagesPack = bundle.getParcelable("data");
		imgsAdapter = new ImgsAdapter(this, imagesPack.folderContent,
				onItemClickClass);
		imgGridView.setAdapter(imgsAdapter);
		select_layout = (LinearLayout) findViewById(R.id.selected_image_layout);
		relativeLayout2 = (LinearLayout) findViewById(R.id.relativeLayout2);
		choise_button = (TextView) findViewById(R.id.button3);
		hashImage = new HashMap<Integer, ImageView>();
		filelist = new ArrayList<String>();
		// imgGridView.setOnItemClickListener(this);
		imageProcessor = new ImageProcessor(this);
		isSingled = getIntent().getBooleanExtra("isSingled", false);
	}

	class BottomImgIcon implements OnItemClickListener {

		int index;

		public BottomImgIcon(int index) {
			this.index = index;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

		}
	}

	@SuppressLint("NewApi")
	public ImageView iconImage(String filepath, int index, CheckBox checkBox)
			throws FileNotFoundException {
		LayoutParams params = new LayoutParams(
				relativeLayout2.getMeasuredHeight() - 10,
				relativeLayout2.getMeasuredHeight() - 10);
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(params);
		imageView.setBackgroundResource(R.drawable.gallery_wait);
		float alpha = 1.0f;
		imageView.setAlpha(alpha);
		imageProcessor.Execute(imageView, imgCallBack, filepath);
		imageView.setOnClickListener(new ImgOnclick(filepath, checkBox));
		return imageView;
	}

	ImgCallBack imgCallBack = new ImgCallBack() {
		@Override
		public void ImgBinder(ImageView imageView, Bitmap bitmap) {
			imageView.setImageBitmap(bitmap);
		}
	};

	class ImgOnclick implements OnClickListener {
		String filepath;
		CheckBox checkBox;

		public ImgOnclick(String filepath, CheckBox checkBox) {
			this.filepath = filepath;
			this.checkBox = checkBox;
		}

		@Override
		public void onClick(View arg0) {
			checkBox.setChecked(false);
			select_layout.removeView(arg0);
			choise_button.setText("已选择(" + select_layout.getChildCount() + ")张");
			filelist.remove(filepath);
		}
	}

	ImgsAdapter.OnItemClickClass onItemClickClass = new OnItemClickClass() {
		@Override
		public void OnItemClick(View v, int Position, CheckBox checkBox) {
			String filapath = imagesPack.folderContent.get(Position);
			if (isSingled) {
				Intent intent = new Intent();
				intent.putExtra("url", filapath);
				setResult(201, intent);
				finish();
			}
			if (checkBox.isChecked()) {
				checkBox.setChecked(false);
				select_layout.removeView(hashImage.get(Position));
				filelist.remove(filapath);
				choise_button.setText("已选择(" + select_layout.getChildCount()
						+ ")张");
			} else {
				try {
					checkBox.setChecked(true);
					ImageView imageView = iconImage(filapath, Position,
							checkBox);
					if (imageView != null) {
						hashImage.put(Position, imageView);
						filelist.add(filapath);
						select_layout.addView(imageView);
						choise_button.setText("已选择("
								+ select_layout.getChildCount() + ")张");
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	};

	public void tobreak(View view) {
		finish();
	}

	/**
	 * FIXME 亲只需要在这个方法把选中的文档目录已list的形式传过去即可
	 */
	public void sendfiles() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("files", filelist);
		intent.putExtras(bundle);
		setResult(201, intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img_ok:
			sendfiles();
			break;
		}
	}
}
