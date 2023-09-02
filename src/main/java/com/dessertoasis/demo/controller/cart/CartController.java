package com.dessertoasis.demo.controller.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.Cart;
import com.dessertoasis.demo.model.cart.ReservationCart;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.MemberService;
import com.dessertoasis.demo.service.ProductService;
import com.dessertoasis.demo.service.cart.CartService;
import com.dessertoasis.demo.service.cart.ReservationCartService;

@RestController
public class CartController {
	
	private Integer productCategoryId = 1;
	private Integer courseCategoryId = 2;
	private Integer reservationCategoryId = 4;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ReservationCartService rcService;
	
	// 取出會員購物車所有東西
	@GetMapping("/carts/{memberId}")
	public Map<String, Object> findAllCartsByMemberId(@PathVariable("memberId") Integer memberId) {
		
		List<Cart> cartList = cartService.findByMemberId(memberId);
		
		if (cartList == null) return null;
		
		List<Map<String, Object>> productList = new ArrayList<>();
		List<Map<String, Object>> courseList = new ArrayList<>();
		List<Map<String, Object>> reservationCartList = new ArrayList<>();	
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == productCategoryId) {
				Map<String, Object> product = new HashMap<>();
				product.put("cartId", cart.getId());
				product.put("product", productService.findProductById(cart.getInterestedId()));
				product.put("quantity", cart.getProdQuantity());
				productList.add(product);
			}else if (cart.getCategoryId() == courseCategoryId) {
				Map<String, Object> course = new HashMap<>();
				course.put("cartId", cart.getId());
				course.put("course", courseService.findById(cart.getInterestedId()));
				courseList.add(course);
			}else {
				Map<String, Object> reservationCart = new HashMap<>();
				reservationCart.put("cartId", cart.getId());
				reservationCart.put("reservation", rcService.findById(cart.getInterestedId()));
				reservationCartList.add(reservationCart);
			}
		}
		
		Map<String, Object> cart = new HashMap<>();
		cart.put("productCart", productList);
		cart.put("courseCart", courseList);
		cart.put("reservationCart", reservationCartList);
		
		return cart;
	}
	
	// 加入購物車
	@PostMapping("/cart")
	public String addToCart(@RequestBody Cart cart) {
		Integer memberId = cart.getMemberId();
		Member member = memberService.findByMemberId(memberId);
		if (member != null) {
			cart.setMember(member);
			cartService.insert(cart);
			return "加入購物車成功";
		}
		return "加入購物車失敗，沒有此會員";
	}
	
	// 刪除購物車項目
	@DeleteMapping("/cart/{cartId}")
	public void deleteCart(@PathVariable("cartId") Integer cartId) {
		Cart cart = cartService.findByCartId(cartId);
		if (cart != null && cart.getCategoryId() == reservationCategoryId) {
			rcService.deleteById(cart.getInterestedId());
		}
		cartService.deleteById(cartId);
	}
	
	
	
	
	
	
	
	
	
}
