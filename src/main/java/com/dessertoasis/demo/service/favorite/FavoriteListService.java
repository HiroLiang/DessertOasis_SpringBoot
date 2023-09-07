package com.dessertoasis.demo.service.favorite;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.favorite.FavoriteList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class FavoriteListService {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<FavoriteList> getListByUserId(Integer memberId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FavoriteList> cq = cb.createQuery(FavoriteList.class);
		Root<FavoriteList> root = cq.from(FavoriteList.class);
		cq.where(cb.equal(root.get("memberId"), memberId));
		cq.orderBy(cb.asc(root.get("categoryId")));
		List<FavoriteList> resultList = em.createQuery(cq).getResultList();
		if(resultList != null) {
			return resultList;
		}
		return null;
	}

}
