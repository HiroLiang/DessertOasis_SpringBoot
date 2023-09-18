package com.dessertoasis.demo.model.websocket;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "chatMessage")
public class ChatMessage {

	@Id
	@Column(name = "ID", columnDefinition = "INT")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "SENDER", columnDefinition = "INT")
	private Integer sender;
	@Column(name = "CATCHER", columnDefinition = "INT")
	private Integer catcher;
	@Column(name = "CHATMESSAGE", columnDefinition = "NVARCHAR(255)")
	private String chatMessage;
	@Column(name = "SENDTIME", columnDefinition = "DATETIME2")
	private LocalDateTime sendTime;
	@Column(name = "MESSAGESTATE", columnDefinition = "NVARCHAR(50)")
	private String messageState;

	public ChatMessage(Integer sender, Integer catcher, String chatMessage, LocalDateTime sendTime,
			String messageState) {
		super();
//		this.id = id;
		this.sender = sender;
		this.catcher = catcher;
		this.chatMessage = chatMessage;
		this.sendTime = sendTime;
		this.messageState = messageState;
	}

	public ChatMessage() {
		super();
	}

}
