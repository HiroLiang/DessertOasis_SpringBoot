package com.dessertoasis.demo.service.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.cart.Cart;
import com.dessertoasis.demo.model.cart.CartDTO;
import com.dessertoasis.demo.model.cart.CartRepository;
import com.dessertoasis.demo.model.cart.CourseCartDTO;
import com.dessertoasis.demo.model.cart.ProductCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCart;
import com.dessertoasis.demo.model.cart.ReservationCartDTO;
import com.dessertoasis.demo.model.cart.ReservationCartRepository;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;
import com.dessertoasis.demo.model.classroom.Classroom;
import com.dessertoasis.demo.model.classroom.ClassroomRepository;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductRepository;

@Service
public class CartService {
	
	private Integer productCategoryId = 1;
	private Integer courseCategoryId = 2;
	private Integer reservationCategoryId = 4;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private ReservationCartRepository rsvCartRepo;
	
	@Autowired
	private ClassroomRepository roomRepo;
	
	public Integer getCountByMemberId(Integer memberId) {
		Member member = memberRepo.findById(memberId).get();
		return member.getCarts().size();
	}
	
	public List<ProductCartDTO> getProductCartDTOs(Integer memberId){
		Member member = memberRepo.findById(memberId).get();
		List<Cart> cartList = member.getCarts();
		List<ProductCartDTO> productCartDTOs = new ArrayList<>();
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == productCategoryId) {
				Product product = productRepo.findById(cart.getInterestedId()).get();
				ProductCartDTO productCartDTO = new ProductCartDTO(cart, product);
				productCartDTOs.add(productCartDTO);
			}
		}
		return productCartDTOs;
	}
	
	public List<CourseCartDTO> getCourseCartDTOs(Integer memberId){
		Member member = memberRepo.findById(memberId).get();
		List<Cart> cartList = member.getCarts();
		List<CourseCartDTO> courseCartDTOs = new ArrayList<>();
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == courseCategoryId) {
				Course course = courseRepo.findById(cart.getInterestedId()).get();
				CourseCartDTO courseCartDTO = new CourseCartDTO(cart, course);
				courseCartDTOs.add(courseCartDTO);
			}
		}
		return courseCartDTOs;
	}
	
	public List<ReservationCartDTO> getReservationCartDTOs(Integer memberId){
		Member member = memberRepo.findById(memberId).get();
		List<Cart> cartList = member.getCarts();
		List<ReservationCartDTO> rsvCartDTOs = new ArrayList<>();
		for (Cart cart : cartList) {
			if (cart.getCategoryId() == reservationCategoryId) {
				ReservationCart rsvCart = rsvCartRepo.findById(cart.getInterestedId()).get();
				ReservationCartDTO rsvCartDTO = new ReservationCartDTO(cart, rsvCart);
				rsvCartDTOs.add(rsvCartDTO);
			}
		}
		return rsvCartDTOs;
	}
	
	public Cart insert(CartDTO cartDTO, Integer memberId) {
		Member member = memberRepo.findById(memberId).get();
		
		// 調整 categoryId 為最上層之 categoryId
		Integer categoryId = cartDTO.getCategoryId();
		Category category = categoryRepo.findById(categoryId).get();
		while (category.getParent() != null) {
			category = category.getParent();
		}
		cartDTO.setCategoryId(category.getId());
		
		// 若購物車內已經有此商品，更新商品 quantity
		if (cartDTO.getCategoryId() == productCategoryId &&
				cartRepo.findByMemberAndCategoryIdAndInterestedId(member, productCategoryId, cartDTO.getInterestedId()) != null) {
			
			Integer prodQuantity = cartDTO.getProdQuantity();
			Cart prodCart = cartRepo.findByMemberAndCategoryIdAndInterestedId(member, productCategoryId, cartDTO.getInterestedId());
			prodQuantity += prodCart.getProdQuantity();
			return this.updateProdQuantity(prodCart, prodQuantity);
		}
		
		// 加入預約教室購物車
		if (cartDTO.getCategoryId() == reservationCategoryId) {
			Classroom room = roomRepo.findById(cartDTO.getRoomId()).get();
			ReservationCart rsvCart = new ReservationCart(cartDTO, room);
			rsvCart = rsvCartRepo.save(rsvCart);
			cartDTO.setInterestedId(rsvCart.getId());
		}
		
		Cart cart = new Cart(cartDTO, member);
		
		return cartRepo.save(cart);
	}	
	
	public Cart updateProdQuantity(Cart cart, Integer prodQuantity) {
		if (cart.getCategoryId() != productCategoryId)
			return null;
		cart.setProdQuantity(prodQuantity);
		cart = cartRepo.save(cart);
		return cart;
	}
	
	public Cart updateProdQuantity(Integer cartId, Integer prodQuantity) {
		Cart cart = cartRepo.findById(cartId).orElse(null);
		if (cart == null || cart.getCategoryId() != productCategoryId) {
			return null;
		}
		cart.setProdQuantity(prodQuantity);
		return cartRepo.save(cart);
	}
	
	public void deleteCart(Integer cartId) {
		Cart cart = cartRepo.findById(cartId).get();
		if (cart.getCategoryId() == reservationCategoryId) {
			rsvCartRepo.deleteById(cart.getInterestedId());
		}
		
		cartRepo.deleteById(cartId);
	}
	
	public void deleteCarts(List<Integer> cartIds) {
		for (Integer cartId : cartIds) {
			this.deleteCart(cartId);
		}
	}
}
