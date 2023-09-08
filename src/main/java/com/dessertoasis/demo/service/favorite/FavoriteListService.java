package com.dessertoasis.demo.service.favorite;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.favorite.FavoriteKey;
import com.dessertoasis.demo.model.favorite.FavoriteList;
import com.dessertoasis.demo.model.favorite.FavoriteListRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

@Service
public class FavoriteListService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private FavoriteListRepository favorListRpo;
	
	public List<FavoriteKey> getListByUserId(Integer memberId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FavoriteKey> cq = cb.createQuery(FavoriteKey.class);
		Root<FavoriteList> root = cq.from(FavoriteList.class);
		Join<FavoriteList, FavoriteKey> join = root.join("favoriteKey");
		cq.multiselect(join.get("memberId"),join.get("categoryId"),join.get("itemId"));
		cq.where(cb.equal(join.get("memberId"), memberId));
		cq.orderBy(cb.asc(join.get("categoryId")));
		List<FavoriteKey> resultList = em.createQuery(cq).getResultList();
		if(resultList != null) {
			return resultList;
		}
		return null;
	}

	public FavoriteList findById(FavoriteKey id) {
		Optional<FavoriteList> result = favorListRpo.findById(id);
		if(result.isPresent()) {
			return result.get();	
		}else{
			return null;
		}
	}
	
	public void insertList(FavoriteList list) {
		favorListRpo.save(list);
	}
	
	public void deleteList(FavoriteKey id) {
		try {
			favorListRpo.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
