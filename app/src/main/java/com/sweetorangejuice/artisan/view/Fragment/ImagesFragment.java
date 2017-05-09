package com.sweetorangejuice.artisan.view.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.MomentsController;
import com.sweetorangejuice.artisan.controller.adapter.GridViewForListView;
import com.sweetorangejuice.artisan.model.MomentsBean;

import java.util.ArrayList;
import java.util.List;

public class ImagesFragment extends WriteFragment {

	private Context context;
	private AVUser mCurrentUser;

	private Button mLoginButton;
	private RelativeLayout mBlockRelativeLayout;
	private GridViewForListView mGridView;
	private LinearLayout mImageContentLayout;
	private TextView mPublishTextView;
	private EditText mPublishEditText;
	private Button mDrawingsButton;
	private Button mBuildingsButton;
	private Button mHandworkButton;
	private Button mSculpturesButton;
	private Button mGraphicsButton;
	private TextView mTextLimitTextView;
	private TextView mImageLimitTextView;

	private boolean isDrawingsButtonPressed=false;
	private boolean isBuildingsButtonPressed=false;
	private boolean isHandworkButtonPressed=false;
	private boolean isSculpturesButtonPressed=false;
	private boolean isGraphicsButtonPressed=false;
	private int mNumberOfPress=0;

	private View mView;

	public static Fragment newInstance(){
		return new ImagesFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCurrentUser=AVUser.getCurrentUser();
		isDrawingsButtonPressed=false;
		isBuildingsButtonPressed=false;
		isHandworkButtonPressed=false;
		isSculpturesButtonPressed=false;
		isGraphicsButtonPressed=false;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mView==null){
			mView = inflater.inflate(R.layout.fragment_image,container,false);
		}
		context = getContext();

		mGridView = (GridViewForListView) mView.findViewById(R.id.gridView1);
		mLoginButton=(Button)mView.findViewById(R.id.fragment_moments_login);
		mBlockRelativeLayout=(RelativeLayout)mView.findViewById(R.id.fragment_moments_block_layout);
		mImageContentLayout=(LinearLayout)mView.findViewById(R.id.fragment_image_content);
		mPublishTextView=(TextView)mView.findViewById(R.id.fragment_image_publish);
		mPublishEditText=(EditText)mView.findViewById(R.id.fragment_image_edit_text);
		mDrawingsButton=(Button)mView.findViewById(R.id.fragment_image_drawings);
		mBuildingsButton=(Button)mView.findViewById(R.id.fragment_image_buildings);
		mHandworkButton=(Button)mView.findViewById(R.id.fragment_image_handwork);
		mSculpturesButton=(Button)mView.findViewById(R.id.fragment_image_sculptures);
		mGraphicsButton=(Button)mView.findViewById(R.id.fragment_image_graphics);
		mTextLimitTextView=(TextView)mView.findViewById(R.id.fragment_image_text_limit);
		mImageLimitTextView=(TextView)mView.findViewById(R.id.fragment_image_limit);

