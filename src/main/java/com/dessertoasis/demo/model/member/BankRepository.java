package com.dessertoasis.demo.model.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
	Bank findByMemberId(Integer memberId);
}
