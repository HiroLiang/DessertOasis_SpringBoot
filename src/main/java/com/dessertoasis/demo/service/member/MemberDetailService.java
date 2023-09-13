package com.dessertoasis.demo.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.member.MemberDetail;
import com.dessertoasis.demo.model.member.MemberDetailRepository;

@Service
public class MemberDetailService {
	@Autowired
	private MemberDetailRepository memberDetailRepository;
	
	//查詢
	public MemberDetail getMemberDetailByMemberId(Integer memberId) {
		
		return memberDetailRepository.findByMemberId(memberId);
	}
	//更新
	 public MemberDetail createMemberDetail(MemberDetail memberDetail) {
	        return memberDetailRepository.save(memberDetail);
	    }
}
