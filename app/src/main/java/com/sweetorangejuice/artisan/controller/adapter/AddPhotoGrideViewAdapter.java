package com.sweetorangejuice.artisan.controller.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.util.TypeChange;

import java.util.ArrayList;
import java.util.List;

public class AddPhotoGrideViewAdapter extends BaseAdapter {
	// List<String> list;
	List<Bitmap> listForAdapter;
	List<String> listForImgPath;
	Context context;
	int screenWidth;
	TypeChange typeChange;

	public AddPhotoGrideViewAdapter(Context context, List<String> list) {
		this.context = context;
		typeChange = new TypeChange();
		listForImgPath = list;
		// this.list = list;
		getBitmapFromPath(list);

		getSysInfo(context);
	}

	public void requestNotifyDataSetChanged(List<String> list) {
		listForImgPath = list;
		// this.list = list;
		// addExtraPhoto(this.list);
		getBitmapFromPath(list);
		notifyDataSetChanged();
	}

	public List<String> getImgPath() {
		return listForImgPath;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listForAdapter.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listForAdapter.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView = new ImageView(context);
		imageView.setImageBitmap(listForAdapter.get(position));
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, (screenWidth / 4) - 10));
		imageView.setBackgroundColor(Color.WHITE);

		return imageView;
	}

	private void getSysInfo(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;

	}

	// private void addExtraPhoto(List<String> list) {
	// Log.i("", "---list:" + list);
	// Log.i("", "---context:" + context);
	// if (list == null)
	// return;
	// list.add(BitmapFactory.decodeResource(context.getResources(),
	// R.drawable.nullimage));
	// }

	private void getBitmapFromPath(List<String> list) {
		listForAdapter = new ArrayList<Bitmap>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				listForAdapter
						.add(typeChange.getZoomBitmap(list.get(i), 50, 50));
			}
		}
		listForAdapter.add(BitmapFactory.decodeResource(context.getResources(),
				R.drawable.gallery_nullimage));
	}
}
