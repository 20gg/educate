package com.hysm.model;

	/**
	 * 用户实体类
	 * @author songkai 
	 */
public class User {
	 

	private int id,role_id,state,baby_num,grade,assess;
	
	private String user_id,password,open_id,phone,name,call,head,note;
	
	private String role_name;
	
	private int login_type;// 1平台登录，2微信登陆，3app登录
	
	 
	public int getLogin_type() {
		return login_type;
	}

	public void setLogin_type(int login_type) {
		this.login_type = login_type;
	}

	public int getId() {
		return id;
	}

	public int getRole_id() {
		return role_id;
	}

	public int getState() {
		return state;
	}

	public int getBaby_num() {
		return baby_num;
	}

	public int getGrade() {
		return grade;
	}

	public int getAssess() {
		return assess;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getPassword() {
		return password;
	}

	public String getOpen_id() {
		return open_id;
	}

	public String getPhone() {
		return phone;
	}

	public String getName() {
		return name;
	}

	public String getCall() {
		return call;
	}

	public String getHead() {
		return head;
	}

	public String getNote() {
		return note;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setBaby_num(int baby_num) {
		this.baby_num = baby_num;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setAssess(int assess) {
		this.assess = assess;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	
}
