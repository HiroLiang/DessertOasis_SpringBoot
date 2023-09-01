package com.dessertoasis.demo.service.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.cart.Cart;
import com.dessertoasis.demo.model.cart.CartRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.service.MemberService;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cRepo;
	
	@Autowired
	private MemberService memberService;
	
	public List<Cart> findByMemberId(Integer memberId) {
		Member member = memberService.findByMemberId(memberId);
		
		if (member != null) {
			return member.getCartList();
		}
		
		return null;
	}
	
}
