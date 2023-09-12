package com.dessertoasis.demo.service.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.websocket.ChatMessage;
import com.dessertoasis.demo.model.websocket.ChatMessage.MessageState;
import com.dessertoasis.demo.model.websocket.ChatMessageRepository;

@Service
public class WSService {

	@Autowired
	private SimpMessagingTemplate messageTemplete;
	
	private ChatMessageRepository cRepo;
	
	
	public void notifyUser(ChatMessage chatMessage) {
		ChatMessage message = cRepo.save(chatMessage);
		if (message != null) {
			messageTemplete.convertAndSendToUser(chatMessage.getCatcher()+"", "/topic/private-messages", "aaas");
			message.setMessageState(MessageState.UNREAD);
			cRepo.save(message);
		}
	}
	
}
