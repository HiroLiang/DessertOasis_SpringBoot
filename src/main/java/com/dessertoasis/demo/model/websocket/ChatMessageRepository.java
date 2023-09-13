package com.dessertoasis.demo.model.websocket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

	@Query("SELECT c FROM ChatMessage c WHERE ( c.sender = :sender AND c.catcher = :catcher ) or ( c.sender = :catcher AND c.catcher = :sender )")
	List<ChatMessage> findBySenderAndCatcher(@Param("sender") Integer sender , @Param("catcher") Integer catcher);
	
	@Query("SELECT c FROM ChatMessage c WHERE ( c.sender = :id AND c.catcher = :sender ) ORDER BY c.sendTime DESC")
	ChatMessage findLastMessage(@Param("id") Integer id,@Param("sender") Integer sender);
	
}
