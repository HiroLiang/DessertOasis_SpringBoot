package com.dessertoasis.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.product.ProdSearchDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductRepository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
@Service
public class ProductService {

    @Autowired
    private ProductRepository prodRepo;

    public Product findProductById(Integer id) {
        Optional<Product> optional = prodRepo.findById(id);
        return optional.orElse(null);
    }

    public List<Product> findAllProduct() {
        return prodRepo.findAll();
    }
    

    public void insert(Product product) {
    	prodRepo.save(product);
    }

    public void update(Product product) {
    	prodRepo.save(product);
    }

    public boolean deleteProductById(Integer id) {
        Optional<Product> optional = prodRepo.findById(id);
        if (optional.isPresent()) {
        	prodRepo.deleteById(id);
            return true;
        }
        return false;
    }
   
    public Page<Product> searchProducts(ProdSearchDTO criteria, Pageable pageable) {
        return prodRepo.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getProdName() != null) {
                predicates.add(builder.like(root.get("prodName"), "%" + criteria.getProdName() + "%"));
            }
            
            if (criteria.getCategory() != null) {
                predicates.add(builder.like(root.get("category"), "%" + criteria.getCategory() + "%"));
            }
            
            if (criteria.getProdPrice() != null) {
                predicates.add(builder.like(root.get("prodPrice"), "%" + criteria.getProdPrice() + "%"));
            }
            
            if (criteria.getProdPurchase() != null) {
                predicates.add(builder.like(root.get("prodPurchase"), "%" + criteria.getProdPurchase() + "%"));
            }
            
            if (criteria.getProdStock() != null) {
                predicates.add(builder.like(root.get("prodStock"), "%" + criteria.getProdStock() + "%"));
            }
            
            if (criteria.getProductStatus() != null) {
                predicates.add(builder.like(root.get("productStatus"), "%" + criteria.getProductStatus() + "%"));
            }
            
            if (criteria.getUpdateTime() != null) {
                predicates.add(builder.like(root.get("updateTime"), "%" + criteria.getUpdateTime() + "%"));
            }
            
            if (criteria.getSaleAfterUpdate() != null) {
                predicates.add(builder.like(root.get("saleAfterUpdate"), "%" + criteria.getSaleAfterUpdate() + "%"));
            }
            
            if (criteria.getProdRemark() != null) {
                predicates.add(builder.like(root.get("prodRemark"), "%" + criteria.getProdRemark() + "%"));
            }
           

            if (criteria.getMinprodPrice() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("prodPrice"), criteria.getMinprodPrice()));
            }

            if (criteria.getMaxprodPrice() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("prodPrice"), criteria.getMaxprodPrice()));
            }

            

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}