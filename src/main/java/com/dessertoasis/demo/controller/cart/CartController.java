package com.dessertoasis.demo.controller.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.cart.Cart;
import com.dessertoasis.demo.model.cart.CartDTO;
import com.dessertoasis.demo.model.cart.CourseCartDTO;
import com.dessertoasis.demo.model.cart.ProductCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCart;
import com.dessertoasis.demo.model.cart.ReservationCartDTO;
import com.dessertoasis.demo.model.classroom.Classroom;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.MemberService;
import com.dessertoasis.demo.service.ProductService;
import com.dessertoasis.demo.service.cart.CartService;
import com.dessertoasis.demo.service.cart.ReservationCartService;
import com.dessertoasis.demo.service.classroom.ClassroomService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CartController {
	
	private Integer productCategoryId = 1;
	private Integer courseCategoryId = 2;
	private Integer reservationCategoryId = 4;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ReservationCartService rsvCartService;
	
	@Autowired
	private ClassroomService roomService;
	
	// 取出會員之購物車的所有商品
	@GetMapping("/cart/product")
	public List<ProductCartDTO> getProductCart(HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		List<Cart> cartList = cartService.findByMemberId(member.getId());
		List<ProductCartDTO> productCart = new ArrayList<>();
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == productCategoryId) {
				Product product = productService.findProductById(cart.getInterestedId());
				ProductCartDTO productCartItem = new ProductCartDTO(cart, product);
				productCart.add(productCartItem);
			}
		}
		return productCart;
	}
	
	// 取出會員之購物車的所有課程
	@GetMapping("/cart/course")
	public List<CourseCartDTO> getCourseCart(HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		List<Cart> cartList = cartService.findByMemberId(member.getId());
		List<CourseCartDTO> courseCart = new ArrayList<>();
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == courseCategoryId) {
				Course course = courseService.findById(cart.getInterestedId());
				CourseCartDTO courseCartItem = new CourseCartDTO(cart, course);
				courseCart.add(courseCartItem);
			}
		}
		return courseCart;
	}
	
	// 取出會員之購物車的所有教室預約
	@GetMapping("/cart/reservation")
	public List<ReservationCartDTO> getReservationCart(HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		List<Cart> cartList = cartService.findByMemberId(member.getId());
		List<ReservationCartDTO> rsvCart = new ArrayList<>();
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == reservationCategoryId) {
				ReservationCart rc = rsvCartService.findById(cart.getInterestedId());
				ReservationCartDTO rsvCartItem = new ReservationCartDTO(cart, rc);
				rsvCart.add(rsvCartItem);
			}
		}
		return rsvCart;
	}
	
	// 加入購物車
	@PostMapping("/cart")
	public String addToCart(@RequestBody CartDTO cartDTO, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		if (member == null) {
			return "加入購物車失敗，沒有此會員";
		}
		member = memberService.findByMemberId(member.getId());
		
		// 加入預約教室購物車
		if (cartDTO.getCategoryId() == reservationCategoryId) {
			Classroom room = roomService.findById(cartDTO.getRoomId());
			ReservationCart rsvCart = new ReservationCart(cartDTO, room);
			rsvCart = rsvCartService.insert(rsvCart);
			cartDTO.setInterestedId(rsvCart.getId());
		}
		
		Cart cart = new Cart(cartDTO, member);		
		cartService.insert(cart);
		
		return "加入購物車成功";
	}
	
	// 刪除購物車項目
	@DeleteMapping("/cart/{cartId}")
	public void deleteCart(@PathVariable("cartId") Integer cartId) {
		Cart cart = cartService.findByCartId(cartId);
		if (cart != null && cart.getCategoryId() == reservationCategoryId) {
			rsvCartService.deleteById(cart.getInterestedId());
		}
		cartService.deleteById(cartId);
	}
	
	
	
	
	
	
	
	
	
}