		setGridViewForListView(context, mGridView);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MomentsController.login();
			}
		});
		mDrawingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTagPressed((Button)v,isDrawingsButtonPressed);
				isDrawingsButtonPressed=!isDrawingsButtonPressed;
			}
		});
		mBuildingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTagPressed((Button)v,isBuildingsButtonPressed);
				isBuildingsButtonPressed=!isBuildingsButtonPressed;
			}
		});
		mHandworkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTagPressed((Button)v,isHandworkButtonPressed);
				isHandworkButtonPressed=!isHandworkButtonPressed;
			}
		});
		mGraphicsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTagPressed((Button)v,isGraphicsButtonPressed);
				isGraphicsButtonPressed=!isGraphicsButtonPressed;
			}
		});
		mSculpturesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTagPressed((Button)v,isSculpturesButtonPressed);
				isSculpturesButtonPressed=!isSculpturesButtonPressed;
			}
		});
		mPublishTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				publish();
			}
		});
		mPublishEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int length=s.length();
				mTextLimitTextView.setText(getString(R.string.fragment_image_text_limit,length));
				if(length>140){
					mTextLimitTextView.setTextColor(Color.RED);
				}else{
					mTextLimitTextView.setTextColor(0x60000000);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		updateUI();

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurrentUser=AVUser.getCurrentUser();
		updateUI();
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

	private void updateUI(){
		if(mCurrentUser!=null){
			mBlockRelativeLayout.setVisibility(View.GONE);
			mImageContentLayout.setVisibility(View.VISIBLE);
		}else{
			mBlockRelativeLayout.setVisibility(View.VISIBLE);
			mImageContentLayout.setVisibility(View.GONE);
		}
		//int length=getSelectedImgPath().size();
		//mImageLimitTextView.setText(getString(R.string.fragment_image_limit,length));
		//Log.d("TAG","haha");
	}

	private void onTagPressed(Button tag,boolean isButtonPressed){
		if(isButtonPressed){
			tag.setBackgroundResource(R.drawable.fragment_unseleted_button);
			mNumberOfPress--;
		}else{
			tag.setBackgroundResource(R.drawable.fragment_seleted_button);
			mNumberOfPress++;
		}
	}

	private void clear(){
		clearImage();
		mPublishEditText.setText("");
		isSculpturesButtonPressed=true;
		isHandworkButtonPressed=true;
		isDrawingsButtonPressed=true;
		isBuildingsButtonPressed=true;
		isGraphicsButtonPressed=true;
		onTagPressed(mBuildingsButton,isBuildingsButtonPressed);
		onTagPressed(mDrawingsButton,isDrawingsButtonPressed);
		onTagPressed(mGraphicsButton,isGraphicsButtonPressed);
		onTagPressed(mSculpturesButton,isSculpturesButtonPressed);
		onTagPressed(mHandworkButton,isHandworkButtonPressed);
		isSculpturesButtonPressed=false;
		isHandworkButtonPressed=false;
		isDrawingsButtonPressed=false;
		isBuildingsButtonPressed=false;
		isGraphicsButtonPressed=false;
		mNumberOfPress=0;
	}

	private void publish(){
		if(mNumberOfPress==0){
			Toast.makeText(getActivity(),"至少选择一个标签，请重试",Toast.LENGTH_SHORT).show();
		}else if(mNumberOfPress>1){
			Toast.makeText(getActivity(),"只能选择一个标签，请重试",Toast.LENGTH_SHORT).show();
		}else{
			int textLength;
			textLength=mPublishEditText.length();
			if(textLength>140){
				Toast.makeText(getActivity(),"最多只能输入140字文字，请删减一些文字",Toast.LENGTH_SHORT).show();
			}else if(textLength<=0){
				Toast.makeText(getActivity(),"文本框为空，无法发布",Toast.LENGTH_SHORT).show();
			}else{
				int images;
				images=getSelectedImgPath().size();
				if(images>9){
					Toast.makeText(getActivity(),"最多只能发布9张图片，请删减一些图片",Toast.LENGTH_SHORT).show();
				}else if(images<=0){
					Toast.makeText(getActivity(),"请至少上传1张图片",Toast.LENGTH_SHORT).show();
				}else{
					MomentsBean momentsBean=new MomentsBean();
					momentsBean.setText(mPublishEditText.getText().toString());
					if(isDrawingsButtonPressed){
						momentsBean.setTag("drawings");
					}else if(isBuildingsButtonPressed){
						momentsBean.setTag("buildings");
					}else if(isHandworkButtonPressed){
						momentsBean.setTag("handwork");
					}else if(isSculpturesButtonPressed){
						momentsBean.setTag("sculptures");
					}else{
						momentsBean.setTag("graphics");
					}
					ArrayList<String> imagesList=(ArrayList<String>)getSelectedImgPath();
					for(int i=0;i<imagesList.size();i++){
						momentsBean.addImages(imagesList.get(i));
					}
					Log.d("TAG",""+imagesList.size());
					MomentsController.distributeMoments(momentsBean);
					Toast.makeText(getActivity(),"发布中...",Toast.LENGTH_SHORT).show();

				}
			}
		}
	}

}
