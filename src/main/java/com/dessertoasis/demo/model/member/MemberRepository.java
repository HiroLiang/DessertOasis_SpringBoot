package com.dessertoasis.demo.model.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Member findByAccount(String account);
}
