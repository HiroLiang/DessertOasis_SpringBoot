package com.dessertoasis.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.product.ProdSearchDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.product.ProductService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

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
    public ResponseEntity<Integer> addProduct(@RequestBody Product product) {
        Product savedProduct = pService.insert(product); // 插入并获取新创建的产品对象
        Integer productId = savedProduct.getId(); // 获取新创建的产品的ID

        return ResponseEntity.ok(productId); // 返回产品ID
    }
//    @PostMapping("/add")
//    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
//        // 处理产品的其他数据（不包括图像）
//        Product savedProduct = pService.insert(product);
//        return ResponseEntity.ok(savedProduct);
//    }
    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("productId") Integer productId, @RequestParam("image") MultipartFile image) {
        try {
            String baseDir = "C:/workspace/dessertoasis-vue/public/images/product/";
            String productDir = baseDir + productId;
            String thumbnailDir = productDir + "/thumbnail"; // 创建thumbnail子文件夹

            File productFolder = new File(productDir);
            if (!productFolder.exists()) {
                productFolder.mkdirs();
            }

            File thumbnailFolder = new File(thumbnailDir);
            if (!thumbnailFolder.exists()) {
                thumbnailFolder.mkdirs();
            }

            String imagePath = productDir + "/" + image.getOriginalFilename();
            String thumbnailPath = thumbnailDir + "/" + "thumbnail_" + image.getOriginalFilename(); // 修改縮圖路径

            File destination = new File(imagePath);
            image.transferTo(destination);

            // 处理縮圖逻辑，将縮圖存储在thumbnailPath中

            //pService.addImageToProduct(productId, imagePath);

            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
        }
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
            Pageable pageable) {
    	
        Page<Product> products = pService.searchProducts(criteria, pageable);
        return ResponseEntity.ok(products);
    }
//
//    @PostMapping("/criter")
//    public ResponseEntity<Page<Product>> searchProducts(
//            @RequestBody ProdSearchDTO criteria,
//            @PageableDefault(size = 20) Pageable pageable,
//            @RequestParam(value = "sort", required = false) String sortParam
//    ) {
//        Sort sort = Sort.unsorted();
//        Integer pageSize = pageable.getPageSize();
//
//        if (sortParam != null && !sortParam.isEmpty()) {
//            String[] sortFields = sortParam.split(",");
//            if (sortFields.length == 2) {
//                String field = sortFields[0];
//                String direction = sortFields[1].toUpperCase();
//                Sort.Order order = "ASC".equals(direction) ? Sort.Order.asc(field) : Sort.Order.desc(field);
//                sort = Sort.by(order);
//            }
//        }
//
//        Pageable customPageable = PageRequest.of(pageable.getPageNumber()-1, pageSize, sort);
//
//        Page<Product> products = pService.searchProducts(criteria, customPageable);
//        return ResponseEntity.ok(products);
//    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
    		@RequestParam(value = "prodName", required = false) String prodName,
        @RequestParam(value = "productStatus", required = false) String productStatus,
        @RequestParam(value = "categoryName", required = false) String categoryName,
        @RequestParam(value = "sortBy", required = false) String sortBy,
        @RequestParam(value = "pageSize", required = false) Integer pageSize,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        ProdSearchDTO criteria = new ProdSearchDTO();

        // 設置關鍵字
        if (prodName != null && !prodName.isEmpty()) {
            criteria.setProdName(prodName);
        }
        
        if (productStatus != null && !productStatus.isEmpty()) {
            criteria.setProductStatus(productStatus);
        }
        
        if (categoryName != null && !categoryName.isEmpty()) {
            criteria.setProductStatus(categoryName);
        }
        

        // 設置排序
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
        	String[] sortParams = sortBy.split("&");
            for (String param : sortParams) {
                String[] sortField = param.split(",");
                if (sortField.length == 2) {
                    String field = sortField[0];
                    String direction = sortField[1].toUpperCase(); // 確保方向為大寫
                    Sort.Order order = "ASC".equals(direction) ? Sort.Order.asc(field) : Sort.Order.desc(field);
                    sort = sort.and(Sort.by(order));
                }
            }
        }

        // 設置頁面大小
        int adjustedPage = pageable.getPageNumber() - 1;
        int effectivePageSize = pageSize != null ? pageSize : 20;

        Page<Product> products = pService.searchProducts(criteria, PageRequest.of(adjustedPage, effectivePageSize, sort));
        return ResponseEntity.ok(products);
    }
    
 
 	@PostMapping("/pagenation")
 	public List<ProdSearchDTO> getOrderPage(@RequestBody SortCondition sortCon, HttpSession session) {
 		System.out.println(sortCon);
 		// 判斷 user 存在且為 ADMIN
// 		Member user = (Member) session.getAttribute("loggedInMember");
// 		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
// 			return null;
// 		}
 		// 送出查詢條件給service，若有結果則回傳list
 		List<ProdSearchDTO> result = pService.getProductPagenation(sortCon);
 		if (result != null) {
 			System.out.println(result);
 			return result;
 		}
 		return null;
 	}

 	@PostMapping("/pages")
 	public Integer getPages(@RequestBody SortCondition sortCon, HttpSession session) {
 		System.out.println(sortCon);
 		// 判斷 user 存在且為 ADMIN
// 		Member user = (Member) session.getAttribute("loggedInMember");
// 		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
// 			return null;
// 		}
 		// 送出條件查詢總頁數
 		Integer pages = pService.getPages(sortCon);
 		return pages;
 	}


}