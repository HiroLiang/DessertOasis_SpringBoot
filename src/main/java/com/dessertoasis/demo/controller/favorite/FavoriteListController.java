package com.dessertoasis.demo.controller.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.favorite.FavoriteList;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.service.favorite.FavoriteListService;

import jakarta.servlet.http.HttpSession;

@RestController
public class FavoriteListController {
	
	@Autowired
	private FavoriteListService favorService;
	
	@GetMapping("/user-favorite-list")
	public List<FavoriteList> getMemberFavor(HttpSession session){
		Member user = (Member) session.getAttribute("loggedInMember");
		List<FavoriteList> resultList = favorService.getListByUserId(user.getId());
		if(!resultList.equals(null)) 
			return resultList;
		return null;
	}

}
