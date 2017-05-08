package com.sweetorangejuice.artisan.controller.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * ImgCallBack接口
 * 		用于在异步处理图片时回调
 * 		负责将处理好的Bitmap和imageView进行绑定
 */
public interface ImgCallBack {
	public void ImgBinder(ImageView imageView, Bitmap bitmap);
}
