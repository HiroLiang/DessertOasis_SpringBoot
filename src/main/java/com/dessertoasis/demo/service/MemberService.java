package com.dessertoasis.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository mRepo;

	//新增
	public void insert(Member member) {
		mRepo.save(member);
	}
	
	
	//查詢單筆
	public Member findById(Integer id) {
		Optional<Member> optional = mRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	
	//查詢全部
		public List<Member> findAll() {
			List<Member> result = mRepo.findAll();
			return result;
		}
		
		
		
}
