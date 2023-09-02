package com.dessertoasis.demo.controller.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.Cart;
import com.dessertoasis.demo.model.cart.ReservationCart;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.ProductService;
import com.dessertoasis.demo.service.cart.CartService;
import com.dessertoasis.demo.service.cart.ReservationCartService;

@RestController
public class CartController {
	
	private Integer productCategoryId = 1;
	private Integer courseCategoryId = 2;
	private Integer reservationCategoryId = 3;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ReservationCartService rcService;
	
	@GetMapping("/cart/findall")
	public Map<String, Object> findAllCartsByMemberId(@RequestParam("memberId") Integer memberId) {
		
		List<Cart> cartList = cartService.findByMemberId(memberId);
		
		if (cartList == null) return null;
		
		List<Map<String, Object>> productList = new ArrayList<>();
		List<Course> courseList = new ArrayList<>();
		List<ReservationCart> reservationCartList = new ArrayList<>();	
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == productCategoryId) {
				Map<String, Object> product = new HashMap<>();
				product.put("product", productService.findProductById(cart.getInteretedId()));
				product.put("quantity", cart.getProdQuantity());
				productList.add(product);
			}else if (cart.getCategoryId() == courseCategoryId) {
				courseList.add(courseService.findById(cart.getInteretedId()));
			}else {
				reservationCartList.add(rcService.findById(cart.getInteretedId()));
			}
		}
		
		Map<String, Object> cart = new HashMap<>();
		cart.put("productCart", productList);
		cart.put("courseCart", courseList);
		cart.put("reservationCart", reservationCartList);
		
		return cart;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
