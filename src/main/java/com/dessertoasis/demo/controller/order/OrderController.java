package com.dessertoasis.demo.controller.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.CourseCartDTO;
import com.dessertoasis.demo.model.cart.ProductCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCartDTO;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.order.CourseOrderItem;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.ProdOrderItem;
import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.MemberService;
import com.dessertoasis.demo.service.ProductService;
import com.dessertoasis.demo.service.classroom.ReservationService;
import com.dessertoasis.demo.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProductService productServie;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ReservationService reservationService;
	
	// 新增訂單
	@PostMapping("/order")
	public String insertOrder(@RequestBody Map<String, List<Map<String, Object>>> cartList, HttpSession session) {		
		Member member = (Member) session.getAttribute("loggedInMember");
		if (member == null) return "沒有會員";
		member = memberService.findByMemberId(member.getId());
		
		Order order = new Order();
		order.setMember(member);
		
		List<Map<String, Object>> productCart = cartList.get("product");
		List<Map<String, Object>> courseCart = cartList.get("course");
		List<Map<String, Object>> rsvCart = cartList.get("reservation");
		 
		if (productCart != null) {
			order.setProdOrderItems(new ArrayList<>());
			for (Map<String, Object> cartItemMap : productCart) {
				ObjectMapper objectMapper = new ObjectMapper();
				ProductCartDTO cartItem = objectMapper.convertValue(cartItemMap, ProductCartDTO.class);
				Product product = productServie.findProductById(cartItem.getProductId());
				ProdOrderItem ordItem = new ProdOrderItem(cartItem, product, order);
				order.getProdOrderItems().add(ordItem);
			}
		}
		
		if (courseCart != null) {
			order.setCourseOrderItems(new ArrayList<>());
			for (Map<String, Object> cartItemMap : courseCart) {
				ObjectMapper objectMapper = new ObjectMapper();
				CourseCartDTO cartItem = objectMapper.convertValue(cartItemMap, CourseCartDTO.class);
				Course course = courseService.findById(cartItem.getCourseId());
				CourseOrderItem ordItem = new CourseOrderItem(cartItem, course, order);
				order.getCourseOrderItems().add(ordItem);
			}
		}
		
		if (rsvCart != null) {
			order.setReservations(new ArrayList<>());
			for (Map<String, Object> cartItemMap : rsvCart) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule()); // 讓 json 支持 java8 的 localDate
				ReservationCartDTO cartItem = objectMapper.convertValue(cartItemMap, ReservationCartDTO.class);
				
				// 查此教室某天某時段是否已經預約
				Reservation rsv = reservationService.getByRoomId(
						cartItem.getClassroom().getId(), cartItem.getReservationDate(), cartItem.getReservationTime());
				if (rsv != null) {
					String room = rsv.getClassroom().getRoomName();
					String date = rsv.getReservationDate().toString();
					String time = reservationService.timeMap(rsv.getReservationTime());
					return room + "-" + date + "-" + time + " 已被預約";
				}
				
				rsv = new Reservation(cartItem, order);
				order.getReservations().add(rsv);
			}
		}
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		order.setOrdDate(currentDateTime);
		order.setUpdateDate(currentDateTime);
		order.setOrdStatus("訂單成立");
		
		orderService.insert(order);

		return "訂單新增成功";
	}
	
	
	
	
	
	
	
	
	

	
}
