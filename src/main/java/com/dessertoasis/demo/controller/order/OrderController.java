package com.dessertoasis.demo.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.CartToOrderDTO;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.order.EcpayDTO;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.order.OrderDTO;
import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.order.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	// 取得單一訂單
	@GetMapping("/order/{ordId}")
	public OrderDTO getOrderById(@PathVariable("ordId") Integer ordId) {
		Order order = orderService.getByOrdId(ordId);
		OrderDTO orderDto = new OrderDTO(order);
		return orderDto;
	}

	// 修改訂單狀態
	@PatchMapping("/order/{ordId}")
	public Order updateOrdStatus(@PathVariable("ordId") Integer ordId, @RequestParam("ordStatus") String ordStatus) {
		Order order = orderService.updateOrdStatus(ordId, ordStatus);
		return order;
	}

	// 刪除訂單
	@DeleteMapping("/order/{ordId}")
	public void deleteOrderById(@PathVariable("ordId") Integer ordId) {
		orderService.deleteByOrdId(ordId);
	}

	// 取得會員的訂單
	@GetMapping("/order/member/page/{pageNum}")
	public Map<String, Object> getOrdersByMember(@PathVariable("pageNum") Integer pageNum, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");

		Integer pageSize = 5;
		Page<Order> page = orderService.getPageByMemberId(member.getId(), pageNum, pageSize, Sort.Direction.DESC,
				"ordDate");
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for (Order order : page.getContent()) {
			orderDTOs.add(new OrderDTO(order));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("totalPages", page.getTotalPages());
		response.put("orders", orderDTOs);
		return response;
	}

	// 新增訂單
	@PostMapping("/order")
	public Integer insertOrder(@RequestBody CartToOrderDTO cartToOrderDTO, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		if (member == null)
			return null;
		
		return orderService.insert(cartToOrderDTO, member.getId());
	}

	// 取出會員的所有預約
	@GetMapping("/order/reservation")
	public List<Reservation> getReservatedClassroom(HttpSession session) {
		Member user = (Member) session.getAttribute("loggedInMember");
		return orderService.getReservationsByMemberId(user.getId());
	}

	/*-----------------------------------------v v v 範例 v v v---------------------------------------------------*/
	// 訂單分頁查詢
	@PostMapping("/order/pagenation")
	public List<OrderCmsTable> getOrderPage(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
		Member user = (Member) session.getAttribute("loggedInMember");
		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
			return null;
		}
		// 送出查詢條件給service，若有結果則回傳list
		List<OrderCmsTable> result = orderService.getOrderPagenation(sortCon);
		if (result != null) {
			System.out.println(result);
			return result;
		}
		return null;
	}

	@PostMapping("/order/pages")
	public Integer getPages(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
		Member user = (Member) session.getAttribute("loggedInMember");
		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
			return null;
		}
		// 送出條件查詢總頁數
		Integer pages = orderService.getPages(sortCon);
		return pages;
	}
	/*-----------------------------------------＾＾＾範例＾＾＾---------------------------------------------------*/
	
	//ECPAY CONTROLLER
	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout(@RequestBody EcpayDTO ecpayDto) {
		String aioCheckOutALLForm = orderService.ecpayCheckout(ecpayDto);
		
		return aioCheckOutALLForm;
	}
}
