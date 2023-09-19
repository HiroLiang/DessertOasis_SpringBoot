package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class RedirectUrls {

	private String appPackageName;
	private String confirmUrl;
	private String confirmUrlType;
	private String cancelUrl;
	public RedirectUrls(String appPackageName, String confirmUrl, String confirmUrlType, String cancelUrl) {
		super();
		this.appPackageName = appPackageName;
		this.confirmUrl = confirmUrl;
		this.confirmUrlType = confirmUrlType;
		this.cancelUrl = cancelUrl;
	}
	public RedirectUrls() {
		super();
	}
	
}
