package com.viagame.platform.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ���ݿ�����İ�����
 * 
 * @author Steven.xu
 *
 */

public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper dbh= null;
	/**
	 *  ���ݿ����
	 */
	public final static String TABLE = "_via_game";

	private DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE "+TABLE+" (" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				"_name VARCHAR," +
				"_exists VARCHAR,"+
				"_lable VARCHAR," +
				"_control VARCHAR," +
				"_url VARCHAR," +
				"_description VARCHAR)";		
		arg0.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		String sql1 =  "drop table "+TABLE;
		arg0.execSQL(sql1);
		String sql = "CREATE TABLE "+TABLE+" (" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				"_name VARCHAR," +
				"_exists VARCHAR,"+
				"_lable VARCHAR," +
				"_control VARCHAR," +
				"_url VARCHAR," +
				"_description VARCHAR)";

		arg0.execSQL(sql);
	}

	public static DBHelper getDBHelper(Context context)
	{	
		if(dbh == null)
			dbh = new  DBHelper(context, TABLE, null, 1);
		return dbh;
	}

}
