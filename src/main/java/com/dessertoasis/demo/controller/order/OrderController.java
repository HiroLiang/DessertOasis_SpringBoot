package com.dessertoasis.demo.controller.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.CourseCartDTO;
import com.dessertoasis.demo.model.cart.ProductCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCartDTO;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.order.CourseOrderItem;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.ProdOrderItem;
import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.MemberService;
import com.dessertoasis.demo.service.ProductService;
import com.dessertoasis.demo.service.cart.ReservationCartService;
import com.dessertoasis.demo.service.classroom.ReservationService;
import com.dessertoasis.demo.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
	private ReservationCartService rcService;
	
	@Autowired
	private ReservationService reservationService;
	
	// 新增訂單
	@PostMapping("/order/{memberId}")
	public String insertOrder(@PathVariable("memberId") Integer memberId, @RequestBody Map<String, List<Map<String, Object>>> cartList) {
		Order order = new Order();
		
		List<Map<String, Object>> productCart = cartList.get("product");
		List<Map<String, Object>> courseCart = cartList.get("course");
		List<Map<String, Object>> rsvCart = cartList.get("reservation");
		 
		if (productCart != null) {
			order.setProdOrderItems(new ArrayList<ProdOrderItem>());
			for (Map<String, Object> pCartItemMap : productCart) {
				ObjectMapper objectMapper = new ObjectMapper();
				ProductCartDTO pCartItem = objectMapper.convertValue(pCartItemMap, ProductCartDTO.class);
				Product product = productServie.findProductById(pCartItem.getProductId());
				
				ProdOrderItem pOrdItem = new ProdOrderItem();
				pOrdItem.setOrder(order);
				pOrdItem.setProduct(product);
				pOrdItem.setPrice(product.getProdPrice());
				pOrdItem.setQuantity(pCartItem.getProdQuantity());
				
				order.getProdOrderItems().add(pOrdItem);
			}
		}
		
		if (courseCart != null) {
			order.setCourseOrderItems(new ArrayList<CourseOrderItem>());
			for (Map<String, Object> cCartItemMap : courseCart) {
				ObjectMapper objectMapper = new ObjectMapper();
				CourseCartDTO cCartItem = objectMapper.convertValue(cCartItemMap, CourseCartDTO.class);
				Course course = courseService.findById(cCartItem.getCartId());
				
				CourseOrderItem cOrdItem = new CourseOrderItem();
				cOrdItem.setOrder(order);
				cOrdItem.setCourse(course);
				cOrdItem.setPrice(cCartItem.getCoursePrice());
				
				order.getCourseOrderItems().add(cOrdItem);
			}
		}
		
		if (rsvCart != null) {
			order.setReservations(new ArrayList<Reservation>());
			for (Map<String, Object> rCartItemMap : rsvCart) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule()); // 讓 json 支持 java8 的 localDate
				ReservationCartDTO rCartItem = objectMapper.convertValue(rCartItemMap, ReservationCartDTO.class);
				
				// 查此教室某天某時段是否已經預約
				Reservation rsv = reservationService.getByRoomId(
						rCartItem.getClassroom().getId(), rCartItem.getReservationDate(), rCartItem.getReservationTime());
				
				if (rsv != null) {
					String room = rsv.getClassroom().getRoomName();
					String date = rsv.getReservationDate().toString();
					String time = reservationService.timeMap(rsv.getReservationTime());
					return room + "-" + date + "-" + time + " 已被預約";
				}else {
					rsv = new Reservation(rCartItem);
				}
				
				order.getReservations().add(rsv);
			}
		}
		
		order.setMember(memberService.findByMemberId(memberId));
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		order.setOrdDate(currentDateTime);
		order.setUpdateDate(currentDateTime);
		
		order.setOrdStatus("訂單成立");
		
		orderService.insert(order);

		return "訂單新增成功";
	}
}
