package com.qianxuefeng.wechatalbum.album;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.qianxuefeng.wechatalbum.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtils
{
	
	private static String ROOT = FileUtil.getRootPath();

	/**
	 * 动态发布临时图片保存路径
	 */
	public final static String FEED_PATH = ROOT + "/guqu/feed/";
	/**
	 * 实名认证临时图片保存路径
	 */
	public final static String REAL_PATH = ROOT + "/guqu/real/";

	/**
	 * 保存动态发布的临时图片
	 * @param bm
	 * @param picName
	 */
	public static void saveFeedBitmap(Bitmap bm, String picName){
		saveBitmap(FEED_PATH, bm, picName);
	}
	
	/**
	 * 保存实名认证的临时图片
	 * @param bm
	 * @param picName
	 */
	public static void saveRealBitmap(Bitmap bm, String picName){
		saveBitmap(REAL_PATH, bm, picName);
	}
	
	/**
	 * 保存图片
	 * @param path
	 * @param bm
	 * @param picName
	 */
	public static void saveBitmap(String path, Bitmap bm, String picName)
	{
		try
		{
			if (!isFileExist(path))//判断文件夹是否存在
			{
				createDir(path);
			}
			File f = new File(path, picName + ".JPEG");
			if (f.exists())
			{
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);// 图片压缩 2015.6.30
			out.flush();
			out.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 创建目录
	 * @param dirName
	 * @return
	 * @throws IOException
	 */
	public static File createDir(String dirName) throws IOException
	{
		File dir = new File(dirName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			Log.e("createFile", "createDir:" + dir.getAbsolutePath());
			Log.e("createFile", "createDir:" + dir.mkdirs());
		}
		return dir;
	}

	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean isFileExist(String path)
	{
		File file = new File(path);
		file.isFile();
		return file.exists();
	}

	/**
	 * 删除实名认证保存的临时图片
	 * @param fileName
	 */
	public static void delRealFile(String fileName)
	{
		delFile(REAL_PATH, fileName);
	}
	
	/**
	 * 删除动态发布保存的临时图片
	 * @param fileName
	 */
	public static void delFeedFile(String fileName)
	{
		delFile(FEED_PATH, fileName);
	}
	
	/**
	 * 删除文件
	 * @param path 文件路径
	 * @param fileName 文件名称
	 */
	public static void delFile(String path, String fileName)
	{
		File file = new File(path + fileName);
		if (file.isFile())
		{
			file.delete();
		}
		file.exists();
	}

	/**
	 * 删除实名认证临时存储的图片
	 */
	public static void deleteRealDir()
	{
		deleteDir(REAL_PATH);
	}
	
	/**
	 * 删除动态发布临时存储的图片
	 */
	public static void deleteFeedDir()
	{
		deleteDir(FEED_PATH);
	}
	/**
	 * 删除选中后临时存储的图片
	 */
	public static void deleteDir(String sdpath)
	{
		File dir = new File(sdpath);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles())
		{
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(file.getAbsolutePath()); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path)
	{
		try
		{
			File f = new File(path);
			if (!f.exists())
			{
				return false;
			}
		} catch (Exception e)
		{

			return false;
		}
		return true;
	}

}
