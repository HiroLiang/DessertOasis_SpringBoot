package com.dessertoasis.demo.controller.pay;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderDTO;
import com.dessertoasis.demo.model.pay.ConfirmBody;
import com.dessertoasis.demo.model.pay.ConfirmResponse;
import com.dessertoasis.demo.model.pay.LinePayResponse;
import com.dessertoasis.demo.model.pay.PayBody;
import com.dessertoasis.demo.service.order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
public class LinePayController {

	@Autowired
	private OrderService oService;

	private final String ChannelId = "2000804386";
	private final String sandboxUrl = "https://sandbox-api-pay.line.me";
	private final String ChannelSecret = "179f0d3119f4ebf49b0b8e6725c30435";

	public String uuidGgenerator() {
		return UUID.randomUUID().toString();
	}

	private String authorizationGenerator(String data) {
		return Base64.encodeBase64String(HmacUtils
				.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, ChannelSecret.getBytes()).doFinal(data.getBytes()));
	}

	@GetMapping("/pay/linepay/confirm")
	public String getLinePayConfirm(@RequestParam String transactionId, @RequestParam String orderId)
			throws JsonProcessingException {
		System.out.println(transactionId);
		System.out.println(orderId);
		Order order = oService.getByOrdId(Integer.parseInt(orderId));
		OrderDTO orderDTO = new OrderDTO(order);
		ConfirmBody confirmBody = new ConfirmBody();
		if (Integer.parseInt(orderId) == order.getId()) {
			ObjectMapper objectMapper = new ObjectMapper();
			RestTemplate restTemplate = new RestTemplate();
			String UUID = uuidGgenerator();
			HttpHeaders headers = new HttpHeaders();
			String requestUri = sandboxUrl + "/v3/payments/" + transactionId + "/confirm";
			confirmBody.setAmount(orderDTO.getTotal());
			confirmBody.setCurrency("TWD");

			headers.add("X-LINE-ChannelId", ChannelId);
			headers.add("Content-Type", "application/json");
			headers.add("X-LINE-ChannelSecret", ChannelSecret);
			headers.add("X-LINE-Authorization-Nonce", UUID);
			String authorization = authorizationGenerator(ChannelSecret + "/v3/payments/" + transactionId + "/confirm"
					+ objectMapper.writeValueAsString(confirmBody) + UUID);
			headers.add("X-LINE-Authorization", authorization);

			HttpEntity<ConfirmBody> httpEntity = new HttpEntity<>(confirmBody, headers);
			ConfirmResponse response = restTemplate.postForObject(requestUri, httpEntity, ConfirmResponse.class);
			System.out.println(response.getReturnCode());
			
			return "交易完成";
		}
		return "交易失敗";
	}

	@PostMapping("/pay/linepay")
	public String sendLinePayRequest(@RequestBody PayBody payBody, HttpSession session) throws JsonProcessingException {
		session.setAttribute("order", payBody);
		ObjectMapper objectMapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		String UUID = uuidGgenerator();
		HttpHeaders headers = new HttpHeaders();
		String requestUri = sandboxUrl + "/v3/payments/request";

		headers.add("X-LINE-ChannelId", ChannelId);
		headers.add("Content-Type", "application/json");
		headers.add("X-LINE-ChannelSecret", ChannelSecret);
		headers.add("X-LINE-Authorization-Nonce", UUID);
		String authorization = authorizationGenerator(
				ChannelSecret + "/v3/payments/request" + objectMapper.writeValueAsString(payBody) + UUID);
		headers.add("X-LINE-Authorization", authorization);

		HttpEntity<PayBody> httpEntity = new HttpEntity<>(payBody, headers);

		LinePayResponse response = restTemplate.postForObject(requestUri, httpEntity, LinePayResponse.class);

		System.out.println(response);

//		if ((response.getReturnCode() == "0000")) {
			return response.getInfo().getPaymentUrl().getWeb();
//		}
//		return null;
	}

}
