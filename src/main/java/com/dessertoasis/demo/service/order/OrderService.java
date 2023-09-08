package com.dessertoasis.demo.service.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.cart.CourseCartDTO;
import com.dessertoasis.demo.model.cart.ProductCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCartDTO;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.order.CourseOrderItem;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderRepository;
import com.dessertoasis.demo.model.order.ProdOrderItem;
import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.model.order.ReservationRepository;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private ReservationRepository rsvRepo;
	
	@Autowired
	private ReservationService rsvService;
	
	public Order getByOrdId(Integer ordId) {
		return orderRepo.findById(ordId).orElse(null);
	}
	
	public Page<Order> getPage(Integer pageNum, Integer pageSize, Sort.Direction sort, String prop) {
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sort, prop);
		Page<Order> page= orderRepo.findAll(pageRequest);
		return page;
	}
	
	public Page<Order> getPageByMemberId(Integer memberId, Integer pageNum, Integer pageSize, Sort.Direction sort, String prop) {
		Member member = memberRepo.findById(memberId).get();
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sort, prop);
		Page<Order> page = orderRepo.findByMember(member, pageRequest);
		return page;
	}
	
	// 新增訂單
	public Order insert(Order order, Integer memberId) {
		order.setMember(memberRepo.findById(memberId).get());
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		order.setOrdDate(currentDateTime);
		order.setUpdateDate(currentDateTime);
		order.setOrdStatus("訂單成立");
		return orderRepo.save(order);
	}

	
	public Order placeProdOrderItem(Order order, List<ProductCartDTO> productCartDTOs) {
		if (productCartDTOs != null) {
			List<ProdOrderItem> orderItems = new ArrayList<>(); 
			for (ProductCartDTO cartDTO : productCartDTOs) {
				Product product = productRepo.findById(cartDTO.getProductId()).get();
				ProdOrderItem ordItem = new ProdOrderItem(cartDTO, product, order);
				orderItems.add(ordItem);
			}
			order.setProdOrderItems(orderItems);
		}
		return order;
	}
	
	public Order placeCourseOrderItem(Order order, List<CourseCartDTO> courseCartDTOs) {
		if (courseCartDTOs != null) {
			List<CourseOrderItem> orderItems = new ArrayList<>();
			for (CourseCartDTO cartDTO : courseCartDTOs) {
				Course course = courseRepo.findById(cartDTO.getCourseId()).get();
				CourseOrderItem ordItem = new CourseOrderItem(cartDTO, course, order);
				orderItems.add(ordItem);
			}
			order.setCourseOrderItems(orderItems);
		}
		return order;
	}
	
	public Order placeReservation(Order order, List<ReservationCartDTO> rsvCartDTOs) {
		if (rsvCartDTOs != null) {
			List<Reservation> reservations = new ArrayList<>();
			for (ReservationCartDTO cartDTO : rsvCartDTOs) {
				Reservation rsv = new Reservation(cartDTO, order);
				reservations.add(rsv);
			}
			order.setReservations(reservations);
		}
		return order;
	}
	
	
	// 查預約是否已經存在 (教室某天某時段已經被預約)
	public String checkReservation(List<ReservationCartDTO> rsvCartDTOs) {
		if (rsvCartDTOs != null) {
			for (ReservationCartDTO cartDTO : rsvCartDTOs) {
				Reservation rsv = rsvRepo.findByDateAndTime(
						cartDTO.getClassroom().getId(), cartDTO.getReservationDate(), cartDTO.getReservationTime());
				if (rsv != null) {
					String room = rsv.getClassroom().getRoomName();
					String date = rsv.getReservationDate().toString();
					String time = rsvService.timeMap(rsv.getReservationTime());
					return room + "-" + date + "-" + time + " 已被預約";
				}
			}
		}
		return null;
	}
	
}
