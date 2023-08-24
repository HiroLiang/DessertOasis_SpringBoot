package com.dessertoasis.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductRepository;

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
}