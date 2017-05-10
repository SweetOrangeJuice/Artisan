package com.sweetorangejuice.artisan.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
	private static FileUtils instance;						//FileUtils的实例对象
	private static final String TAG = "FileUtils";			//用于输出错误的标签

	private static String sdAppDataPath = null;			//sd卡的应用数据目录
	private static String sdAppCachePath = null;		//sd卡的应用缓存目录
	private static String appDataPath = null;			//手机上的应用数据目录
	private static String appCachePath = null;			//手机上的应用缓存目录

	private final static String IMAGE_FOLDER = "/ArtisanImage";			//保存Image的目录名
	private final static String USER_IMAGE_FOLDER = "/UserImage";		//保存用户手动保存的Image的目录名
	private final static String USER_FILE_FOLDER = "/UserFile";			//保存文件的目录名
	private final static String UPLOAD_IMAGE_FOLDER = "/UploadImage";	//保存上传的图片的目录名

	public FileUtils(Context context) {
		sdAppDataPath = context.getExternalFilesDir(null).getPath();	//获取sd卡上的应用数据目录
		sdAppCachePath = context.getExternalCacheDir().getPath();		//获取sd卡上的应用缓存目录
		appDataPath = context.getFilesDir().getPath();					//获取手机上的应用数据目录
		appCachePath = context.getCacheDir().getPath();					//获取手机上的应用缓存目录
	}

	public static FileUtils getInstance(Context context) {
		if (instance == null) {
			instance = new FileUtils(context);
		}
		return instance;
	}

	/**
	 * 获取手机缓存目录，若有SD卡就是SD卡缓存目录，否则为手机缓存目录
	 * 
	 * @return
	 */
	public String getCacheDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdAppCachePath : appCachePath;
	}

	/**
	 * 获取手机文件目录，若有SD卡就是SD卡文件目录，否则为手机文件目录
	 *
	 * @return
	 */
	public String getFilesDirectory(){
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdAppDataPath : appDataPath;
	}

	/**
	 * 获取储存Image的目录
	 * @return
	 */
	public String getStorageDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdAppCachePath + IMAGE_FOLDER
				: appCachePath + IMAGE_FOLDER;
	}

	/**
	 * 获取用户手动保存的Image的目录
	 * 
	 * @return
	 */
	public String getImageDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdAppCachePath + USER_IMAGE_FOLDER
				: appCachePath + USER_IMAGE_FOLDER;
	}

	/**
	 * 获取储存文件的目录
	 * 
	 * @return
	 */
	public String getFileDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdAppCachePath + USER_FILE_FOLDER
				: appCachePath + USER_FILE_FOLDER;
	}

	/**
	 * 获取上传的图片的目录名
	 * 
	 * @return
	 */
	public String getUpPhotosFileDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdAppCachePath + UPLOAD_IMAGE_FOLDER
				: appCachePath + UPLOAD_IMAGE_FOLDER;
	}

	/**
	 * 获取录音的目录名
	 * 
	 * @return
	 */
	public String getRecordFileDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdAppCachePath : appCachePath;
	}

	/**
	 * 创建下载更新的apk的路径
	 * 
	 * @param name
	 */
	public File createUpdataFile(String name) {
		File file = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			File storageDir = new File(getFileDirectory());
			file = new File(storageDir + "/" + name + ".apk");

			if (!storageDir.exists()) {
				storageDir.mkdirs();
			}
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return file;
	}

	/**
	 * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	 * 
	 * @param fileName
	 * @param bitmap
	 * @throws IOException
	 */
	public void saveBitmap(String fileName, Bitmap bitmap) {
		if (bitmap == null) {
			return;
		}
		String path = getStorageDirectory();
		File folderFile = new File(path);
		if (!folderFile.exists()) {
			folderFile.mkdir();
		}
		File file = new File(path + File.separator + fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i(TAG, "saveBitmap() has FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(TAG, "saveBitmap() has IOException");
		}
	}

	/**
	 * 保存用户需要的图片 返回保存的路径
	 * 
	 * @param bitmap
	 */
	public String saveImage(String fileName, Bitmap bitmap) {
		fileName = fileName.replaceAll("[^\\w]", "");
		if (bitmap == null) {
			return null;
		}
		String result = null;
		String path = getImageDirectory();
		File folderFile = new File(path);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		File file = new File(path + File.separator + fileName + ".jpg");
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			result = file.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i(TAG, "saveImage() has FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(TAG, "saveImage() has IOException");
		}
		return result;
	}

	/**
	 * 从手机或者sd卡获取Bitmap
	 * 
	 * @param fileName
	 * @return
	 */
	public Bitmap getBitmap(String fileName) {
		return BitmapFactory.decodeFile(getStorageDirectory() + File.separator
				+ fileName);
	}

	/**
	 * 从手机或者sd卡获取Bitmap的路径
	 * 
	 * @param fileName
	 * @return
	 */
	public String getBitmapPath(String fileName) {
		return getStorageDirectory() + File.separator + fileName;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExists(String fileName) {
		return new File(getStorageDirectory() + File.separator + fileName)
				.exists();
	}

	/**
	 * 获取文件的大小
	 * 
	 * @param fileName
	 * @return
	 */
	public long getFileSize(String fileName) {
		return new File(getStorageDirectory() + File.separator + fileName)
				.length();
	}

	/**
	 * 返回的是创建的文件,若输入的文件夹路径不对，则返回null
	 * 
	 * @param dirPath
	 * @param name
	 * @return
	 */
	public File createFile(String dirPath, String name) {
		File files = new File(dirPath);
		if (!files.exists()) {
			Log.i("TAG", "创建文件时，输入的文件夹路径不存在！");
			return null;
		}
		File file = new File(files, name);
		try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param filesName
	 * @return 返回的是创建的文件夹的地址
	 */
	public String createFiles(String filesName) {
		File files = new File(getCacheDirectory() + File.separator + filesName);
		if (!files.exists()) {
			files.mkdirs();
		}
		return files.getPath();
	}

	/**
	 * 删除SD卡或者手机的缓存图片和目录
	 */
	public void deleteFile() {
		File dirFile = new File(getStorageDirectory());
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}

		dirFile.delete();

	}
}
