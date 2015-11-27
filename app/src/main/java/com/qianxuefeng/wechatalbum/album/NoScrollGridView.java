package com.qianxuefeng.wechatalbum.album;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class NoScrollGridView extends GridView
{
	public NoScrollGridView(Context context)
	{
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			return true; // 禁止GridView滑动
		}
		return super.dispatchTouchEvent(ev);
	}

}
