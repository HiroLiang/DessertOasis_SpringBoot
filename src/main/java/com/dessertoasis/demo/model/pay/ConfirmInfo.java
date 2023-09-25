package com.dessertoasis.demo.model.pay;

import java.util.List;

import lombok.Data;

@Data
public class ConfirmInfo {

	private String transactionId;
	private String authorizationExpireDate;
	private String regKey;
	private List<PayInfo> payInfo;
	private List<ConfirmPackages> packages;
	private MerchantReference merchantReference;
	private Shipping shipping;
	public ConfirmInfo(String transactionId, String authorizationExpireDate, String regKey, List<PayInfo> payInfo,
			List<ConfirmPackages> packages, MerchantReference merchantReference, Shipping shipping) {
		super();
		this.transactionId = transactionId;
		this.authorizationExpireDate = authorizationExpireDate;
		this.regKey = regKey;
		this.payInfo = payInfo;
		this.packages = packages;
		this.merchantReference = merchantReference;
		this.shipping = shipping;
	}
	public ConfirmInfo() {
		super();
	}
	
	
}
