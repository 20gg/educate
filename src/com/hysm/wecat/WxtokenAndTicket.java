package com.hysm.wecat;

import java.sql.Timestamp;

public class WxtokenAndTicket {

	private String token;
	private long token_ctime;
	private String jsticket;
	private long ticket_ctime;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getToken_ctime() {
		return token_ctime;
	}
	public void setToken_ctime(long token_ctime) {
		this.token_ctime = token_ctime;
	}
	public String getJsticket() {
		return jsticket;
	}
	public void setJsticket(String jsticket) {
		this.jsticket = jsticket;
	}
	public long getTicket_ctime() {
		return ticket_ctime;
	}
	public void setTicket_ctime(long ticket_ctime) {
		this.ticket_ctime = ticket_ctime;
	}
	
	
}
