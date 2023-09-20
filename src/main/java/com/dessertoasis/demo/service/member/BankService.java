package com.dessertoasis.demo.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.member.Bank;
import com.dessertoasis.demo.model.member.BankRepository;
@Service
public class BankService {
	@Autowired
	private BankRepository bRepo;
	
	//查詢
		public Bank getMemberBankByMemberId(Integer memberId) {
			
			return bRepo.findByMemberId(memberId);
		}
		
		
	
}
