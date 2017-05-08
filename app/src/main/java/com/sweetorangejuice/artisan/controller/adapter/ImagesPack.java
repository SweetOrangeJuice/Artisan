package com.sweetorangejuice.artisan.controller.adapter;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * PhotoPack类
 * 		将图片的目录和目录下的图片文件清单打包起来
 * 		实现Parcelable接口
 * 		传递给Adapter供显示使用
 * @version v1.0
 */
@SuppressLint("ParcelCreator")
public class ImagesPack implements Parcelable {
	public String folderName;									//文件夹的名称
	public List<String> folderContent = new ArrayList<String>();		//文件夹下的图片清单

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {			//序列化函数
		parcel.writeString(folderName);
		parcel.writeList(folderContent);
	}

	public static final Creator<ImagesPack> CREATOR = new Creator<ImagesPack>() {

		@Override
		public ImagesPack[] newArray(int size) {
			return new ImagesPack[size];
		}

		@SuppressWarnings("unchecked")
		@Override
		public ImagesPack createFromParcel(Parcel parcel) {		//反序列化函数
			ImagesPack imagesPack = new ImagesPack();
			imagesPack.folderName = parcel.readString();
			imagesPack.folderContent = parcel.readArrayList(ImagesPack.class
					.getClassLoader());
			return imagesPack;
		}

	};
}
