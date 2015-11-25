package com.qianxuefeng.wechatalbum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.qianxuefeng.wechatalbum.album.AlbumBitmapCacheHelper;
import com.qianxuefeng.wechatalbum.album.Bitmp;
import com.qianxuefeng.wechatalbum.album.FileUtils;
import com.qianxuefeng.wechatalbum.album.NoScrollGridView;
import com.qianxuefeng.wechatalbum.album.PickOrTakeImageActivity;
import com.qianxuefeng.wechatalbum.album.PreviewImagesActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 申请实名认证页面
 * 
 * @author DAI 2015.8.9
 * 
 */
public class MainActivity extends Activity
{
	private PicAdapter realAdapter;
	private NoScrollGridView noScrollgridview;
	private List<Bitmap> imgBmpList = new ArrayList<>(); // 所选照片的集合

	/**
	 * 最多选择图片张数
	 */
	private final static int MAX_PICS = 5;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;

		noScrollgridview = (NoScrollGridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		realAdapter = new PicAdapter(this);
		noScrollgridview.setAdapter(realAdapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				if (imgBmpList.get(position) == null)
				{
					Intent intent = new Intent(MainActivity.this, PickOrTakeImageActivity.class);
					intent.putExtra(PickOrTakeImageActivity.EXTRA_NUMS, MAX_PICS);
					startActivityForResult(intent, PickOrTakeImageActivity.PICK_IMAGE);
				} else
				{
					Intent intent = new Intent(MainActivity.this, PreviewImagesActivity.class);
					intent.putExtra(PreviewImagesActivity.EXTRA_CURRENT_PIC, position);
					intent.putExtra(PreviewImagesActivity.EXTRA_TYPE, Bitmp.REAL_TYPE);
					startActivityForResult(intent, PreviewImagesActivity.PREVIEW_IMG);
				}
			}
		});
		imgBmpList.add(null);
		realAdapter.update(imgBmpList);
	}


	@Override
	protected void onDestroy()
	{
		Bitmp.realClear();
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			switch (requestCode)
			{
				case PickOrTakeImageActivity.PICK_IMAGE:
					Bitmp.realAddAll(data.getStringArrayListExtra("data"));
					refreshPickImg();
					break;
				default:
					break;
			}
		} else {
			switch (requestCode)
			{
				case PreviewImagesActivity.PREVIEW_IMG:
					refreshPickImg();
					break;
				default:
					break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void refreshPickImg() throws OutOfMemoryError {
		imgBmpList.clear();
		Bitmap defaultImg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_feed);
		int j ;
		for (j = 0; j < Bitmp.realSize(); j++) {
			if(Bitmp.realGet(j).img != null){
				imgBmpList.add(Bitmp.realGet(j).img);
			}else {
				imgBmpList.add(defaultImg);
			    AlbumBitmapCacheHelper.getInstance().decodeBitmapFromPath( this, Bitmp.realGet(j).path,  new AlbumBitmapCacheHelper.ILoadImageCallback() {
			        @Override
			        public void onLoadImageCallBack(Bitmap bitmap, String filePath, Object... objects) {
			        	int index = Integer.parseInt(String.valueOf(objects[0]));
			        	Bitmp.realGet(index).img=bitmap;
			        	imgBmpList.set(index, bitmap);
						realAdapter.update(imgBmpList);
						String fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));//这个是获取后缀名
			        	FileUtils.saveRealBitmap(bitmap, fileName);
			        }
			    }, j);
			}

		}
		if(j<MAX_PICS){
			imgBmpList.add(null);
		}
		
		realAdapter.update(imgBmpList);
	}

}
