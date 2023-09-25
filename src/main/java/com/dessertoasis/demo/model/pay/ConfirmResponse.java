package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class ConfirmResponse {

	private String returnCode;
	private String returnMessage;
	private ConfirmInfo info;
	public ConfirmResponse(String returnCode, String returnMessage, ConfirmInfo info) {
		super();
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
		this.info = info;
	}
	public ConfirmResponse() {
		super();
	}
	
}
