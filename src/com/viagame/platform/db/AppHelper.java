package com.viagame.platform.db;

import java.io.File;
import java.io.FileOutputStream;

import com.viagame.platform.bean.Game;
import com.viagame.platform.util.DrawableUtil;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

/**
 * ���ݿ���ɾ�Ĳ�Ĳٿ���
 * 
 * 
 * @author Steven.xu
 *
 */
public class AppHelper {

	public final DBHelper dbh ;
	private Context context;

	public AppHelper(Context context)
	{
		dbh  = DBHelper.getDBHelper(context);
		this.context = context;
	}
    /**
     * �����ݿ����һ��Ӧ�õ���Ϣ�����ҽ�Ӧ�õ�ͼ��볡��sdcard�ϡ������Ӧ���Ѿ���������������ɾ����¼�ٲ���
     * 
     * @param Ӧ�õİ�
     * @return �����ɹ�����true,ʧ�ܷ���false
     */
	@SuppressLint("SdCardPath")
	synchronized public boolean Insert(Game game)//String name, String exists, String control, String url)
	{
		PackageManager pm = context.getPackageManager();
		Bitmap icon = null;
		try {
			icon = DrawableUtil.drawableToBitmap(pm.getApplicationIcon(game.getName()));
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		File icon_file = new File("/mnt/sdcard/viagame/app_icon/"+game.getName()+".icon");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(icon_file);
			icon.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		SQLiteDatabase db = dbh.getReadableDatabase();
		db.delete(DBHelper.TABLE, "_name=?", new String[] { game.getName() });
		ContentValues cv = new ContentValues();
		cv.put("_name", game.getName());
		cv.put("_lable", game.getLable());
		cv.put("_description", game.getDesc());
		cv.put("_control", game.getControll());
		cv.put("_url", game.getUrl());
		cv.put("_exists", game.getExits());
		try {
			if(db.insert(DBHelper.TABLE, "", cv)> -1)
				return true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
    /**
     * �����ݿ���ɾ��һ��Ӧ�á�
     * 
     * @param Ӧ�õİ���
     * @return �����ɹ�����true,ʧ�ܷ���false
     */
	synchronized public boolean delete(String name)
	{
		SQLiteDatabase db = dbh.getReadableDatabase();
		File file = new File("mnt/sdcard/viagame/app_icon/"+name+".icon");
		if(file.exists())
			file.delete();
		if(db.delete(DBHelper.TABLE, "_name=?", new String[] { name }) >0)
			return false;
		return true;
	}
    /**
     * �����ݿ��в�ѯָ��Ӧ��
     * 
     * @param Ӧ�õİ���
     * @return ���ݿ��cursor
     */
	synchronized public Cursor Qurey(String selection, String args[])
	{
		//String arg = startdate+" and "+enddate;
		if((args == null) || (args.length == 0))
			selection = null;
		if(selection == null || selection.equals(""))
			args = null;
		
		SQLiteDatabase db = dbh.getReadableDatabase();
		
		Cursor cursor = null;
		try {
			cursor = db.query(DBHelper.TABLE, null, selection,
					args, null, null, "_lable");
			if(cursor.moveToFirst())
			{
				System.out.println("cuisor has content");
			}
			else
			{
				System.out.println("cuisor has none");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return cursor;
	}
}
