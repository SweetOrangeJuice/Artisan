package com.sweetorangejuice.artisan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ImageProcessor {

	Context context;							//执行Util的上下文

	public ImageProcessor(Context context) {				//构造函数
		this.context = context;
	}

	/**
	 * listAllImagesDir函数
	 * 使用ContentResolver访问安卓图库数据库
	 * 		输出有全部图片地址的ArrayList
	 *
	 * @version v1.0
	 * @return ArrayList
	 */
	public ArrayList<String> ListAllImagesDir() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 		//从安卓图库数据库中获取所有图片资源
		Uri uri = intent.getData();									//从intent中取回获得资源的URI
		ArrayList<String> list = new ArrayList<String>();			//存地址的列表
		String[] projection = { MediaStore.Images.Media.DATA };		//指定查询的列名
		Cursor cursor = context.getContentResolver().query(uri, projection, null,
				null, null);										//对图库数据进行查询
		while (cursor.moveToNext()) {								//当有数据的时候
			String path = cursor.getString(0);						//获取图片地址
			Log.d("Image",path);
			Log.d("Image",new File(path).getAbsolutePath());		//测试
			list.add(new File(path).getAbsolutePath());				//将图片的绝对地址加入列表
		}
		return list;
	}

	/**
	 * LocalImgFileList函数
	 * 		返回一个按照文件夹和子文件分好类的文件包裹清单
	 * 		可以直接用于绑定并显示
	 * @return
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ImagesPack> LocalImgFileList() {
		List<ImagesPack> data = new ArrayList<ImagesPack>();
		List<String> allImgList = ListAllImagesDir();
		String folderName = "";
		if (allImgList != null) {
			Set set = new TreeSet();
			for (int i = 0; i < allImgList.size(); i++) {
				set.add(getFileFolder(allImgList.get(i)));
			}
			String[] folderList = (String[]) set.toArray(new String[0]);
			for (int i = 0; i < folderList.length; i++) {
				folderName = folderList[i];
				ImagesPack imagesPack = new ImagesPack();
				imagesPack.folderName = folderName;
				data.add(imagesPack);
			}
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < allImgList.size(); j++) {
					if (data.get(i).folderName.equals(getFileFolder(allImgList.get(j)))) {
						data.get(i).folderContent.add(allImgList.get(j));
					}
				}
			}
		}
		return data;
	}

	/**
	 * getTreatedBitmap
	 * 		生成指定形状的Bitmap对象供显示
	 * 		按照给定路径查找对应图片
	 * 		转码缩放
	 * @param imageFilePath
	 * @param dw
	 * @param dh
	 * @return
	 * @throws FileNotFoundException
     */
	public Bitmap getTreatedBitmap(Uri imageFilePath, int dw, int dh) throws FileNotFoundException {
		/**
		 * 为了计算缩放的比例，我们需要获取整个图片的尺寸，而不是图片
		 * BitmapFactory.Options类中有一个布尔型变量inJustDecodeBounds，将其设置为true
		 * 这样，我们获取到的就是图片的尺寸，而不用加载图片了。
		 * 当我们设置这个值的时候，我们接着就可以从BitmapFactory.Options的outWidth和outHeight中获取到值
		 */
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		// 由于使用了MediaStore存储，这里根据URI获取输入流的形式
		Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(imageFilePath), null, option);

		int wRatio = (int) Math.ceil(option.outWidth / (float) dw); 	//计算宽度缩放比例
		int hRatio = (int) Math.ceil(option.outHeight / (float) dh); 	//计算高度缩放比例

		/**
		 * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。 如果高和宽不是全都超出了屏幕，那么无需缩放。
		 * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》 这需要判断wRatio和hRatio的大小
		 * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。 缩放使用的还是inSampleSize变量
		 */
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				option.inSampleSize = wRatio;
			} else {
				option.inSampleSize = hRatio;
			}
		}
		option.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		bitmap = BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(imageFilePath), null, option);
		return bitmap;
	}

	/**
	 * getFileFolder函数
	 * 		输入文件的绝对路径
	 * 		输出文件的上一级文件夹
	 * 		用于图片的按文件夹输入
	 * @param path
	 * @version v1.0
	 * @return
     */
	public String getFileFolder(String path) {
		String filepath[] = path.split("/");
		if (filepath != null) {
			return filepath[filepath.length - 2];
		}
		return null;
	}

	/**
	 * imgExcute函数
	 * 		负责处理并且绑定处理好的图片和视图构件
	 * @param imageView 绑定的视图构件
	 * @param icb		负责绑定的函数
	 * @param params	传入的图片地址参数
     */
	public void Execute(ImageView imageView, ImgCallBack icb,
						String... params) {
		LoadBitmapAsynk loadBitmapAsynk = new LoadBitmapAsynk(imageView, icb);
		loadBitmapAsynk.execute(params);
	}

	/**
	 * LoadBitmapAsynk类
	 * 		负责处理图片的异步操作
	 */
	public class LoadBitmapAsynk extends AsyncTask<String, Integer, Bitmap> {

		ImageView imageView;
		ImgCallBack icb;

		LoadBitmapAsynk(ImageView imageView, ImgCallBack icb) {
			this.imageView = imageView;
			this.icb = icb;
		}

		/**
		 * doInBackground函数
		 * 		在子线程中异步执行的操作
		 * 		获取列表中的图片并且进行缩放处理
		 * 		输出处理好的图片
		 * @param params
         * @return
         */
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			try {
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						bitmap = getTreatedBitmap(
								Uri.fromFile(new File(params[i])), 200, 200);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		/**
		 * onPostExecute函数
		 * 		处理完之后执行的操作
		 * 		负责回调一个将图片和imageView绑定的函数
		 * @param result
         */
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				// imageView.setImageBitmap(result);
				icb.ImgBinder(imageView, result);
			}
		}

	}

}
