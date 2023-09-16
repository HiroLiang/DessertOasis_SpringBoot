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
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.order.OrderRepository;
import com.dessertoasis.demo.model.order.ProdOrderItem;
import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.model.order.ReservationRepository;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductRepository;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class OrderService {

	@PersistenceContext
	private EntityManager em;

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

	@Autowired
	private PageSortService pService;

	public Order getByOrdId(Integer ordId) {
		return orderRepo.findById(ordId).orElse(null);
	}

	public Page<Order> getPage(Integer pageNum, Integer pageSize, Sort.Direction sort, String prop) {
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sort, prop);
		Page<Order> page = orderRepo.findAll(pageRequest);
		return page;
	}

	public Page<Order> getPageByMemberId(Integer memberId, Integer pageNum, Integer pageSize, Sort.Direction sort,
			String prop) {
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
				Reservation rsv = rsvRepo.findByDateAndTime(cartDTO.getClassroom().getId(),
						cartDTO.getReservationDate(), cartDTO.getReservationTime());
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
	
	// 取出會員預約的教室
	public List<Reservation> getReservationsByMemberId(Integer memberId) {
		Member member = memberRepo.findById(memberId).get();
		List<Order> orders = member.getOrders();
		
		List<Reservation> reservations = new ArrayList<>();
		for (Order order : orders) {
			if (order.getReservations() != null) {
				reservations.addAll(order.getReservations());
			}
		}
		
		return reservations;
	}
	
	/*-----------------------------------------v v v 範例 v v v---------------------------------------------------*/
	// Order table範例
	public List<OrderCmsTable> getOrderPagenation(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<OrderCmsTable> cq = cb.createQuery(OrderCmsTable.class);

		// 決定select.join表格
		Root<Order> root = cq.from(Order.class);
		Join<Order, Member> join = root.join("member");

		// 決定查詢 column
		cq.multiselect(root.get("id"), join.get("fullName"), root.get("ordDate"), root.get("updateDate"),
				root.get("ordStatus"));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Order order = new Order();
		Predicate pre = pService.checkCondition(root, join, predicate, sortCon, cb, order);
		
		// 填入 where 條件
		cq.where(pre);

		// 排序條件
		if (sortCon.getSortBy() != null) {
			System.out.println("sort");
			if (pService.hasProperty(order, sortCon.getSortBy())) {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(root.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(root.get(sortCon.getSortBy())));
				}
			} else {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(join.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(join.get(sortCon.getSortBy())));
				}
			}
		}

		// 分頁
		TypedQuery<OrderCmsTable> query = em.createQuery(cq);
		query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
		query.setMaxResults(sortCon.getPageSize());

		// 送出請求
		List<OrderCmsTable> list = query.getResultList();
		if (list != null) 
			return list;
		return null;
	}

	public Integer getPages(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		// 決定select.join表格
		Root<Order> root = cq.from(Order.class);
		Join<Order, Member> join = root.join("member");

		// 決定查詢 column
		cq.select(cb.count(root));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Order order = new Order();
		Predicate pre = pService.checkCondition(root, join, predicate, sortCon, cb, order);
		cq.where(pre);
		
		//查詢傯頁數
		Long totalRecords = em.createQuery(cq).getSingleResult();
		Integer totalPages = (int) Math.ceil((double) totalRecords / sortCon.getPageSize());
		
		return totalPages;
	}
	/*-----------------------------------------＾＾＾範例＾＾＾---------------------------------------------------*/
}
