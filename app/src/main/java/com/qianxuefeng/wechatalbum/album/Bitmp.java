package com.qianxuefeng.wechatalbum.album;

import java.util.ArrayList;
import java.util.List;

public class Bitmp {

	/**
	 * 实名认证选中的图片路径
	 */
	public static ArrayList<PickImg> imgList=new ArrayList<PickImg>();
	
	/**
	 * 添加动态发布选中的图片
	 * @return
	 */
	public static void realAdd(String filePath){
		imgList.add(new PickImg(filePath));
	}
	
	/**
	 * 添加动态发布选中的图片
	 * @return
	 */
	public static void realAddAll(List<String> list){
		for (String path:list) {
			imgList.add(new PickImg(path));
		}
	}
	
	/**
	 * 获取实名认证选中的图片
	 * @return
	 */
	public static PickImg realGet(int position){
		return imgList.get(position);
	}
	
	/**
	 * 动态发布图片选中数量
	 * @return
	 */
	public static int realSize(){
		return imgList.size();
	}
	
	/**
	 * 清空动态发布选中的图片
	 */
	public static void realClear(){
		imgList.clear();
	}
	
	public static ArrayList<PickImg> getList() {
		return imgList;
	}

}
