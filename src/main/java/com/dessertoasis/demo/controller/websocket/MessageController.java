package com.dessertoasis.demo.controller.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import com.dessertoasis.demo.model.websocket.ChatMessage;
import com.dessertoasis.demo.service.websocket.WSService;

@Controller
@EnableScheduling
public class MessageController {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private WSService wService;

	@MessageMapping("/send-private-message")
	public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
		System.out.println(chatMessage + "msgMap");
		chatMessage.setMessageState("UNREAD");
		ChatMessage message = wService.saveMessage(chatMessage);
		if (message != null) {
			template.convertAndSend("/topic/getResponse/user-" + message.getCatcher(), message);
			template.convertAndSend("/topic/getResponse/user-" + message.getSender(), message);
		} else {
			chatMessage.setMessageState("UNSEND");
			template.convertAndSend("/topic/getResponse/user-" + chatMessage.getSender(), chatMessage);
		}
	}

}
