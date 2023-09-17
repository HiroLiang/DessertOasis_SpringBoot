package com.dessertoasis.demo.model.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Member findByAccount(String account);
	
	@Query("FROM Member WHERE access = 'ADMIN' AND id != :id ")
	List<Member> findByAccessExcept(Integer id);

	Optional<Member> findByEmail(String email);

	
}
