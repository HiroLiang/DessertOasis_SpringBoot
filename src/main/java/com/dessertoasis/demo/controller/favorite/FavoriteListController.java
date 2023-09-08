package com.dessertoasis.demo.controller.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.favorite.FavoriteKey;
import com.dessertoasis.demo.model.favorite.FavoriteList;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.service.favorite.FavoriteListService;

import jakarta.servlet.http.HttpSession;

@RestController
public class FavoriteListController {
	
	@Autowired
	private FavoriteListService favorService;
	
	@GetMapping("/user-favorite-list")
	public List<FavoriteKey> getMemberFavor(HttpSession session){
		Member user = (Member) session.getAttribute("loggedInMember");
		if(user != null) {
			List<FavoriteKey> resultList = favorService.getListByUserId(user.getId());
			if(!resultList.equals(null)) 
				return resultList;			
		}
		return null;
	}
	
	@PostMapping("/favorite-list/updateList")
	public void updateList(@RequestBody FavoriteKey key,HttpSession session) {
		Member user = (Member) session.getAttribute("loggedInMember");
		System.out.println(key);
		if(user != null) {
			key.setMemberId(user.getId());
			FavoriteList oldList = favorService.findById(key);
			if(oldList != null) {
				favorService.deleteList(key);
			}else {
				FavoriteList list = new FavoriteList(key);
				favorService.insertList(list);
			}
		}
	}

}
