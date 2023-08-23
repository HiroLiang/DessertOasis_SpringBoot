package com.dessertoasis.demo.model.Chat;

public class ChatMessage {
	
	private ChatType type;
	private String  sender;
	private String content;
	
	public enum ChatType {
		CHAT,
		JOIN,
		LEAVE
	}
	
	public ChatMessage(ChatType type, String sender, String content) {
		super();
		this.type = type;
		this.sender = sender;
		this.content = content;
	}

	public ChatMessage() {
		super();
	}

	public ChatType getType() {
		return type;
	}

	public void setType(ChatType type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
