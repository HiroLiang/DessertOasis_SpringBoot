package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class LinePayResponse {

	private String returnCode;
	private String returnMessage;
	private Info info;
	public LinePayResponse(String returnCode, String returnMessage, Info info) {
		super();
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
		this.info = info;
	}
	public LinePayResponse() {
		super();
	}
	
}
