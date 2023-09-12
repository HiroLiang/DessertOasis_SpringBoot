package com.dessertoasis.demo.service.product;

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
import com.dessertoasis.demo.model.product.ProductPicture;
import com.dessertoasis.demo.model.product.ProductRepository;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;

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
public class ProductService {
	@PersistenceContext
	private EntityManager em;
	
    @Autowired
    private ProductRepository prodRepo;
 
//    @Autowired
//	private ProdPageService ppService;
    
    @Autowired
	private PageSortService ppService;
    
    public Product findProductById(Integer id) {
        Optional<Product> optional = prodRepo.findById(id);
        return optional.orElse(null);
    }

    public List<Product> findAllProduct() {
        return prodRepo.findAll();
    }
    

    public Product insert(Product product) {
        return prodRepo.save(product);
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
            
            if (criteria.getCategoryName() != null) {
                predicates.add(builder.like(root.get("categoryName"), "%" + criteria.getCategoryName() + "%"));
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
           
//
//            if (criteria.getMinprodPrice() != null) {
//                predicates.add(builder.greaterThanOrEqualTo(root.get("prodPrice"), criteria.getMinprodPrice()));
//            }
//
//            if (criteria.getMaxprodPrice() != null) {
//                predicates.add(builder.lessThanOrEqualTo(root.get("prodPrice"), criteria.getMaxprodPrice()));
//            }
//            
//
//            if (criteria.getMinprodPurchase() != null) {
//                predicates.add(builder.greaterThanOrEqualTo(root.get("prodPurchase"), criteria.getMinprodPurchase()));
//            }
//
//            if (criteria.getMaxprodPurchase() != null) {
//                predicates.add(builder.lessThanOrEqualTo(root.get("prodPurchase"), criteria.getMaxprodPurchase()));
//            }
//            
//            if (criteria.getMinprodStock() != null) {
//                predicates.add(builder.greaterThanOrEqualTo(root.get("prodStock"), criteria.getMinprodStock()));
//            }
//
//            if (criteria.getMaxprodStock() != null) {
//                predicates.add(builder.lessThanOrEqualTo(root.get("prodStock"), criteria.getMaxprodStock()));
//            }
//            
//            if (criteria.getMinsaleAfterUpdate() != null) {
//                predicates.add(builder.greaterThanOrEqualTo(root.get("saleAfterUpdate"), criteria.getMinsaleAfterUpdate()));
//            }
//
//            if (criteria.getMaxsaleAfterUpdate() != null) {
//                predicates.add(builder.lessThanOrEqualTo(root.get("saleAfterUpdate"), criteria.getMaxsaleAfterUpdate()));
//            }

            

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    
 
 	

	public List<ProdSearchDTO> getProductPagenation(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<ProdSearchDTO> cq = cb.createQuery(ProdSearchDTO.class);

		// 決定select.join表格
		Root<Product> root = cq.from(Product.class);
		Join<Product,Category> join = root.join("category");

		// 決定查詢 column
		cq.multiselect(root.get("id"),join.get("categoryName"),root.get("prodPrice"),root.get("prodPurchase"),root.get("prodRemark"),root.get("prodStock"),root.get("productStatus"),root.get("saleAfterUpdate"),root.get("updateTime"),root.get("prodName"));
		
		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Product product = new Product();
		Predicate pre = ppService.checkCondition(root, join, predicate, sortCon, cb, product);
		
		// 填入 where 條件
		cq.where(pre);

		// 排序條件
		if (sortCon.getSortBy() != null) {
			System.out.println("sort");
			if (ppService.hasProperty(product, sortCon.getSortBy())) {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(root.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(root.get(sortCon.getSortBy())));
				}
			} else {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(join.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(join.get(sortCon.getSortBy())));
				}
			}
		}

		// 分頁
		TypedQuery<ProdSearchDTO> query = em.createQuery(cq);
		query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
		query.setMaxResults(sortCon.getPageSize());

		// 送出請求
		List<ProdSearchDTO> list = query.getResultList();
		if (list != null) 
			return list;
		return null;
	}

	public Integer getPages(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		// 決定select.join表格
		Root<Product> root = cq.from(Product.class);
		Join<Product,Category> join = root.join("category");

		// 決定查詢 column
		cq.select(cb.count(root));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Product product = new Product();
		Predicate pre = ppService.checkCondition(root, join, predicate, sortCon, cb, product);
		cq.where(pre);
		
		//查詢總頁數
		Long totalRecords = em.createQuery(cq).getSingleResult();
		Integer totalPages = (int) Math.ceil((double) totalRecords / sortCon.getPageSize());
		
		return totalPages;
	}

	public void addImageToProduct(Integer id, String imagePath) {
        Product product = findProductById(id);
        if (product != null) {
           
            List<ProductPicture> pictures = product.getPictures();
            if (pictures == null) {
                pictures = new ArrayList<>();
            }
            
            ProductPicture productPicture = new ProductPicture();
            productPicture.setPictureURL(imagePath);
            
            pictures.add(productPicture);
            
            product.setPictures(pictures);
           
            update(product);
        }
    }

	
}