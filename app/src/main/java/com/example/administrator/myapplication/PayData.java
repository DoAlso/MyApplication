package com.example.administrator.myapplication;

import java.io.Serializable;

public class PayData implements Serializable{
	private String tradeno;
	private String money;
	private String money_up;
	private String type;
	private String payurl;
	private String count;
	private String summoney;
	private String taskid;
	
	
	public PayData() {
		super();
	}
	public PayData(String tradeno, String money,String money_up, String type) {
		super();
		this.tradeno = tradeno;
		this.money = money;
		this.money_up = money_up;
		this.type = type;
	}
	
	public PayData(String count, String summoney) {
		super();
		this.count = count;
		this.summoney = summoney;
	}
	public String getTradeno() {
		return tradeno;
	}
	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPayurl() {
		return payurl;
	}
	public void setPayurl(String payurl) {
		this.payurl = payurl;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSummoney() {
		return summoney;
	}
	public void setSummoney(String summoney) {
		this.summoney = summoney;
	}
	public String getMoney_up() {
		return money_up;
	}
	public void setMoney_up(String money_up) {
		this.money_up = money_up;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
}
