package com.dessertoasis.demo.service.product;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.product.ProdSearchDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductRepository;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

@Service
public class ProdPageService {
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
    
 // 訂單後台查詢條件
 	public Predicate checkCondition(Root<Product> root, Join<Product,Category> join, Predicate predicate,
 			SortCondition sortCon, CriteriaBuilder cb, Product product) {
 		// 模糊搜索
 		if (sortCon.getSearchRules() != null && sortCon.getSearchRules().size() != 0) {
 			System.out.println("search");
 			for (SearchRules rule : sortCon.getSearchRules()) {
 				if (hasProperty(product, rule.getKey())) {
					predicate = cb.and(predicate, cb.like(root.get(rule.getKey()), "%"+rule.getInput()+"%"));
				} else {
					predicate = cb.and(predicate, cb.like(join.get(rule.getKey()), "%"+rule.getInput()+"%"));
				}
			}
 		}
 		// 日期範圍
 		if (sortCon.getDateRules() != null && sortCon.getDateRules().size() != 0) {
 			for (DateRules rule : sortCon.getDateRules()) {
 				System.out.println(rule.getKey());
 				if (hasProperty(product, rule.getKey())) {
 					predicate = cb.and(predicate, cb.between(root.get(rule.getKey()), rule.getStart(), rule.getEnd()));
 				} else {
 					predicate = cb.and(predicate, cb.between(join.get(rule.getKey()), rule.getStart(), rule.getEnd()));
 				}
 			}
 		}
 		// 數值範圍
 		if (sortCon.getNumKey() != null) {
 			System.out.println("num");
 			if (hasProperty(product, sortCon.getNumKey())) {
 				predicate = cb.and(predicate,
 						cb.between(root.get(sortCon.getNumKey()), sortCon.getNumStart(), sortCon.getNumEnd()));
 			} else {
 				predicate = cb.and(predicate,
 						cb.between(join.get(sortCon.getNumKey()), sortCon.getNumStart(), sortCon.getNumEnd()));
 			}
 		}
 		return predicate;
 	}
 	
 	
 	
 	

	
}