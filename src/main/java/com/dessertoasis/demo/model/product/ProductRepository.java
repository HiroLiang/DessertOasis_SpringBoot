package com.dessertoasis.demo.model.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {

	
//    @Query("SELECT p FROM Product p JOIN FETCH p.category")
//    List<Product> findAllWithCategory();
//
//    @EntityGraph(attributePaths = "category")
//    List<Product> findAll();
	
	Page<Product> findAll(Pageable pageable);

	   
	}
	

