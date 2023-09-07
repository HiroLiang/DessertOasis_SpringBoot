package com.dessertoasis.demo.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.CartListDTO;
import com.dessertoasis.demo.model.cart.CourseCartDTO;
import com.dessertoasis.demo.model.cart.ProductCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCartDTO;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.service.cart.CartService;
import com.dessertoasis.demo.service.order.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartService cartService;
	
	// 取得單一訂單
	@GetMapping("/order/{ordId}")
	public Order getOrderById(@PathVariable("ordId") Integer ordId) {
		return orderService.getByOrdId(ordId);
	}
	
	// 新增訂單
	@PostMapping("/order")
	public ResponseEntity<?> insertOrder(@RequestBody CartListDTO cartList, HttpSession session) {		
		Member member = (Member) session.getAttribute("loggedInMember");
		if (member == null) return ResponseEntity.ok("沒有會員");;
				
		List<ProductCartDTO> productCartDTOs = cartList.getProductCartDTOs();
		List<CourseCartDTO> courseCartDTOs = cartList.getCourseCartDTOs();
		List<ReservationCartDTO> rsvCartDTOs = cartList.getReservationCartDTOs();
		
		// 檢查教室是否已被預約
		String rsvCheckResult = orderService.checkReservation(rsvCartDTOs);
		if (rsvCheckResult != null) {
			return ResponseEntity.ok(rsvCheckResult);
		}
		
		Order order = new Order();
		
		// 訂單設置 orderItem
		order = orderService.placeProdOrderItem(order, productCartDTOs);
		order = orderService.placeCourseOrderItem(order, courseCartDTOs);
		order = orderService.placeReservation(order, rsvCartDTOs);
		
		// 新增訂單
		order = orderService.insert(order, member.getId());
		
		// 清掉購物車
		cartService.deleteCarts(productCartDTOs, courseCartDTOs, rsvCartDTOs);
		
		return ResponseEntity.ok(order);
	}

	
}
