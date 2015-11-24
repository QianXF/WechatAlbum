package com.qianxuefeng.wechatalbum.utils;

import android.os.Environment;

import java.io.File;

public class FileUtil {

	public final static String ALBUM_PATH = "/guqu/GuQu";
	
	/**
	 * 获取图片存储路径
	 * 没有则创建
	 */
	public static String getPicPath(){
		return createFilePath(ALBUM_PATH);
	}
	
	/**
	 * 创建存储路径
	 *  优先考虑SD卡
	 * @param path
	 * @return
	 */
	public static String createFilePath(String path) {
		String root = getRootPath();
		File file = new File(root+path);
		file.mkdirs();
		return file.getAbsolutePath();
	}

	/**
	 * 获取文件存储根目录
	 * 优先考虑SD卡
	 * @return
	 */
	public static String getRootPath() {
		String root;
		if(hasSDCard()){
			root = Environment.getExternalStorageDirectory().getAbsolutePath();//获取SD卡根目录
		}else{
			root = Environment.getRootDirectory().getAbsolutePath();//获取手机根目录
		}
		return root;
	}

	/**
	 * 判断是否存在SD卡
	 * @return
	 */
	public static boolean hasSDCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}
}
