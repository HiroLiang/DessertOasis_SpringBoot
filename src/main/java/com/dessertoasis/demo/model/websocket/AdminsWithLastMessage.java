package com.dessertoasis.demo.model.websocket;

import com.dessertoasis.demo.model.member.Member;

import lombok.Data;

@Data
public class AdminsWithLastMessage {
	
	private Member member;
	private ChatMessage lastMessage;
	public AdminsWithLastMessage(Member member, ChatMessage lastMessage) {
		super();
		this.member = member;
		this.lastMessage = lastMessage;
	}
	public AdminsWithLastMessage() {
		super();
	}

}
