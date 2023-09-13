package com.dessertoasis.demo.service.websocket;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.websocket.ChatMessage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.dessertoasis.demo.model.websocket.ChatMessageRepository;

@Service
public class WSService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ChatMessageRepository cRepo;
	
	public List<ChatMessage> getChatMessages(Integer sender , Integer catcher){
		List<ChatMessage> chatList = cRepo.findBySenderAndCatcher(sender, catcher);
		if(chatList != null)
			return chatList;
		return null;
	}
	
	public ChatMessage saveMessage(ChatMessage chatMessage) {
		ChatMessage message = cRepo.save(chatMessage);
		if(message != null) {
			return message;
		}
		return null;
	}
	
	public ChatMessage findById(Integer id) {
		Optional<ChatMessage> message = cRepo.findById(id);
		if(message.isPresent()) {
			ChatMessage chatMessage = message.get();
			chatMessage.setMessageState("READED");
			cRepo.save(chatMessage);
			return chatMessage;
		}
		return null;
	}
	
	public ChatMessage findLastMessage(Integer id, Integer sender) {
		ChatMessage lastMessage = cRepo.findLastMessage(id, sender);
		if(lastMessage!=null)
			return lastMessage;
		return null;
	}
}
