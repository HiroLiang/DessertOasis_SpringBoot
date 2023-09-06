package com.dessertoasis.demo.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderItemsDTO;
import com.dessertoasis.demo.service.MemberService;
import com.dessertoasis.demo.service.ProductService;
import com.dessertoasis.demo.service.cart.CartService;
//import com.dessertoasis.demo.service.classroom.ReservationService;
import com.dessertoasis.demo.service.course.CourseService;
import com.dessertoasis.demo.service.order.OrderService;
import com.dessertoasis.demo.service.order.ReservationService;

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
	
	@Autowired
	private CartService cartService;
	
	// 新增訂單
//	@PostMapping("/order")
//	public String insertOrder(@RequestBody CartListDTO cartList, HttpSession session) {		
//		Member member = (Member) session.getAttribute("loggedInMember");
//		if (member == null) return "沒有會員";
//		member = memberService.findByMemberId(member.getId());
//		
//		Order order = new Order();
//		order.setMember(member);
//		
//		List<ProductCartDTO> productCartDTOs = cartList.getProductCartDTOs();
//		List<CourseCartDTO> courseCartDTOs = cartList.getCourseCartDTOs();
//		List<ReservationCartDTO> rsvCartDTOs = cartList.getReservationCartDTOs();
//		 
//		if (productCartDTOs != null) {
//			order.setProdOrderItems(new ArrayList<>());
//			for (ProductCartDTO cartDTO : productCartDTOs) {
//				Product product = productServie.findProductById(cartDTO.getProductId());
//				ProdOrderItem ordItem = new ProdOrderItem(cartDTO, product, order);
//				order.getProdOrderItems().add(ordItem);
//			}
//		}
//		
//		if (courseCartDTOs != null) {
//			order.setCourseOrderItems(new ArrayList<>());
//			for (CourseCartDTO cartDTO : courseCartDTOs) {
//				Course course = courseService.findById(cartDTO.getCourseId());
//				CourseOrderItem ordItem = new CourseOrderItem(cartDTO, course, order);
//				order.getCourseOrderItems().add(ordItem);
//			}
//		}
//		
//		if (rsvCartDTOs != null) {
//			order.setReservations(new ArrayList<>());
//			for (ReservationCartDTO cartDTO : rsvCartDTOs) {
//				// 查此教室某天某時段是否已經預約
//				Reservation rsv = reservationService.getByRoomIdWithDateAndTime(
//						cartDTO.getClassroom().getId(), cartDTO.getReservationDate(), cartDTO.getReservationTime());
//				if (rsv != null) {
//					String room = rsv.getClassroom().getRoomName();
//					String date = rsv.getReservationDate().toString();
//					String time = reservationService.timeMap(rsv.getReservationTime());
//					return room + "-" + date + "-" + time + " 已被預約";
//				}
//				
//				rsv = new Reservation(cartDTO, order);
//				order.getReservations().add(rsv);
//			}
//		}
//		
//		LocalDateTime currentDateTime = LocalDateTime.now();
//		order.setOrdDate(currentDateTime);
//		order.setUpdateDate(currentDateTime);
//		order.setOrdStatus("訂單成立");
//		
//		orderService.insert(order);
//		
//		
//
//		return "訂單新增成功";
//	}
	
//	@PostMapping("/order")
//	public String test(@RequestBody OrderItemsDTO orderItems) {
//		System.out.println(orderItems);
//		return null;
//	}
//	
	@PostMapping("/order")
	public String test(@RequestBody Order order) {
		System.out.println(order);
		return null;
	}
	
	
	

	
}
