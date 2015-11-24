package com.qianxuefeng.wechatalbum.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Log;

import com.qianxuefeng.wechatalbum.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtils {
	
	/**
	 * 根据照片方向矫正图片位置
	 * @param bitmap
	 * @param path
	 * @return
	 */
	public static Bitmap correctBitmap(Bitmap bitmap, String path){
		int degree = getDegree(path);
		Log.e("图片矫正", "图片路径：" + path + "，旋转角度：" + degree);
		if(degree>0){
			bitmap=adjustPhotoRotation(bitmap, degree);
		}
		return bitmap;
	}
	
	/**
	 * 获取照片方向旋转角度
	 * @param filename
	 * @return
	 */
	public static int getDegree(String filename){
		int degree=0;
		try {
            //android读取图片EXIF信息
            ExifInterface exifInterface=new ExifInterface(filename);
            int orientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				default:
					break;
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return degree;
	}
	
	/**
	 * 旋转图片
	 * @param bm
	 * @param orientationDegree
	 * @return
	 */
	public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2,
				(float) bm.getHeight() / 2);
		float targetX, targetY;
		if (orientationDegree == 90) {
			targetX = bm.getHeight();
			targetY = 0;
		} else {
			targetX = bm.getHeight();
			targetY = bm.getWidth();
		}

		final float[] values = new float[9];
		m.getValues(values);

		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];

		m.postTranslate(targetX - x1, targetY - y1);

		Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(),
				bm.getConfig());
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		canvas.drawBitmap(bm, m, paint);

		return bm1;
	}
	
	/**
	 * 压缩图片
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap compressImage(Context ctx, String path)
	{
		Bitmap bitmap = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();
			int i = 0;
			while (true)
			{
				if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000))
				{
					in = new BufferedInputStream(new FileInputStream(new File(path)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}
	
			bitmap=ImageUtils.correctBitmap(bitmap, path);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("图片压缩失败", e.getMessage());
			bitmap= BitmapFactory.decodeResource(ctx.getResources(), R.drawable.default_feed);
		}
		return bitmap;
	}
}
