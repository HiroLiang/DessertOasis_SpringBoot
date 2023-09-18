package com.dessertoasis.demo.service.order;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import lombok.Data;

@Data
public class HmacSignature {
//2000804386
//179f0d3119f4ebf49b0b8e6725c30435
	
	private String ChannelId = "2000804386";
	private String sandboxUrl = "https://sandbox-api-pay.line.me";
	private String Nonce = UUID.randomUUID().toString();
	private String ChannelSecret = "179f0d3119f4ebf49b0b8e6725c30435";

	//簽章
	public static String encrypt(final String keys, final String data) {
        return Base64.encodeBase64String(HmacUtils.getInitializedMac( HmacAlgorithms.HMAC_SHA_256,keys.getBytes()).doFinal(data.getBytes()));
    }
	
}
