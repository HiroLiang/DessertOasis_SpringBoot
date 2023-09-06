package com.dessertoasis.demo.model.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Integer> {
    
   
    MemberDetail findByMemberId(Integer memberId);
    
}
