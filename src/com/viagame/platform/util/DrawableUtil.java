package com.viagame.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;



public class DrawableUtil {

	@SuppressWarnings("deprecation")
	public static Drawable zoomDrawable(Drawable drawable, int w, int h)  
	{  
		int width = drawable.getIntrinsicWidth();  
		int height= drawable.getIntrinsicHeight();  
		Bitmap oldbmp = drawableToBitmap(drawable); // drawableת����bitmap  
		Matrix matrix = new Matrix();   // ��������ͼƬ�õ�Matrix����  
		float scaleWidth = ((float)w / width);   // �������ű���  
		float scaleHeight = ((float)h / height);  
		matrix.postScale(scaleWidth, scaleHeight);         // �������ű���  
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // �����µ�bitmap���������Ƕ�ԭbitmap�����ź��ͼ  
		return new BitmapDrawable(newbmp);       // ��bitmapת����drawable������  
	}  
	
	@SuppressWarnings("deprecation")
	public static Drawable zoomDrawable(Bitmap bitmap, int w, int h)  
	{  
		int width = bitmap.getWidth();  
		int height= bitmap.getHeight();  
		//Bitmap oldbmp = drawableToBitmap(drawable); // drawableת����bitmap  
		Matrix matrix = new Matrix();   // ��������ͼƬ�õ�Matrix����  
		float scaleWidth = ((float)w / width);   // �������ű���  
		float scaleHeight = ((float)h / height);  
		matrix.postScale(scaleWidth, scaleHeight);         // �������ű���  
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);       // �����µ�bitmap���������Ƕ�ԭbitmap�����ź��ͼ  
		return new BitmapDrawable(newbmp);       // ��bitmapת����drawable������  
	}  
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h)  
	{  
		try{
		int width = bitmap.getWidth();  
		int height= bitmap.getHeight();  
		Log.d("test", "heigh = "+height);
		//Bitmap oldbmp = drawableToBitmap(drawable); // drawableת����bitmap  
		Matrix matrix = new Matrix();   // ��������ͼƬ�õ�Matrix����  
		float scaleWidth = ((float)w / width);   // �������ű���  
		float scaleHeight = ((float)h / height);  
		matrix.postScale(scaleWidth, scaleHeight);         // �������ű���  
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);       // �����µ�bitmap���������Ƕ�ԭbitmap�����ź��ͼ  
		return newbmp;       // ��bitmapת����drawable������  n e
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return bitmap;
		}
	}  
	public static Bitmap drawableToBitmap(Drawable drawable) // drawable ת����bitmap  
	{  
		int width = drawable.getIntrinsicWidth();   // ȡdrawable�ĳ���  
		int height = drawable.getIntrinsicHeight();  
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         // ȡdrawable����ɫ��ʽ  
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // ������Ӧbitmap  
		Canvas canvas = new Canvas(bitmap);         // ������Ӧbitmap�Ļ���  
		drawable.setBounds(0, 0, width, height);  
		drawable.draw(canvas);      // ��drawable���ݻ���������  
		return bitmap;  
	}  
	@SuppressLint("SdCardPath")
	public static void saveMyBitmap(Bitmap bitmap, String bitName) throws IOException {
        File f = new File(bitName);
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
                fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
                fOut.flush();
        } catch (IOException e) {
                e.printStackTrace();
        }
        try {
                fOut.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
	public static Bitmap getBitmap(Context context, String path)
	{
		BitmapFactory.Options options=new BitmapFactory.Options(); 
		options.inJustDecodeBounds = false; 
		options.inSampleSize = 1;   
		try {
			InputStream is = new FileInputStream(new File(path));
			return BitmapFactory.decodeStream(is,null,options);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
