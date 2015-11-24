package com.qianxuefeng.wechatalbum.album;

import java.util.ArrayList;
import java.util.List;

public class Bitmp {
	
	public final static int FEED_TYPE = 1;
	public final static int REAL_TYPE = 2;
	
	/**
	 * 动态发布选中的图片路径
	 */
	private static ArrayList<PickImg> feedImgList=new ArrayList<PickImg>();
	
	/**
	 * 添加动态发布选中的图片
	 * @return
	 */
	public static void feedAdd(String filePath){
		feedImgList.add(new PickImg(filePath));
	}
	
	/**
	 * 添加动态发布选中的图片
	 * @return
	 */
	public static void feedAddAll(List<String> list){
		for (String path:list) {
			feedImgList.add(new PickImg(path));
		}
	}
	
	/**
	 * 获取动态发布选中的图片
	 * @return
	 */
	public static PickImg feedGet(int position){
		return feedImgList.get(position);
	}
	
	/**
	 * 动态发布图片选中数量
	 * @return
	 */
	public static int feedSize(){
		return feedImgList.size();
	}

	/**
	 * 清空动态发布选中的图片
	 */
	public static void feedClear(){
		feedImgList.clear();
		feedImgList.clear();
	}
	

	/**
	 * 实名认证选中的图片路径
	 */
	public static ArrayList<PickImg> realImgList=new ArrayList<PickImg>();
	
	/**
	 * 添加动态发布选中的图片
	 * @return
	 */
	public static void realAdd(String filePath){
		realImgList.add(new PickImg(filePath));
	}
	
	/**
	 * 添加动态发布选中的图片
	 * @return
	 */
	public static void realAddAll(List<String> list){
		for (String path:list) {
			realImgList.add(new PickImg(path));
		}
	}
	
	/**
	 * 获取实名认证选中的图片
	 * @return
	 */
	public static PickImg realGet(int position){
		return realImgList.get(position);
	}
	
	/**
	 * 动态发布图片选中数量
	 * @return
	 */
	public static int realSize(){
		return realImgList.size();
	}
	
	/**
	 * 清空动态发布选中的图片
	 */
	public static void realClear(){
		realImgList.clear();
		realImgList.clear();
	}
	
	public static ArrayList<PickImg> getList( int type) {
		if(type == FEED_TYPE){
			return feedImgList;
		}else if(type == REAL_TYPE){
			return realImgList;
		}else{
			return new ArrayList<PickImg>();
		}
	}

}
