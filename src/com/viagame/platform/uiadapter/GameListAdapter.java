package com.viagame.platform.uiadapter;


import com.viagame.platform.GameCenter;
import com.viagame.platform.HorizontalListView;
import com.viagame.platform.R;
import com.viagame.platform.bean.Game;
import com.viagame.platform.db.AppHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GameListAdapter extends BaseAdapter implements OnFocusChangeListener, OnItemClickListener{

	public final static int RESERVE = 3;
	private LayoutInflater inflater;
	private Context context;
	private HorizontalListView list;
	private TextView current_game;
	public Cursor cursor;
	public String controll;
	public boolean exits;
	public boolean scrolling = false;
	public String cgamepkg;
	AppHelper aph;
	
	
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	public void setAppHelp(AppHelper aph)
	{
		this.aph = aph;
	}
	public void setfilterCursor(String controll, boolean exites, String desc)
	{
		
		String selection = "_control like ? and _exists=? and _lable like?";
		String args[] = 
		{
			controll,
			""+exites,
			"%%"+desc+"%%"
		};
		if(cursor != null && !cursor.isClosed())
			cursor.close();
		cursor =aph.Qurey(selection, args);
		list.initView();
		this.notifyDataSetChanged();
	}
	public void setfilterCursor(String controll, boolean exites, boolean reDraw)
	{
		String selection = "_control like ? and _exists=?";
		String args[] = 
		{
			controll,
			""+exites
		};
		if(cursor != null && !cursor.isClosed())
			cursor.close();
		cursor =aph.Qurey(selection, args);
		if(reDraw)
			list.initView();
		this.notifyDataSetChanged();
		
	}
	public void setfilterCursor(String controll, boolean exites)
	{
		setfilterCursor(controll, exites, true);
	}
	public GameListAdapter(Context context, HorizontalListView list, TextView current_game)
	{
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.context = context;
		this.current_game = current_game;
		list.setOnItemClickListener(this);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(cursor.getCount() == 0)
			return 0;
		return (cursor.getCount()+ 2 * RESERVE);
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if(arg0 > (RESERVE -1) || arg0 < this.getCount() - RESERVE)
			return cursor.move(arg0 - RESERVE);
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("SdCardPath")
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.flipper_layout, null);  
		TextView textView = (TextView) convertView;//.findViewById(R.id.iv_view);  
		int size = list.getWidth() / 11;
		int padding = list.getWidth()/36;
		int paddingVec = (list.getHeight() - size - size/2) /2;
		if(position < RESERVE  || position > (cursor.getCount() + RESERVE - 1))
		{
			Bitmap bitmap= this.createReflectedImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.block)); //.decodeFile("/mnt/sdcard/viagame/app_icon/"+cursor.getString(cursor.getColumnIndex("_name"))+".icon"));
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(bitmap);
			drawable.setBounds(0, 0, size, size + size/2);
			textView.setCompoundDrawables(null, drawable, null, null);
			textView.setFocusable(false);
			textView.setVisibility(View.INVISIBLE);
	//		textView.setLayoutParams(new LayoutParams(size, size + size/2));
			textView.setPadding(padding, padding, padding, padding);
			return textView;
		}
		cursor.moveToPosition(position - RESERVE);
		Bitmap bitmap= this.createReflectedImage(BitmapFactory.decodeFile("/mnt/sdcard/viagame/app_icon/"+cursor.getString(cursor.getColumnIndex("_name"))+".icon"));
		@SuppressWarnings("deprecation")
		Drawable drawable = new BitmapDrawable(bitmap);
		drawable.setBounds(0, 0, size, size + size/2);
		textView.setCompoundDrawables(null, drawable, null, null);
		textView.setText("test");
		textView.setFocusable(true);
		textView.setOnFocusChangeListener(this);
		textView.setTag(position);
		textView.setFocusable(true);
		textView.setPadding(padding, paddingVec, padding, paddingVec);
		return textView;
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		TextView  text = (TextView)v;
		if(hasFocus == true)
		{
			int pos = (Integer) text.getTag();
			text.setFocusable(true);
			text.setOnFocusChangeListener(this);
			list.setSelection(pos);
			cursor.moveToPosition(pos - RESERVE);
			current_game.setText(cursor.getString(cursor.getColumnIndex("_lable")));
			cgamepkg = cursor.getString(cursor.getColumnIndex("_name"));
			Animation tolarge = AnimationUtils.loadAnimation(context, R.anim.icon_to_large);	
			tolarge.setFillAfter(true);
			tolarge.setFillBefore(false);
			text.startAnimation(tolarge);

		}
		else
		{
		//	int pos = (Integer) text.getTag();
			text.setFocusable(true);
			text.setOnFocusChangeListener(this);
			current_game.setText("");
			cgamepkg=null;
			Animation tonornal = AnimationUtils.loadAnimation(context, R.anim.icon_to_nomal);
			//text.setText("");
			tonornal.setFillAfter(true);
			tonornal.setFillBefore(true);
			text.startAnimation(tonornal);
		}
	}

	public  Bitmap createReflectedImage(Bitmap originalImage)  
	{  
		int width = originalImage.getWidth();  
		int height = originalImage.getHeight();  
		Matrix matrix = new Matrix();  
		// 实现图片翻转90度  
		matrix.preScale(1, -1);  
		// 创建倒影图片（是原始图片的一半大小）  
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);  
		// 创建总图片（原图片 + 倒影图片）  
		Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);  
		// 创建画布  
		Canvas canvas = new Canvas(finalReflection);  
		canvas.drawBitmap(originalImage, 0, 0, null);  
		//把倒影图片画到画布上  
		canvas.drawBitmap(reflectionImage, 0, height + 1, null);  
		Paint shaderPaint = new Paint();  
		//创建线性渐变LinearGradient对象  
		LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, finalReflection.getHeight() + 1, 0x70ffffff,  
				0x00ffffff, TileMode.MIRROR);  
		shaderPaint.setShader(shader);  
		shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));  
		//画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。  
		canvas.drawRect(0, height + 1, width, finalReflection.getHeight(), shaderPaint);  
		return finalReflection;  
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(arg1.getVisibility() != View.VISIBLE)
			return ;
		cursor.moveToPosition(arg2 - 3);
		Game game = new Game(cursor);
		PackageManager pm = context.getPackageManager();
		if(game.getExits().equals("true"))
		{
			Intent in = pm.getLaunchIntentForPackage(game.getName());
			context.startActivity(in);
			((GameCenter)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

		}
		else
		{
			Uri uri;
			if(game.getUrl()==null || game.getUrl().equals(""))
			{	
				uri = Uri.parse("market://details?id="+game.getName());
			}
			else
				uri	= Uri.parse(game.getUrl());
			Intent it = new Intent(Intent.ACTION_VIEW, uri);   
			context.startActivity(it);  
			((GameCenter)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

		}
	}


}
