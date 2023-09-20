package com.dessertoasis.demo.model.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {

	
//    @Query("SELECT p FROM Product p JOIN FETCH p.category")
//    List<Product> findAllWithCategory();
//
//    @EntityGraph(attributePaths = "category")
//    List<Product> findAll();
	
	Page<Product> findAll(Pageable pageable);

	@Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :productId")
    void deleteProductAndRelatedPictures(@Param("productId") Integer productId);
	
	}
	

