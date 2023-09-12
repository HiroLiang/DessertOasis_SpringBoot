package com.dessertoasis.demo.controller.websocket;

import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.dessertoasis.demo.model.websocket.ChatMessage;

@Controller
@EnableScheduling
public class MessageController {

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private SimpMessageSendingOperations operation;
	
	@MessageMapping("/send-private-message")
	@SendToUser("/user/5/topic/private-message")
	public String sendPrivateMessage(@Payload  ChatMessage chatMessage) {
		System.out.println(chatMessage+"msgMap");
		return "hahaha";
	}
	
	
	@Scheduled(fixedRate = 10000)
	public void sendQueueMessage() {
		System.out.println("1 t0 1 push");
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
				.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId("a");
		headerAccessor.setLeaveMutable(true);
		
		ChatMessage message = new ChatMessage();
		message.setCatcher(1);
		message.setSender(2);
		message.setMessage("abcd");
		operation.convertAndSendToUser("5" , "/topic/private-messages", "asdfg");
	}
	
	@Scheduled(fixedRate = 10000)
	public void sendTopicMessage() {
		System.out.println("send many");
		ChatMessage message = new ChatMessage();
		message.setMessage("aaaa");
		message.setCatcher(1);
		template.convertAndSend("/topic/getResponse",message);
	}

}
