package com.dessertoasis.demo.model.websocket;

import java.security.Principal;

public class MyPrincipal implements Principal {
	private String  name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
