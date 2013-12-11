package com.viagame.platform;

import com.viagame.platform.bean.Game;

import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector.OnGestureListener;

import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ViewContainer extends ViewGroup implements OnClickListener, OnFocusChangeListener, 
OnTouchListener, OnGestureListener{
	
	final static int cell_num = 3;
	private Context context;
	private ImageButton left;
	private ImageButton right;
	private ImageView pos;
	private int currentIndex = 0;
	
	GestureDetector gd;
	
	public void setWidget(ImageButton left, ImageButton right, ImageView pos)
	{
		this.left = left;
		this.right = right;
		this.pos = pos;
		this.left.setOnClickListener(this);
		this.right.setOnClickListener(this);
	}
	
	public void setContext(Context context) {
		this.context = context;
		gd = new GestureDetector(context, this);
		this.setOnTouchListener(this);
	}

	public ViewContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		context = this.context;
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childNum = this.getChildCount();
		int cellWidth = (r-l)/3;
		
		for(int i = 0 ; i < childNum; i++)
		{
				View s = this.getChildAt(i);
				s.setVisibility(View.GONE);
		}
		for(int i = 0; i < (3 > childNum ? 3:childNum); i++)
		{	
			int VisbleIndex = currentIndex + i;
			View child;
			if(VisbleIndex < childNum)
				child = this.getChildAt(VisbleIndex);
			else
				child = this.getChildAt((VisbleIndex-childNum)%cell_num);
			
			child.setVisibility(View.VISIBLE);  
            child.measure(r - l, b - t);  
        	int padding = (cellWidth - this.getChildAt(0).getMeasuredWidth())/2;
            child.layout(padding + i * cellWidth, 0, child.getMeasuredWidth() +padding + i * cellWidth, child  
                    .getMeasuredHeight()); 
            child.setOnClickListener(this);
            child.setOnFocusChangeListener(this);
            child.setOnTouchListener(this);
            Log.d("test2", child.getTag()+"");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent in = new Intent(context,GameCenter.class);
		if(v.getId() == R.id.main_left)
		{	
			currentIndex--;
			if(currentIndex < 0)
				currentIndex = this.getChildCount() - 1;
			pos.setImageResource(R.drawable.main_pos_1 + currentIndex);
			this.requestLayout();
			return ;
		}
		if(v.getId() == R.id.main_right)
		{
			currentIndex++;
			if(currentIndex == this.getChildCount())
				currentIndex = 0;
			pos.setImageResource(R.drawable.main_pos_1 + currentIndex);
			this.requestLayout();
			return ;
		}
		Animation anima = AnimationUtils.loadAnimation(context, R.anim.shake);
		v.findViewById(R.id.main).startAnimation(anima);
		switch((Integer)v.getTag())
		{
		case GameCenter.C1_SELECT:
			in.putExtra("controll", Game.CONTROLL_C1); 
			break;
		case GameCenter.F2_SELECT:
			in.putExtra("controll", Game.CONTROLL_F2); 
			break;
		case GameCenter.E1_SELECT:
			in.putExtra("controll", Game.CONTROLL_E1); 
			break;
		case  GameCenter.K1_SELECT:
			in.putExtra("controll", Game.CONTROLL_K1); 
			break;
		}
	
		
		context.startActivity(in);
		((MainActivity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		
		if(hasFocus)
		{
			Animation anima = AnimationUtils.loadAnimation(context, R.anim.shake);
			View view = v.findViewById(R.id.main);
			view.startAnimation(anima);
			v.setBackgroundColor(getResources().getColor(android.R.color.secondary_text_dark));
		}
		else
		{
			v.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		gd.onTouchEvent(event);
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(velocityX < 0)
		{
			currentIndex++;
			if(currentIndex == this.getChildCount())
				currentIndex = 0;
			pos.setImageResource(R.drawable.main_pos_1 + currentIndex);
			this.requestLayout();
			return false;
		}
		if(velocityX > 0)
		{
			currentIndex--;
			if(currentIndex < 0)
				currentIndex = this.getChildCount() - 1;
			pos.setImageResource(R.drawable.main_pos_1 + currentIndex);
			this.requestLayout();
			return false;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}



}
