package com.sweetorangejuice.artisan.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.BaseAdapter;

import com.sweetorangejuice.artisan.util.TypeChange;

public class ImagesBaseActivity extends BaseActivity {

	// 是否允许全屏
	private boolean mAllowFullScreen = true;
	public static TypeChange typeChange;

	public void setAllowFullScreen(boolean allowFullScreen) {
		this.mAllowFullScreen = allowFullScreen;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 竖屏锁定
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (mAllowFullScreen) {
			requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
		}
		typeChange = new TypeChange();
	}

}
