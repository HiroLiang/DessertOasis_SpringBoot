package com.dessertoasis.demo.model.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dessertoasis.demo.model.member.Member;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	Page<Order> findByMember(Member member, Pageable pageable);
}
