package com.dessertoasis.demo.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.product.ProdSearchDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductPicture;
import com.dessertoasis.demo.model.product.ProductPictureRepository;
import com.dessertoasis.demo.model.product.ProductRepository;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Service
public class ProductPictureService {


    @Autowired
    private ProductPictureRepository ppRepo;

    public List<ProductPicture> findProductPicturesByProductId(Integer productId) {
        return ppRepo.findProductPictureByProductId(productId);
    }}