package com.viagame.platform.bean;

import android.database.Cursor;

public class Game {
	public final static String CONTROLL_C1 = "C1";
	public final static String CONTROLL_F2 = "F2";
	public final static String CONTROLL_E1 ="E1";
	public final static String CONTROLL_K1 ="K1";
	
	private int id = 0;
	private String name = "";
	private String lable = "";
	private String url = "";
	private String desc = "";
	private String controll = "C1";
	private String exits = "true";
	public Game()
	{
		super();
	}
	public Game(Cursor c)
	{
		this.name = c.getString(c.getColumnIndex("_name"));
		this.lable = c.getString(c.getColumnIndex("_lable"));
		this.url = c.getString(c.getColumnIndex("_url"));
		this.desc = c.getString(c.getColumnIndex("_description"));
		this.controll = c.getString(c.getColumnIndex("_control"));
		this.exits = c.getString(c.getColumnIndex("_exists"));
	}
	public String getExits() {
		return exits;
	}
	public void setExits(String exits) {
		this.exits = exits;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getControll() {
		return controll;
	}
	public void setControll(String controll) {
		this.controll = controll;
	}

}
