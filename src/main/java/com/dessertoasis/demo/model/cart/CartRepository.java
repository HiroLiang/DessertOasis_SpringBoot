package com.dessertoasis.demo.model.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dessertoasis.demo.model.member.Member;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	public Cart findByMemberAndCategoryIdAndInterestedId(Member member, Integer categoryId, Integer interestedId);
}
