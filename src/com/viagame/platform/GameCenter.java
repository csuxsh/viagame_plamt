package com.viagame.platform;


import java.io.File;
import java.util.List;

import uiadapter.GameListAdapter;
import uiadapter.PopAddAdapter;

import com.viagame.platform.bean.Game;
import com.viagame.platform.db.AppHelper;
import com.viagame.platform.db.DBHelper;
import com.viagame.platform.util.FileUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SdCardPath")
public class GameCenter extends Activity implements OnFocusChangeListener, OnClickListener
,OnTouchListener,OnEditorActionListener{

	final static int C1_SELECT = 0;
	final static int F2_SELECT = 1;
	final static int E1_SELECT = 2;
	final static int K1_SELECT = 3;

	static  String controll = Game.CONTROLL_C1;
	static boolean myGame = true;
	HorizontalListView gameview;
	public static AppHelper aph;
	TextView current_game;
	private ImageButton c1;
	private ImageView c1_select;
	private ImageButton f2;
	private ImageView f2_select;
	private ImageButton e1;
	private ImageView e1_select;
	private ImageButton k1;
	private ImageView k1_select;
	private ImageButton add_button;
	private ImageButton delete_button;
	private PopAddAdapter popAdapter;
	private Dialog adddialog;
	private GameListAdapter mAdapter;
	private ImageButton myGameB;
	private ImageButton moreGame;
	private ImageView scrollbar;
	private ImageView scrollbg;
	private EditText et;
	private ImageButton search_button;


	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.game_center);
		initData();
		controll = getIntent().getStringExtra("controll");
		if(controll == null || controll.equals(""))
			controll = Game.CONTROLL_C1;
		myGame = true;
		aph = new AppHelper(this);
		gameview = (HorizontalListView) this.findViewById(R.id.gamelist);
		current_game = (TextView) this.findViewById(R.id.current_game_title);
		mAdapter = new GameListAdapter(this, gameview, current_game);
		mAdapter.setAppHelp(aph);
		LayoutParams p = gameview.getLayoutParams();
		p.height = getWindowManager().getDefaultDisplay().getHeight() * 48 /72;
		gameview.setLayoutParams(p);
		FrameLayout.LayoutParams p2 = (android.widget.FrameLayout.LayoutParams) current_game.getLayoutParams();
		p2.setMargins(0, 270 * getWindowManager().getDefaultDisplay().getHeight() / 768, 0, 0);
		current_game.setLayoutParams(p2);
		checkApp();
		gameview.setAdapter(mAdapter);

		c1 = (ImageButton) this.findViewById(R.id.gamecenter_c);
		c1_select = (ImageView) this.findViewById(R.id.gamecenter_c_select);
		f2 = (ImageButton) this.findViewById(R.id.gamecenter_f);
		f2_select = (ImageView) this.findViewById(R.id.gamecenter_f_select);
		e1 = (ImageButton) this.findViewById(R.id.gamecenter_e);
		e1_select = (ImageView) this.findViewById(R.id.gamecenter_e_select);
		k1 = (ImageButton) this.findViewById(R.id.gamecenterk);
		k1_select = (ImageView) this.findViewById(R.id.gamecenter_k_select);
		add_button = (ImageButton) this.findViewById(R.id.add);
		myGameB = (ImageButton) this.findViewById(R.id.mygame);
		myGameB.setBackgroundResource(R.drawable.my_game_select);
		moreGame = (ImageButton) this.findViewById(R.id.moregame);
		scrollbar = (ImageView) this.findViewById(R.id.scrollbar);
		scrollbg = (ImageView) this.findViewById(R.id.scroll_bg);
		et = (EditText) this.findViewById(R.id.search_edit);
		delete_button = (ImageButton) this.findViewById(R.id.delete);
		search_button = (ImageButton) this.findViewById(R.id.gamecenter_search);
		et.setOnEditorActionListener(this);
		c1.setOnFocusChangeListener(this);
		f2.setOnFocusChangeListener(this);
		e1.setOnFocusChangeListener(this);
		k1.setOnFocusChangeListener(this);
		myGameB.setOnFocusChangeListener(this);
		moreGame.setOnFocusChangeListener(this);
		et.setOnFocusChangeListener(this);
		add_button.setOnClickListener(this);
		c1.setOnClickListener(this);
		f2.setOnClickListener(this);
		e1.setOnClickListener(this);
		k1.setOnClickListener(this);
		myGameB.setOnClickListener(this);
		moreGame.setOnClickListener(this);
		scrollbar.setOnTouchListener(this);
		search_button.setOnClickListener(this);
		delete_button.setOnClickListener(this);

		if(controll.equals(Game.CONTROLL_C1))
			c1_select.setVisibility(View.VISIBLE);
		else if(controll.equals(Game.CONTROLL_E1))
			e1_select.setVisibility(View.VISIBLE);
		else if(controll.equals(Game.CONTROLL_F2))
			f2_select.setVisibility(View.VISIBLE);
		else if(controll.equals(Game.CONTROLL_K1))
			k1_select.setVisibility(View.VISIBLE);
		
		
}
@Override
public void onPause()
{
	super.onPause();
	this.finish();
	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

}
private void checkApp()
{
	PackageManager pm = this.getPackageManager();
	List<PackageInfo> applist= pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES );
	mAdapter.cursor = aph.Qurey(null, null);
	while(!mAdapter.cursor.isAfterLast())
	{
		Game game = new Game(mAdapter.cursor);
		int i = 0;
		for(;i < applist.size(); i++)
		{
			PackageInfo rf = applist.get(i);
			if(game.getName().equals(rf.packageName))	
			{
				if(game.getExits().equals("false"))
				{
					game.setExits("true");
					aph.Insert(game);
				}
				break;
			}

		}
		if((i == applist.size()) && game.getExits().equals("true"))
		{
			game.setExits("false");
			aph.Insert(game);
		}
		mAdapter.cursor.moveToNext();
	}
	mAdapter.setfilterCursor(controll, myGame);

}
@SuppressLint("SdCardPath")
private void initData()
{
	SharedPreferences sp = this.getApplicationContext(). getSharedPreferences("init", Context.MODE_PRIVATE); 
	SharedPreferences.Editor  edit = sp.edit();
	int i = sp.getInt("boolean", 0);
	if(i == 0)
	{
		File file = new File("/mnt/sdcard/viagame/app_icon");
		file.mkdirs();
		if(CopyDatabase())
		{
			edit.putInt("boolean", 1);
			edit.commit();
			CopyMappings();
		}
		else
		{
			Toast.makeText(this, "Init failed", Toast.LENGTH_SHORT).show();
		}
	}
}
@SuppressLint("SdCardPath")
private void CopyMappings()
{
	SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/mnt/sdcard/viagame/_via_game", null);
	Cursor cursor= null;

	cursor = sqLiteDatabase.query("_via_game", null, null,
			null, null, null, "_description");
	cursor.moveToFirst();
	while(!cursor.isLast())
		//for(int i = 0; i < mappingFiles.length; i++)
	{
		String apkname = cursor.getString(cursor.getColumnIndex("_name"));
		//	FileUtil.movingContextFile(this,this.getFilesDir()+"/"+ apkname + ".keymap", apkname+ ".keymap") ;
		FileUtil.movingContextFile(this,"/mnt/sdcard/viagame/app_icon/"+ apkname + ".icon", apkname + ".icon.png");
		cursor.moveToNext();
	}
}
public boolean onKeyDown(int code,KeyEvent evnt)
{
	/*
		if(code == KeyEvent.KEYCODE_MEDIA_NEXT)
			Toast.makeText(this, "next key pressed", Toast.LENGTH_SHORT).show();
		if(code == KeyEvent.KEYCODE_MEDIA_PREVIOUS)
			Toast.makeText(this, "next key KEYCODE_MEDIA_PREVIOUS", Toast.LENGTH_SHORT).show();
		if(code == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
			Toast.makeText(this, "next key KEYCODE_MEDIA_PLAY_PAUSE", Toast.LENGTH_SHORT).show();
	 */
	if(code != KeyEvent.KEYCODE_BACK)
		gameview.mToushZoomMode = false;
	return super.onKeyDown( code, evnt);
}
@SuppressLint("SdCardPath")
private boolean CopyDatabase()
{
	if(!FileUtil.movingContextFile(this, "/mnt/sdcard/viagame/_via_game","_via_game"))
	{	
		Toast.makeText(this, "Copy databases failed", Toast.LENGTH_SHORT).show();
		return false;
	}
	String filename = "/mnt/sdcard/viagame/_via_game";

	SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(filename, null);
	Cursor cursor= null;

	cursor = sqLiteDatabase.query("_via_game", null, null,
			null, null, null, "_lable");
	cursor.moveToFirst();

	while(!cursor.isLast())
	{
		SQLiteDatabase db = DBHelper.getDBHelper(this).getReadableDatabase();
		try
		{
			db.delete(DBHelper.TABLE, "_name=?", new String[] { cursor.getString(cursor.getColumnIndex("_name")) });
		}
		catch(Exception e)
		{

		}
		ContentValues cv = new ContentValues();
		cv.put("_name", cursor.getString(cursor.getColumnIndex("_name")));
		cv.put("_description", cursor.getString(cursor.getColumnIndex("_description")));
		cv.put("_lable", cursor.getString(cursor.getColumnIndex("_lable")));
		cv.put("_url",  cursor.getString(cursor.getColumnIndex("url")));
		cv.put("_control", cursor.getString(cursor.getColumnIndex("_control")));
		cv.put("_exists", true);
		String hidonly = cursor.getString(cursor.getColumnIndex("_hidonly"));
		if(hidonly==null)
			hidonly = "false";
		cv.put("_hidonly", hidonly);
		try {
			if(db.insert(DBHelper.TABLE, "", cv) < 0)
			{	
				Toast.makeText(this, "Init databases failed", Toast.LENGTH_SHORT).show();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		cursor.moveToNext();
	}
	return true;
}
@Override
public void onFocusChange(View v, boolean hasFocus) {
	// TODO Auto-generated method stub
	switch(v.getId())
	{

	case R.id.gamecenter_c:
		if(hasFocus)
		{
			controll = Game.CONTROLL_C1;
			mAdapter.setfilterCursor(controll, myGame);
			c1_select.setVisibility(View.VISIBLE);
			f2_select.setVisibility(View.INVISIBLE);
			k1_select.setVisibility(View.INVISIBLE);
			e1_select.setVisibility(View.INVISIBLE);
		}
		break;
	case R.id.gamecenter_f:
		if(hasFocus)
		{	
			controll = Game.CONTROLL_F2;
			mAdapter.setfilterCursor(controll, myGame);
			c1_select.setVisibility(View.INVISIBLE);
			f2_select.setVisibility(View.VISIBLE);
			k1_select.setVisibility(View.INVISIBLE);
			e1_select.setVisibility(View.INVISIBLE);
		}
		break;
	case R.id.gamecenter_e:
		if(hasFocus)
		{
			controll = Game.CONTROLL_E1;
			mAdapter.setfilterCursor(controll, myGame);
			c1_select.setVisibility(View.INVISIBLE);
			f2_select.setVisibility(View.INVISIBLE);
			k1_select.setVisibility(View.INVISIBLE);
			e1_select.setVisibility(View.VISIBLE);
		}
		break;
	case R.id.gamecenterk:
		if(hasFocus)
		{
			controll = Game.CONTROLL_K1;
			mAdapter.setfilterCursor(controll, myGame);
			c1_select.setVisibility(View.INVISIBLE);
			f2_select.setVisibility(View.INVISIBLE);
			k1_select.setVisibility(View.VISIBLE);
			e1_select.setVisibility(View.INVISIBLE);
		}
		break;
	case R.id.mygame:
		if(hasFocus)
		{	
			myGame = true;
			mAdapter.setfilterCursor(controll, myGame);
			myGameB.setBackgroundResource(R.drawable.my_game_select);
			moreGame.setBackgroundResource(R.drawable.gamecenter_moregame);
			add_button.setVisibility(View.VISIBLE);
			delete_button.setVisibility(View.VISIBLE);
		}
		break;
	case R.id.moregame:
		if(hasFocus)
		{
			myGame = false;
			mAdapter.setfilterCursor(controll, myGame);
			moreGame.setBackgroundResource(R.drawable.more_game_select);
			myGameB.setBackgroundResource(R.drawable.gamecenter_mygame);
			add_button.setVisibility(View.GONE);
			delete_button.setVisibility(View.GONE);
		}
		break;
	case R.id.search_edit:
		if(hasFocus)
			gameview.mToushZoomMode = false;
		break;
	}

}
@SuppressWarnings("deprecation")
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch(v.getId())
	{
	case R.id.delete:
		if(mAdapter.cgamepkg!=null)
		{	
			aph.delete(mAdapter.cgamepkg);
			mAdapter.setfilterCursor(controll, myGame, false);
		}
		break;
	case R.id.gamecenter_search:
		searchGame();
		gameview.mToushZoomMode=true;
		break;
	case R.id.gamecenter_c:
	case R.id.gamecenter_e:
	case R.id.gamecenter_f:
	case R.id.gamecenterk:
	case R.id.mygame:
	case R.id.moregame:
		v.setFocusableInTouchMode(true);
		v.requestFocusFromTouch();
		break;
	case R.id.add_cancal:
		adddialog.dismiss();
		break;
	case R.id.add_confirm:
		AppHelper aph = GameCenter.aph;
		for(int i = 0; i < PopAddAdapter.isSelected.size(); i++)
		{
			if(PopAddAdapter.isSelected.get(i))
			{
				ResolveInfo rinfo = (ResolveInfo)((popAdapter.apps.get(i)).get("resolveInfo"));
				Game game = new Game();
				game.setName(rinfo.activityInfo.packageName);
				game.setExits("true");
				game.setControll(controll);
				game.setLable(rinfo.loadLabel(getPackageManager())+"");
				aph.Insert(game);
			}
		}
		mAdapter.setfilterCursor(controll, myGame);
		adddialog.dismiss();
		break;
	case R.id.add:
		View view = GameCenter.this.getLayoutInflater().inflate(R.layout.add_game, null);
		ListView lv = (ListView) view.findViewById(R.id.lv);
		Button comfirm = (Button) view.findViewById(R.id.add_confirm);
		Button cancel = (Button) view.findViewById(R.id.add_cancal);
		comfirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		popAdapter = new PopAddAdapter(GameCenter.this);
		lv.setAdapter(popAdapter);
		//	adddialog = new AlertDialog.Builder(JnsIMEGameListActivity.this,R.style.mydialog).setView(view).create();
		adddialog = new Dialog(GameCenter.this, R.style.mydialog);
		adddialog.setContentView(view);
		adddialog.setCancelable(true);
		WindowManager m = getWindowManager();    
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高     
		android.view.WindowManager.LayoutParams p = adddialog.getWindow().getAttributes();  //获取对话框当前的参数值     
		p.height = (int) (d.getHeight() * 0.8);   //高度设置为屏幕的1.0    
		p.width = (int) (d.getWidth() * 0.5);    //宽度设置为屏幕的0.8    

		adddialog.getWindow().setAttributes(p);
		adddialog.show();
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// ViewHolder vHollder = (ViewHolder) view.getTag();    
				//在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值。    
				CheckBox cbox = (CheckBox) view.findViewById(R.id.cb); 
				Log.v("check box","select at" + position);
				cbox.toggle();    
				PopAddAdapter.isSelected.put(position, cbox.isChecked());
			}

		});
		break;
	}
}
void scrollToPos(float pos)
{
	int index = 0;
	int count = mAdapter.getCount();
	Log.d("test", "count="+count);
	float totalLength = scrollbg.getWidth()-scrollbar.getWidth();
	if(count == 0)
		return;
	count = count - 6;
	index = (int) ((pos/totalLength) * count +3);
	Log.d("test", "index="+index);
	gameview.mToushZoomMode = false;
	gameview.setSelection(index);
}
@Override
public boolean onTouchEvent(MotionEvent e)
{/*
	Rect r = new Rect();
	scrollbg.getGlobalVisibleRect(r);
	if((e.getAction() == MotionEvent.ACTION_MOVE) && mAdapter.scrolling)
	{
		//	if((e.getX() > r.left && (e.getX() < (r.right) )))
		//	{
		if (r.right - e.getX() < scrollbar.getMeasuredWidth())
		{
			scrollbar.setX(r.right - scrollbar.getMeasuredWidth() - r.left);
			scrollToPos(r.right - scrollbar.getMeasuredWidth() - r.left);
			return false;
		}
		if (r.left - e.getX() > 0)									
		{
			scrollbar.setX(0);
			scrollToPos(0);
			return false;
		}
		scrollbar.setX(e.getX()-r.left );
		scrollToPos(e.getX()-r.left);
		//	}
	}
	if((e.getAction() == MotionEvent.ACTION_UP) && mAdapter.scrolling)
	{	
		//mAdapter.scrolling = false;
		//gameview.mToushZoomMode = true;
		gameview.requestFacous();
	}*/
	return false;
}
@Override
public boolean onTouch(View v, MotionEvent event) {
	// TODO Auto-generated method stub
	/*
	if(v.getId() == R.id.scrollbar)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{	
			mAdapter.scrolling = true;
		}
		/*
			if((event.getAction() == MotionEvent.ACTION_MOVE) && 
					(event.getX() > scrollbg.getLeft()&& (event.getX() < scrollbg.getRight())))
			{
				v.setX(event.getX());
			}
		 
	}*/
	return false;
}
@Override
public boolean onEditorAction(TextView arg0, int actionId, KeyEvent event) {
	// TODO Auto-generated method stub
	if (actionId == EditorInfo.IME_ACTION_SEARCH) {  
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);	
		searchGame();
		gameview.mToushZoomMode=true;
	}  
	return true;
}
private void searchGame()
{
	mAdapter.setfilterCursor(controll, myGame, et.getText().toString());
}

}
