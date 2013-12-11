package com.viagame.platform;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class HolderView  implements OnFocusChangeListener{
	
	private ImageView title;
	private ImageView main;
	private ImageView bg;
	private FrameLayout hold;
	Context context;
	
	public HolderView(Context context){
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		Activity act = (Activity) context;
		@SuppressWarnings("deprecation")
		int pHeight = act.getWindowManager().getDefaultDisplay().getHeight();
		hold = (FrameLayout) inflater.inflate(R.layout.holdview, null);
		title = (ImageView) hold.findViewById(R.id.title);
		bg = (ImageView) hold.findViewById(R.id.bg);
		main = (ImageView)hold.findViewById(R.id.main);
		View v = hold.findViewById(R.id.t);
		
		FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int) (pHeight /2.24));  
		v.setLayoutParams(params1);
		FrameLayout.LayoutParams params2 = (android.widget.FrameLayout.LayoutParams) bg.getLayoutParams();
		params2.height = (int) (pHeight /2.24);
		params2.width = 236 * pHeight / 720;
		bg.setLayoutParams(params2);
		
		RelativeLayout.LayoutParams param3 = (android.widget.RelativeLayout.LayoutParams) main.getLayoutParams();
		param3.width = 264 * pHeight / 720;
		main.setLayoutParams(param3);
		
		RelativeLayout.LayoutParams param4 = (android.widget.RelativeLayout.LayoutParams) title.getLayoutParams();
		param4.width = 149 * pHeight / 720;
		main.setLayoutParams(param3);
		
	}
	public FrameLayout getHoidView()
	{
		return hold;
	}
	public  void setTitle(int res)
	{
		title.setImageResource(res);
	}
	public void setMain(int res)
	{
		main.setImageResource(res);
	}
	public void setBg(int res)
	{
		bg.setImageResource(res);
	}
	public void setTag(int tag)
	{
		hold.setTag(tag);
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(hasFocus)
		{
			
		}
	}
	
	
}
