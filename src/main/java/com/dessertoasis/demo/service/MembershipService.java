package com.dessertoasis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.membership.Membership;
import com.dessertoasis.demo.model.membership.MembershipRepository;

@Service
public class MembershipService {
	@Autowired
	private MembershipRepository memberRep;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Membership memberLogin(String account , String password) {
		Membership loginAccount = memberRep.findByAccoount(account);
		if(loginAccount != null) {
			if(passwordEncoder.matches(password, loginAccount.getPassword())) {
				return loginAccount;
			}
		}
		return null;
	}
	
}
