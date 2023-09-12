package com.dessertoasis.demo.controller.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.websocket.ChatMessage;
import com.dessertoasis.demo.model.websocket.ChatMessage.MessageState;
import com.dessertoasis.demo.service.websocket.WSService;

import jakarta.servlet.http.HttpSession;

@RestController
public class WSController {
	
	@Autowired
	private WSService wsService;
	
	@PostMapping("/send-private-message")
	public void sendPrivateMessage(@RequestBody ChatMessage chatMessage,HttpSession session) {
		System.out.println(chatMessage);
		Member user = (Member) session.getAttribute("loggedInMember");
		chatMessage.setSender(user.getId());
		chatMessage.setMessageState(MessageState.UNSENDED);
		wsService.notifyUser(chatMessage);
	}
	
}
