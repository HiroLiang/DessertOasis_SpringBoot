package com.dessertoasis.demo.model.sort;

import lombok.Data;

@Data
public class SearchRules {
	
	private String input;
	private String key;
	private String type;
	public SearchRules() {
		super();
	}
	public SearchRules(String input, String key, String type) {
		super();
		this.input = input;
		this.key = key;
		this.type = type;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
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

}
