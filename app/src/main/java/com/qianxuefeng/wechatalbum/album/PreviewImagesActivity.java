package com.qianxuefeng.wechatalbum.album;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qianxuefeng.wechatalbum.R;

import java.util.ArrayList;


/**
 * @author: Shwan
 * @since: 2015-11-12
 * Description: 图片预览
 */
public class PreviewImagesActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener{
    private ViewPager viewPager;

    private MyViewPagerAdapter adapter;

    /** 当前选中的图片 */
    private int currentPic=0;

    /** 所有被选中的图片 */
    public final static String EXTRA_ALL_PICK_DATA = "extra_pick_data";
    /** 当前被选中的照片 */
    public final static String EXTRA_CURRENT_PIC = "extra_current_pic";
    /** 当前被选中的照片 */
    public final static String EXTRA_TYPE = "extra_type";
    /** 预览照片 **/
    public final static int PREVIEW_IMG = 5101;
    
    private static ArrayList<PickImg> pickList;
    
    private Activity activity;
	protected ImageView ivBack,ivRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_preview_images);
        activity = this;
        initFindView();
        initData();
    }

	protected void initFindView() {
        viewPager = (ViewPager) findViewById(R.id.vp_content);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		ivRight = (ImageView) findViewById(R.id.ivRight);
        ivRight.setOnClickListener(this);
    }

	protected void initData() {
        currentPic = getIntent().getIntExtra(EXTRA_CURRENT_PIC, 0);
        int extraType = getIntent().getIntExtra(EXTRA_TYPE, 0);
        pickList = Bitmp.getList(extraType);

//        tvTitle.setText((currentPic + 1) + "/" + Bitmp.pickList.size());

        adapter = new MyViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPic);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPic = position;
//        tvTitle.setText((currentPic + 1) + "/" + Bitmp.pickList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId())
		{
			case R.id.ivBack:
				finish();
				break;
			case R.id.ivRight:
				//删除照片
				pickList.remove(currentPic);
				if(pickList.size() == 0){
					finish();
				}
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
		}
    }

    private class MyViewPagerAdapter extends PagerAdapter {

    	 private int mChildCount = 0;
    	 
         @Override
         public void notifyDataSetChanged() {         
               mChildCount = getCount();
               super.notifyDataSetChanged();
         }
     
         @Override
         public int getItemPosition(Object object)   {
               if ( mChildCount > 0) {
               mChildCount --;
               return POSITION_NONE;
               }
               return super.getItemPosition(object);
         }
    	
        @Override
        public int getCount() {
            return pickList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(PreviewImagesActivity.this).inflate(R.layout.widget_album_zoom_iamge, null);
            final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);

            AlbumBitmapCacheHelper.getInstance().addPathToShowlist(pickList.get(position).path);
            zoomImageView.setTag(pickList.get(position).path);
            Bitmap bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(activity, pickList.get(position).path, 0, 0, new AlbumBitmapCacheHelper.ILoadImageCallback() {
                @Override
                public void onLoadImageCallBack(Bitmap bitmap, String path, Object... objects) {
                    ZoomImageView view = ((ZoomImageView)viewPager.findViewWithTag(path));
                    if (view != null && bitmap != null)
                        ((ZoomImageView)viewPager.findViewWithTag(path)).setSourceImageBitmap(bitmap, PreviewImagesActivity.this);
                }
            }, position);
            if (bitmap != null){
                zoomImageView.setSourceImageBitmap(bitmap, PreviewImagesActivity.this);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
