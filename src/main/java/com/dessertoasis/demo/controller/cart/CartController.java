package com.dessertoasis.demo.controller.cart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.CartDTO;
import com.dessertoasis.demo.model.cart.CourseCartDTO;
import com.dessertoasis.demo.model.cart.ProductCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCartDTO;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.service.cart.CartService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	// 取出會員的購物車物品數量
	@GetMapping("/cart/count")
	public Integer getCountByMember(HttpSession session) {
		Member user = (Member) session.getAttribute("loggedInMember");
		if (user == null) {
			return null;
		}
		return cartService.getCountByMemberId(user.getId());
	}
	
	// 取出會員之購物車的所有商品
	@GetMapping("/cart/product")
	public List<ProductCartDTO> getProductCart(HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		List<ProductCartDTO> cartList = cartService.getProductCartDTOs(member.getId());
		return cartList;
	}
	
	// 取出會員之購物車的所有課程
	@GetMapping("/cart/course")
	public List<CourseCartDTO> getCourseCart(HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		List<CourseCartDTO> cartList = cartService.getCourseCartDTOs(member.getId());
		return cartList;
	}
	
	// 取出會員之購物車的所有教室預約
	@GetMapping("/cart/reservation")
	public List<ReservationCartDTO> getReservationCart(HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		List<ReservationCartDTO> cartList = cartService.getReservationCartDTOs(member.getId());
		return cartList;
	}
	
	// 加入購物車
	@PostMapping("/cart")
	public String addToCart(@RequestBody CartDTO cartDTO, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		if (member == null) {
			return "加入購物車失敗，沒有此會員";
		}
		
		cartService.insert(cartDTO, member.getId());
		
		return "1";
	}
	
	@PatchMapping("/cart/product")
	public void updateProdQuantities(@RequestBody List<Map<String, Integer>> items) {
		for (Map<String, Integer> item : items) {
			cartService.updateProdQuantity(item.get("cartId"), item.get("prodQuantity"));
		}
	}
	
	// 刪除購物車項目
	@DeleteMapping("/cart/{cartId}")
	public void deleteCart(@PathVariable("cartId") Integer cartId) {
		cartService.deleteCart(cartId);
	}
}
