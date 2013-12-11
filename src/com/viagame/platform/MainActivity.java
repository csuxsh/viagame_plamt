package com.viagame.platform;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnFocusChangeListener,OnClickListener{

	ImageView gamecenter_select;
	ImageView web_select;
	ImageView shop_select;
	ImageView cotouc_select;
	ImageButton gamecenter;
	ImageButton web;
	ImageButton shop;
	ImageButton cotouct;
	ImageButton left;
	ImageButton right;
	ImageView pos;
	ViewContainer views;
	FrameLayout main_title; 
	LinearLayout plist;
	
	int[][]  reses= {
			
			{R.drawable.main_c_bg,R.drawable.main_c,R.drawable.main_c_title,GameCenter.C1_SELECT},
			{R.drawable.main_f_bg,R.drawable.main_f,R.drawable.main_f_title,GameCenter.F2_SELECT},
			{R.drawable.main_k_bg,R.drawable.main_k,R.drawable.main_k_title,GameCenter.K1_SELECT},
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		@SuppressWarnings("deprecation")
		int height = this.getWindowManager().getDefaultDisplay().getHeight();
		@SuppressWarnings("deprecation")
		int width = this.getWindowManager().getDefaultDisplay().getWidth();
		gamecenter_select = (ImageView) this.findViewById(R.id.gamecenter_select);
		web_select = (ImageView) this.findViewById(R.id.web_select);
		shop_select = (ImageView) this.findViewById(R.id.shop_select);
		cotouc_select = (ImageView) this.findViewById(R.id.cotouct_us_select);
		gamecenter = (ImageButton) this.findViewById(R.id.gamecenter);
		web = (ImageButton) this.findViewById(R.id.web);
		shop = (ImageButton) this.findViewById(R.id.shop);
		cotouct = (ImageButton) this.findViewById(R.id.cotouct_us);
		views = (ViewContainer) this.findViewById(R.id.container);
		right = (ImageButton) this.findViewById(R.id.main_left);
		left = (ImageButton) this.findViewById(R.id.main_right);
		pos = (ImageView) this.findViewById(R.id.main_pos);
		main_title = (FrameLayout) this.findViewById(R.id.main_title);
		plist = (LinearLayout) this.findViewById(R.id.plist);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
		params.setMargins(width/71, (int) (height/12.3), width/71, 0);
		main_title.setLayoutParams(params);
		
		FrameLayout.LayoutParams params2 = (android.widget.FrameLayout.LayoutParams) plist.getLayoutParams();
		params2.setMargins(width/20, 0, width/20, (height/20));
		plist.setLayoutParams(params2);
		
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams((int) (width/1.25), (int) (height /2.24));  
		params3.width = width - width/20 * 2 - 2 * 41;
		views.setLayoutParams(params3);
		views.setContext(this);
		views.setWidget(left, right, pos);
		for(int i = 0; i < reses.length; i++)
		{
			HolderView hd = new HolderView(this);
			hd.setBg(reses[i][0]);
			hd.setMain(reses[i][1]);
			hd.setTitle(reses[i][2]);
			hd.setTag(reses[i][3]);
			views.addView(hd.getHoidView());
		}
		gamecenter.setOnFocusChangeListener(this);
		gamecenter.setOnClickListener(this);
		web.setOnFocusChangeListener(this);
		web.setOnClickListener(this);
		shop.setOnFocusChangeListener(this);
		shop.setOnClickListener(this);
		cotouct.setOnFocusChangeListener(this);
		cotouct.setOnClickListener(this);
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.gamecenter:
			if(hasFocus)
				gamecenter_select.setVisibility(View.VISIBLE);
			else
				gamecenter_select.setVisibility(View.INVISIBLE);
			break;
		case R.id.web:
			if(hasFocus)
				web_select.setVisibility(View.VISIBLE);
			else
				web_select.setVisibility(View.INVISIBLE);
			break;
		case R.id.shop:
			if(hasFocus)
				shop_select.setVisibility(View.VISIBLE);
			else
				shop_select.setVisibility(View.INVISIBLE);
			break;
		case R.id.cotouct_us:
			if(hasFocus)
				cotouc_select.setVisibility(View.VISIBLE);
			else
				cotouc_select.setVisibility(View.INVISIBLE);
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		v.requestFocus();
		v.requestFocusFromTouch();
		
		switch(v.getId())
		{
		case R.id.gamecenter:
			this.startActivity(new Intent(this,GameCenter.class));
			break;
		case R.id.cotouct_us:
			Uri uri = Uri.parse("http://www.via-play.com/support.asp?cid");  
			Intent it = new Intent(Intent.ACTION_VIEW, uri);   
			this.startActivity(it);
			break;
		case R.id.web:
			Uri uri2 = Uri.parse("http://www.via-play.com/");  
			Intent it2 = new Intent(Intent.ACTION_VIEW, uri2);   
			this.startActivity(it2);
			break;
		case R.id.shop:
			Uri uri3 = Uri.parse("http://viaplay.taobao.com/");  
			Intent it3 = new Intent(Intent.ACTION_VIEW, uri3);   
			this.startActivity(it3);
			break;
		}
		
	}

}
