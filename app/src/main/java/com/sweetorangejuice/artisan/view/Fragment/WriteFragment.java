package com.sweetorangejuice.artisan.view.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.sweetorangejuice.artisan.base.ImagesBaseActivity;
import com.sweetorangejuice.artisan.controller.adapter.AddPhotoGrideViewAdapter;
import com.sweetorangejuice.artisan.controller.adapter.AddPhotoGrideViewOnItemClickListener;
import com.sweetorangejuice.artisan.controller.adapter.GridViewForListView;
import com.sweetorangejuice.artisan.util.TypeChange;

public class WriteFragment extends Fragment implements OnClickListener {
	private GridViewForListView mGridView;
	private List<Bitmap> dataForGV;
	private List<String> listfile;// 上传的照片在手机中的路径

	private AddPhotoGrideViewAdapter adapter;
	private AddPhotoGrideViewOnItemClickListener addPhotoGrideViewOnItemClickListener;
	private Context context;
	private Activity activity;
	private final int TAKE_PICTURE = 200;
	private final int SELECTPHOTO = 300;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setGridViewForListView(Context context,
			GridViewForListView mGridView) {
		this.mGridView = mGridView;
		this.context = context;
		dataForGV = new ArrayList<Bitmap>();
		listfile = new ArrayList<String>();
		getImageBitmap();
	}

	public List<String> getImgPath() {
		return adapter.getImgPath();
	}

	private void getImageBitmap() {
		dataForGV.clear();
		Bitmap bitmap = null;
		if (listfile != null && listfile.size() != 0) {
			for (int i = 0; i < listfile.size(); i++) {
				try {
					TypeChange typeChange = new TypeChange();
					bitmap = typeChange.getZoomBitmap(listfile.get(i), 50, 50);
					dataForGV.add(bitmap);

				} catch (OutOfMemoryError e) {
					Log.i("", "OutOfMemoryError");
				}
			}
		}
		if (adapter == null && dataForGV != null) {
			adapter = new AddPhotoGrideViewAdapter(context, listfile);
			mGridView.setAdapter(adapter);
			addPhotoGrideViewOnItemClickListener = new AddPhotoGrideViewOnItemClickListener(
					context, this, adapter, listfile, SELECTPHOTO, TAKE_PICTURE);
			mGridView.setOnItemClickListener(addPhotoGrideViewOnItemClickListener);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELECTPHOTO) {
			if (addPhotoGrideViewOnItemClickListener != null)
				addPhotoGrideViewOnItemClickListener.dismissSelectDialog();
			if (data != null) {
				ArrayList<String> currentListfile = new ArrayList<String>();
				Bundle bundle = data.getExtras();
				currentListfile = bundle.getStringArrayList("files");
				for (int i = 0; i < currentListfile.size(); i++) {
					if (listfile.size() < 9) {
						listfile.add(currentListfile.get(i));
					} else {
						Toast.makeText(context, "最多只能添加九张照片!",
								Toast.LENGTH_LONG).show();
						break;
					}
				}
				getImageBitmap();
				if (adapter != null) {
					adapter.requestNotifyDataSetChanged(listfile);
				}
				if (addPhotoGrideViewOnItemClickListener != null) {
					addPhotoGrideViewOnItemClickListener
							.requestNotifyDataSetChanged(listfile, adapter);
				}
			}

		} else if (requestCode == TAKE_PICTURE
				&& resultCode == Activity.RESULT_OK) {
			if (addPhotoGrideViewOnItemClickListener != null)
				addPhotoGrideViewOnItemClickListener.dismissSelectDialog();

			if (addPhotoGrideViewOnItemClickListener.getPhotoPath() != null) {
				listfile.add(addPhotoGrideViewOnItemClickListener
						.getPhotoPath());
				getImageBitmap();
				if (adapter != null) {
					adapter.requestNotifyDataSetChanged(listfile);
				}
				if (addPhotoGrideViewOnItemClickListener != null) {
					addPhotoGrideViewOnItemClickListener
							.requestNotifyDataSetChanged(listfile, adapter);
				}
			}
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,String[] permissions,
										   int[] grantResults){
		switch(requestCode){
			case 1:
				if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
					addPhotoGrideViewOnItemClickListener.startCamera();
				}else{
					Toast.makeText(context,"You denied the permission",Toast.LENGTH_SHORT).show();
					getActivity().finish();
				}
				break;
			default:
		}
	}
	@Override
	public void onClick(View v) {

	}
}
