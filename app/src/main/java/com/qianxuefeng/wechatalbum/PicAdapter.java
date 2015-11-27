package com.qianxuefeng.wechatalbum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择适配
 * 
 * @author Shwan 2015.8.9
 * 
 */
public class PicAdapter extends BaseAdapter
{
	private List<Bitmap> mList = new ArrayList<>();
	Context ctx;
	LayoutInflater inflater;

	public PicAdapter(Context context)
	{
		this.ctx = context;
		inflater = LayoutInflater.from(ctx);
	}

	public void update(List<Bitmap> list)
	{
		this.mList = list;
		// loading();
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return this.mList.size();
	}

	@Override
	public Bitmap getItem(int position)
	{
		return this.mList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		Bitmap item = getItem(position);
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_published_grida, null);
			holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		if (item == null)
		{
			holder.image.setScaleType(ScaleType.FIT_CENTER);
			holder.image.setImageBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.add_pic));
		} else
		{
			holder.image.setScaleType(ScaleType.CENTER_CROP);
			holder.image.setImageBitmap(item);
		}
		return convertView;
	}

	class ViewHolder
	{
		ImageView image;
	}

}