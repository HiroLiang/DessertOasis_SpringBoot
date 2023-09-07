package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.product.ProdSearchDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService pService;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = pService.findAllProduct();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<Product> productDetails(@PathVariable Integer id) {
        Product product = pService.findProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        pService.insert(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Integer id, @RequestBody Product product) {
        Product existingProduct = pService.findProductById(id);
        if (existingProduct != null) {
            pService.update(product); // Use the provided product object
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        pService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
  
    @PostMapping("/criteria")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestBody ProdSearchDTO criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortBy));

        Page<Product> products = pService.searchProducts(criteria, pageable);
        return ResponseEntity.ok(products);
    }
    	    
    
    @GetMapping("/search")
    
    public ResponseEntity<Page<Product>> searchProducts(
            ProdSearchDTO criteria,
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        Sort sort = Sort.unsorted();

        if (sortBy != null && !sortBy.isEmpty()) {
            String[] sortParams = sortBy.split("&");
            for (String param : sortParams) {
                String[] sortField = param.split(",");
                if (sortField.length == 2) {
                    String field = sortField[0];
                    String direction = sortField[1].toUpperCase(); // Ensure direction is uppercase
                    Sort.Order order = "ASC".equals(direction) ? Sort.Order.asc(field) : Sort.Order.desc(field);
                    sort = sort.and(Sort.by(order));
                }
            }
        }

        int adjustedPage = pageable.getPageNumber() - 1;
        int effectivePageSize = pageSize != null ? pageSize : 20;

        Page<Product> products = pService.searchProducts(criteria, PageRequest.of(adjustedPage, effectivePageSize, sort));
        return ResponseEntity.ok(products);
    }}