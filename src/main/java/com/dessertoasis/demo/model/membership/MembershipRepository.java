package com.dessertoasis.demo.model.membership;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
	
	Membership findByAccoount(String account);

}
