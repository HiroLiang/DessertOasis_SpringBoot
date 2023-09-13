package com.dessertoasis.demo.controller.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.websocket.AdminsWithLastMessage;
import com.dessertoasis.demo.model.websocket.ChatMessage;
import com.dessertoasis.demo.service.member.MemberService;
import com.dessertoasis.demo.service.websocket.WSService;

@RestController
public class WSController {

	@Autowired
	private SimpMessagingTemplate messageTemplete;

	@Autowired
	private WSService wsService;

	@Autowired
	private MemberService mService;

	@GetMapping("/message/history")
	public List<ChatMessage> getChatMessages(@RequestParam Integer sender, @RequestParam Integer catcher) {
		List<ChatMessage> messages = wsService.getChatMessages(sender, catcher);
		if (messages != null)
			return messages;
		return null;
	}

	@GetMapping("/message/admins")
	@Transactional
	public List<AdminsWithLastMessage> getAllAdminsWithMsg(@RequestParam Integer id, @RequestParam Integer sender) {
		List<Member> allAdmins = mService.getAllAdmin(id);
		List<AdminsWithLastMessage> list = new ArrayList<>();
		if (allAdmins != null) {
			for (Member admin : allAdmins) {
				AdminsWithLastMessage adWithMsg = new AdminsWithLastMessage();
				adWithMsg.setMember(admin);
				ChatMessage message = wsService.findLastMessage(id, sender);
				if (message != null)
					adWithMsg.setLastMessage(message);
			}
			return list;
		}
		return null;
	}

	@PutMapping("/message/setReaded")
	public ChatMessage readMessage(@RequestBody ChatMessage chatMessage) {
		ChatMessage message = wsService.findById(chatMessage.getId());
		if (message != null) {
			message.setMessageState("READED");
			messageTemplete.convertAndSend("/topic/getResponse/user-" + message.getSender(), message);
			return message;
		}
		return null;
	}
}
