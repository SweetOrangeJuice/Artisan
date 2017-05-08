package com.sweetorangejuice.artisan.view.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.adapter.GridViewForListView;

import java.util.List;

public class ImagesFragment extends WriteFragment {
	private Context context;
	private GridViewForListView mGridView;

	private View mView;

	public static Fragment newInstance(){
		return new ImagesFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView==null){
			mView = inflater.inflate(R.layout.fragment_image,container,false);
		}
		context = getContext();
		mGridView = (GridViewForListView) mView.findViewById(R.id.gridView1);
		setGridViewForListView(context, mGridView);
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * 获取已选择的图片的路径
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<String> getSelectedImgPath() {
		List<String> imgsPath = getImgPath();
		return imgsPath;
	}
}
