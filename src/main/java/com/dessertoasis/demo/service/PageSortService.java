package com.dessertoasis.demo.service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.recipe.Ingredient;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class PageSortService {

	@PersistenceContext
	private EntityManager em;

	// 確認class中有沒有該屬性
	public boolean hasProperty(Object bean, String propertyName) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (propertyDescriptor.getName().equals(propertyName))
					return true;
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*-----------------------------------------v v v 範例 v v v---------------------------------------------------*/
	// 訂單後台查詢條件
	public Predicate checkCondition(Root<Order> root, Join<Order, Member> join, Predicate predicate,
			SortCondition sortCon, CriteriaBuilder cb, Order order) {
		// 模糊搜索
		if (sortCon.getSearchRules() != null && sortCon.getSearchRules().size() != 0) {
			System.out.println("search");
			for (SearchRules rule : sortCon.getSearchRules()) {
				if (hasProperty(order, rule.getKey())) {
					predicate = cb.and(predicate, cb.like(root.get(rule.getKey()), "%" + rule.getInput() + "%"));
				} else {
					predicate = cb.and(predicate, cb.like(join.get(rule.getKey()), "%" + rule.getInput() + "%"));
				}
			}
		}
		// 日期範圍
		if (sortCon.getDateRules() != null && sortCon.getDateRules().size() != 0) {
			for (DateRules rule : sortCon.getDateRules()) {
				System.out.println(rule.getKey());
				if (hasProperty(order, rule.getKey())) {
					predicate = cb.and(predicate, cb.between(root.get(rule.getKey()), rule.getStart(), rule.getEnd()));
				} else {
					predicate = cb.and(predicate, cb.between(join.get(rule.getKey()), rule.getStart(), rule.getEnd()));
				}
			}
		}
		// 數值範圍
		if (sortCon.getNumKey() != null) {
			System.out.println("num");
			if (hasProperty(order, sortCon.getNumKey())) {
				predicate = cb.and(predicate,
						cb.between(root.get(sortCon.getNumKey()), sortCon.getNumStart(), sortCon.getNumEnd()));
			} else {
				predicate = cb.and(predicate,
						cb.between(join.get(sortCon.getNumKey()), sortCon.getNumStart(), sortCon.getNumEnd()));
			}
		}
		return predicate;
	}
	/*-----------------------------------------＾＾＾範例＾＾＾---------------------------------------------------*/

	/*-----------------------------------------食譜測試---------------------------------------------------*/
	public Predicate checkRecipeCondition(Root<Recipes> root, Join<Recipes, Member> join, Predicate predicate,
			SortCondition sortCon, CriteriaBuilder cb, Recipes recipes) {
		// 模糊搜索
		if (sortCon.getSearchRules() != null && sortCon.getSearchRules().size() != 0) {
			System.out.println("search");
			for (SearchRules rule : sortCon.getSearchRules()) {
				if (hasProperty(recipes, rule.getKey())) {
					predicate = cb.and(predicate, cb.like(root.get(rule.getKey()), "%" + rule.getInput() + "%"));
				} else {
					predicate = cb.and(predicate, cb.like(join.get(rule.getKey()), "%" + rule.getInput() + "%"));
				}
			}
		}
		// 日期範圍
		if (sortCon.getDateRules() != null && sortCon.getDateRules().size() != 0) {
			for (DateRules rule : sortCon.getDateRules()) {
				System.out.println(rule.getKey());
				if (hasProperty(recipes, rule.getKey())) {
					predicate = cb.and(predicate, cb.between(root.get(rule.getKey()), rule.getStart(), rule.getEnd()));
				} else {
					predicate = cb.and(predicate, cb.between(join.get(rule.getKey()), rule.getStart(), rule.getEnd()));
				}
			}
		}
		// 數值範圍
		if (sortCon.getNumKey() != null) {
			System.out.println("num");
			if (hasProperty(recipes, sortCon.getNumKey())) {
				predicate = cb.and(predicate,
						cb.between(root.get(sortCon.getNumKey()), sortCon.getNumStart(), sortCon.getNumEnd()));
			} else {
				predicate = cb.and(predicate,
						cb.between(join.get(sortCon.getNumKey()), sortCon.getNumStart(), sortCon.getNumEnd()));
			}
		}
		return predicate;
	}

	// (棄用)
	public String getPageJson(SortCondition sortCod) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> root = cq.from(Member.class);
		Predicate[] predicates = new Predicate[20];
		int counter = 0;
		if (sortCod.getSortBy() != null) {
			predicates[counter] = cb.like(root.get("itemName"), "%" + sortCod.getSortBy() + "%");
			counter++;
		}
		CriteriaQuery<Member> select = cq.select(root).where(predicates);
		TypedQuery<Member> query = em.createQuery(select);
		query.setFirstResult((sortCod.getPage() - 1) * sortCod.getPageSize());
		query.setMaxResults(sortCod.getPageSize());
		List<Member> list = query.getResultList();
		System.out.println(list);

		return null;
	}

}
