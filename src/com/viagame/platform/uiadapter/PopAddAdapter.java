package com.viagame.platform.uiadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.viagame.platform.GameCenter;
import com.viagame.platform.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ������ʾ��ӽ����adapter
 * 
 * @author Steven.xu
 */

public class PopAddAdapter extends BaseAdapter {

	LayoutInflater inflater;
	public List<Map<String, Object>> apps;
	/**
	 *  ����Ӧ���б�Ĺ�ѡ��Ϣ��
	 */
	public static Map<Integer, Boolean> isSelected; 
	PackageManager pm;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return apps.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return apps.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.popaddlist, parent, false);
		}

		LinearLayout  hold = (LinearLayout) convertView;
		ImageView iv = (ImageView) hold.findViewById(R.id.img);
		TextView tv = (TextView)hold.findViewById(R.id.title);
		CheckBox cb= (CheckBox) hold.findViewById(R.id.cb);
		ResolveInfo  ri = (ResolveInfo)(apps.get(position).get("resolveInfo"));
		iv.setBackgroundDrawable(ri.activityInfo.loadIcon(pm));
		tv.setText(ri.activityInfo.loadLabel(pm));
		cb.setChecked(isSelected.get(position));
		return convertView;
	}

	@SuppressLint({ "UseSparseArrays", "UseSparseArrays" })
	public  PopAddAdapter(Activity activity) {    
		apps = new ArrayList<Map<String, Object>>();
		inflater  = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pm = activity.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> rlist = pm.queryIntentActivities(mainIntent, 0);
		isSelected = new HashMap<Integer, Boolean>();
		Iterator<ResolveInfo> iterator = rlist.iterator();
		Cursor cusor= GameCenter.aph.Qurey(null,null);
		while(iterator.hasNext())
		{	 
			ResolveInfo ri = (ResolveInfo) iterator.next();
			String packagname = ri.activityInfo.packageName;
			if(this.searchApp(cusor, packagname))
				continue;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resolveInfo", ri);
			apps.add(map);

			//�������isSelected���map�Ǽ�¼ÿ��listitem��״̬����ʼ״̬ȫ��Ϊfalse��      
			for (int i = 0; i < apps.size(); i++) {    
				isSelected.put(i, false);    
			}   
		}
	}    
	/**
	 * �ж�Ӧ���Ƿ��Ѵ��������ݿ���
	 * 
	 * @param cusor ���ݿⷵ�ص�����Ӧ��
	 * @param pkgname ָ����Ӧ�ð���
	 * @return
	 */
	private  boolean  searchApp(Cursor cusor, String pkgname) 
	{
		cusor.moveToFirst();
		cusor.move(-1);
		Log.d("CURSOR", "CURRENT POS "+cusor.getPosition());
		int count = cusor.getCount();
		while((count-- ) > 0)
		{
			cusor.moveToNext();
			String name = cusor.getString(cusor.getColumnIndex("_name"));
			if(pkgname.equals(name))
				return true;
		}
		return false;
	}
}

