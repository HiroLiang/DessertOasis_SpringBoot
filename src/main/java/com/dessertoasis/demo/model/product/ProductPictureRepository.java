package com.dessertoasis.demo.model.product;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPicture, Integer> {

	List<ProductPicture> findProductPictureByProductId(Integer productId);

	//List<ProductPicture> findProductPictureByProductId(Integer productId);

	//List<ProductPicture> findByproductId(Integer productId);
//    List<ProductPicture> findByProductId(Integer productId);
	//public List<ProductPicture> findByProductId(Integer productId);
}