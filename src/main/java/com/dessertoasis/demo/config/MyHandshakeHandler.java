package com.dessertoasis.demo.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.dessertoasis.demo.model.websocket.MyPrincipal;

public class MyHandshakeHandler extends DefaultHandshakeHandler {

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		 String id=null;
	        if (request instanceof ServletServerHttpRequest) {
	            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
	            id = ((ServletServerHttpRequest) request).getServletRequest().getSession().getId();
	            System.out.printf("this is " + id);

	        }

	        Principal principal =new MyPrincipal();
	        ((MyPrincipal) principal).setName(id);
	        
	        return principal;
	    }

}
