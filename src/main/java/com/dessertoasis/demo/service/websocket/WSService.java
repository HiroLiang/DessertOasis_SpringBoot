package com.dessertoasis.demo.service.websocket;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public Page<ChatMessage> getChatMessages(Integer sender, Integer catcher, Integer page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		Page<ChatMessage> chatList = cRepo.findBySenderAndCatcher(sender, catcher, pageable);

		if (chatList != null)
			return chatList;
		return null;
	}

	public Integer getUnreadSum(Integer catcher) {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<ChatMessage> chatList = cRepo.findBySenderAndCatcher(catcher, "UNREAD", pageable);
		if (chatList != null) {
			return chatList.getNumberOfElements();
		}
		return 0;
	}

	public ChatMessage saveMessage(ChatMessage chatMessage) {
		ChatMessage message = cRepo.save(chatMessage);
		if (message != null) {
			return message;
		}
		return null;
	}

	public ChatMessage findById(Integer id) {
		Optional<ChatMessage> message = cRepo.findById(id);
		if (message.isPresent()) {
			ChatMessage chatMessage = message.get();
			chatMessage.setMessageState("READED");
			cRepo.save(chatMessage);
			return chatMessage;
		}
		return null;
	}

	public ChatMessage findLastMessage(Integer id, Integer sender) {
		List<ChatMessage> lastMessage = cRepo.findLastMessage(id, sender);
		if (lastMessage.size() != 0)
			return lastMessage.get(0);
		return null;
	}
}
