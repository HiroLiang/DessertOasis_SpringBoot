package com.dessertoasis.demo.controller.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public Page<ChatMessage> getChatMessages(@RequestParam Integer sender, @RequestParam Integer catcher,
			@RequestParam Integer page) {
		Page<ChatMessage> messages = wsService.getChatMessages(sender, catcher, page);
		if (messages != null)
			return messages;
		return null;
	}

	@GetMapping("/message/unread")
	public Integer getUnreadSum(@RequestParam Integer catcher) {
		Integer sum = wsService.getUnreadSum(catcher);
		if (sum > 0)
			return sum;
		return 0;
	}

	@GetMapping("/message/admins")
	public List<AdminsWithLastMessage> getAllAdminsWithMsg(@RequestParam Integer id) {
		System.out.println(id);
		List<Member> allAdmins = mService.getAllAdmin(id);
		List<AdminsWithLastMessage> list = new ArrayList<>();
		if (allAdmins != null && allAdmins.size() != 0) {
			for (Member admin : allAdmins) {
				AdminsWithLastMessage adWithMsg = new AdminsWithLastMessage();
				admin.setPasswords(null);
				admin.setCode(null);
				admin.setCarts(null);
				admin.setRecipes(null);
				adWithMsg.setMember(admin);
				ChatMessage message = wsService.findLastMessage(admin.getId(), id);
				if (message != null && message.getChatMessage() != null) {
					adWithMsg.setLastMessage(message);
				} else {
					ChatMessage emptyMsg = new ChatMessage();
					emptyMsg.setChatMessage(" ");
					adWithMsg.setLastMessage(emptyMsg);
				}
				list.add(adWithMsg);
			}
		}
		return list;
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
