package com.dessertoasis.demo.service.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.cart.Cart;
import com.dessertoasis.demo.model.cart.CartRepository;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.service.CategoryService;
import com.dessertoasis.demo.service.MemberService;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cRepo;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;
	
	public List<Cart> findByMemberId(Integer memberId) {
		Member member = memberService.findByMemberId(memberId);
		
		if (member != null) {
			return member.getCarts();
		}
		
		return null;
	}
	
	public Cart findByCartId(Integer cartId) {
		Optional<Cart> optional = cRepo.findById(cartId);
		if (optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
	public Cart insert(Cart cart) {
		// 調整 categoryId 為最上層之 categoryId
		Integer categoryId = cart.getCategoryId();
		Category category = categoryService.findCategory(categoryId);
		while (category.getParent() != null) {
			category = category.getParent();
		}
		cart.setCategoryId(category.getId());
		
		return cRepo.save(cart);
	}
	
	public void deleteById(Integer cartId) {
		cRepo.deleteById(cartId);
	}
}
