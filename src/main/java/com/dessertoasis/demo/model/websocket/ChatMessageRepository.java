package com.dessertoasis.demo.model.websocket;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

	@Query("SELECT c FROM ChatMessage c WHERE ( c.sender = :sender AND c.catcher = :catcher ) or ( c.sender = :catcher AND c.catcher = :sender )")
	List<ChatMessage> findBySenderAndCatcher(@Param("sender") Integer sender, @Param("catcher") Integer catcher);

	@Query("SELECT c FROM ChatMessage c WHERE ( c.sender = :id AND c.catcher = :sender ) or ( c.sender = :sender AND c.catcher = :id ) ORDER BY c.sendTime DESC")
	List<ChatMessage> findLastMessage(@Param("id") Integer id, @Param("sender") Integer sender);

	@Query("SELECT c FROM ChatMessage c WHERE ( c.sender = :sender AND c.catcher = :catcher ) or ( c.sender = :catcher AND c.catcher = :sender )")
	Page<ChatMessage> findBySenderAndCatcher(@Param("sender") Integer sender, @Param("catcher") Integer catcher,
			Pageable pageable);

	@Query("SELECT c FROM ChatMessage c WHERE c.catcher = :catcher AND c.messageState = :messageState")
	Page<ChatMessage> findBySenderAndCatcher(@Param("catcher") Integer catcher,
			@Param("messageState") String messageState, Pageable pageable);

}
