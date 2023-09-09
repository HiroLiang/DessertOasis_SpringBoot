package com.dessertoasis.demo.model.sort;

import java.util.Date;

import lombok.Data;

@Data
public class DateRules {
	
	private String key;
	private String type;
	private Date start;
	private Date end;
	public DateRules(String key, String type, Date start, Date end) {
		super();
		this.key = key;
		this.type = type;
		this.start = start;
		this.end = end;
	}
	public DateRules() {
		super();
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

}
