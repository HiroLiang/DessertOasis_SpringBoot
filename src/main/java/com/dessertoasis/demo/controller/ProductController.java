package com.dessertoasis.demo.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;
import com.dessertoasis.demo.model.product.ProdSearchDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductPicture;
import com.dessertoasis.demo.model.product.ProductPictureRepository;
import com.dessertoasis.demo.model.product.ProductRepository;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.CategoryService;
import com.dessertoasis.demo.service.product.ProductPictureService;
import com.dessertoasis.demo.service.product.ProductService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService pService;
    @Autowired
	private ProductPictureRepository ProdPicRepo;
    
    @Autowired
	private ImageUploadUtil imgUtil;
    
    @Autowired
    private ProductPictureService ppService;
    
    @Autowired
    private CategoryService cService;
    
    @Autowired
    private CategoryRepository cRepo;
    
    @Autowired
    private ProductRepository pRepo;
    

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = pService.findAllProduct();
        return ResponseEntity.ok(products);
    }

//
//    @GetMapping("/details/{id}")
//    public ResponseEntity<Product> productDetails(@PathVariable Integer id) {
//        Product product = pService.findProductById(id);
//        if (product != null) {
//            return ResponseEntity.ok(product);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping("/details/{id}")
    public ResponseEntity<Map<String, Object>> getProductAndCategory(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        Product product = pService.findProductById(id);
        if (product != null) {
            // 创建产品信息对象
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put("prodName", product.getProdName());
            productInfo.put("prodDescription", product.getProdDescription());
            productInfo.put("prodStock", product.getProdStock());
            productInfo.put("prodPrice", product.getProdPrice());
            productInfo.put("updateTime", product.getUpdateTime());
            productInfo.put("prodRemark", product.getProdRemark());
            
            // 创建类别信息对象
            Map<String, Object> categoryInfo = new HashMap<>();
            Category category = product.getCategory();
            if (category != null) {
                categoryInfo.put("id", category.getId());
            }
            
            // 将产品信息和类别信息添加到结果对象中
            result.put("product", productInfo);
            result.put("category", categoryInfo);
            
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @PostMapping("/add")
//    public ResponseEntity<Integer> addProduct(@RequestBody Product product) {
//        Product savedProduct = pService.insert(product); // 插入并获取新创建的产品对象
//        Integer productId = savedProduct.getId(); // 获取新创建的产品的ID
//
//        return ResponseEntity.ok(productId); // 返回产品ID
//    }
    @PostMapping("/add")
    public ResponseEntity<Integer> addProductAndCategory(@RequestBody Map<String, Object> requestPayload) {
        try {
            // 从 JSON 数据中提取“Product”和“Category”的信息
            Map<String, Object> productData = (Map<String, Object>) requestPayload.get("product");
            Map<String, Object> categoryData = (Map<String, Object>) requestPayload.get("category");

            // 创建新的 Category 实例并保存它
            Category category = new Category();
            category.setId((Integer) categoryData.get("id"));
            // 设置其他 Category 属性（如果需要）

            // 创建新的 Product 实例并与 Category 关联
            Product product = new Product();
            product.setCategory(category); // 设置关联的 Category
            product.setProdName((String) productData.get("prodName"));
            product.setProdDescription((String) productData.get("prodDescription"));
            product.setProdStock((Integer) productData.get("prodStock"));
            product.setProdPrice((Integer) productData.get("prodPrice")); // 使用 BigDecimal 存储价格
            product.setProdPurchase((Integer) productData.get("prodPurchase"));
            String frontendTimestamp = (String) productData.get("updateTime");
            SimpleDateFormat frontendDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            frontendDateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); 
            Date date = frontendDateFormat.parse(frontendTimestamp);
            Timestamp backendTimestamp = new Timestamp(date.getTime());
            product.setUpdateTime(backendTimestamp); // 设置后端格式的时间戳
            product.setProdRemark((String) productData.get("prodRemark"));

            // 保存 Category 和 Product 到数据库
            cRepo.save(category); // 保存类别
            pRepo.save(product); // 保存产品

            // 返回新创建的 Product 的 ID
            return ResponseEntity.ok(product.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



//    @PostMapping("/add")
//    public ResponseEntity<Integer> addProduct(@RequestBody Product product) {
//        Integer categoryId = product.getCategory().getId();
//
//        // 查询或检索 Category 对象
//        Category category = cService.findById(categoryId); // 假设您有一个名为 categoryService 的服务类来处理 Category 的相关操作
//
//        if (category == null) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        product.setCategory(category);
//
//        Product savedProduct = pService.insert(product);
//
//        Integer productId = savedProduct.getId(); // 获取新创建的产品的ID
//
//        return ResponseEntity.ok(productId); // 返回产品ID
//    }

   
    
   
    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("productId") Integer productId, @RequestParam("image") MultipartFile image) {
       
        try {
        	String uploadDir ="C:/Users/iSpan/Documents/dessertoasis-vue/public/images/product/"+productId;
        	 //String uploadDir ="C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\" +productId;
            //String uploadDir = "C:/workspace/dessertoasis-vue/public/images/product/" +productId;
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String imagePath = uploadDir + "/" + image.getOriginalFilename();
            String sqlPath = "/"+"images/product/"+productId+ "/" + image.getOriginalFilename();
            File destination = new File(imagePath);
            image.transferTo(destination);
System.out.println(imagePath);
           
            pService.addImageToProduct(productId, sqlPath);

            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
        }
    }

    /*----------------------﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀發送base64給前端範例﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀--------------------------*/
	@PostMapping("/getImage")
	@ResponseBody
	public List<String> getPicByGetPicture(@RequestBody Integer id) {
		System.out.println(id);
		Product productPictures = pService.findById(id);
		 System.out.println(productPictures.getPictures().get(0).getPictureURL());
		System.out.println("start");
		 if (!productPictures.getPictures().isEmpty()) {
		        System.out.println("if");
		        //String userPath = "C:/workspace/dessertoasis-vue/public";
		         String userPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public";
		        
		       
		        for (ProductPicture productPicture : productPictures.getPictures()) {
		            String pictureURL = productPicture.getPictureURL();
		            
		            /*-------------------getPicture方法   第一個參數接收自己的儲存路徑， 第二個參數接收存於資料庫的路徑(範例: images/recipe/1/3584160_20230914005256937.jpg  等等)---------------------*/
		            List<String> picture = imgUtil.getPicture(userPath, pictureURL);
		            System.out.println(userPath);
		            System.out.println(pictureURL);
		            HttpHeaders headers = new HttpHeaders();
		            /*-------------------getPicture回傳值[0]為檔案MIME字串(如image/png 等)  將其設定到 headers中----------------------------*/
		            headers.setContentType(MediaType.parseMediaType(picture.get(0)));
		            
		            /*-------------------getPicture回傳值[1]為檔案base64字串  將其設定到body中----------------------------*/
		            return picture;
		        }
		    }
		    
		    return null;
		}
	
	
	@PostMapping("/getAllImage")
	@ResponseBody
	public List<List<String>> getAllPicByGetPicture(@RequestBody Integer id) {
	    System.out.println(id);
	    Product productPictures = pService.findById(id);
	    System.out.println("start");
	    List<List<String>> allPictures = new ArrayList<>(); // 創建用於存儲所有圖片資訊的列表
	    List<String> pictureURLs = new ArrayList<>();

	    if (!productPictures.getPictures().isEmpty()) {
	        //String userPath = "C:\\workspace\\dessertoasis-vue\\public";
	        // String userPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\";
            String userPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public";
	    	//String userPath = "C:\\Vue\\dessertoasis-vue\\public";

	        for (ProductPicture productPicture : productPictures.getPictures()) {
	            String pictureURL = productPicture.getPictureURL();

	            /*-------------------getPicture方法   第一個參數接收自己的儲存路徑， 第二個參數接收存於資料庫的路徑(範例: images/recipe/1/3584160_20230914005256937.jpg  等等)---------------------*/
	            List<String> pictureInfo = imgUtil.getPicture(userPath, pictureURL);
	            System.out.println(userPath);
	            System.out.println(pictureURL);

	            if (!pictureInfo.isEmpty()) {
	                allPictures.add(pictureInfo); // 添加圖片資訊到列表中
	                pictureURLs.add(pictureURL);
	            }
	        }

	        if (!allPictures.isEmpty()) {
	            HttpHeaders headers = new HttpHeaders();
	            /*-------------------設定 headers，這裡使用第一張圖片的 MIME 類型---------------------*/
	            headers.setContentType(MediaType.parseMediaType(allPictures.get(0).get(0)));
	            allPictures.add(pictureURLs); // 添加图片URL列表到返回数据中
	            return allPictures; 
	        }
	    }

	    return null;
	}
	
//	 @PostMapping("/updateImg/{id}")
//	    public ResponseEntity<String> updateProductPictures(@PathVariable Integer id, @RequestBody List<String> pictureUrls) {
//	        try {
//	            // 根据商品ID查询商品信息
//	            Product product = pService.findById(id);
//
//	            if (product != null) {
//	                // 根据传入的图片URL列表，创建新的 ProductPicture 实例并添加到商品的图片列表中
//	                List<ProductPicture> productPictures = new ArrayList<>();
//	                for (String pictureUrl : pictureUrls) {
//	                    ProductPicture productPicture = new ProductPicture();
//	                    productPicture.setPictureURL(pictureUrl);
//	                    // 设置其他图片属性（如果需要）
//	                    productPictures.add(productPicture);
//	                }
//	                product.setPictures(productPictures);
//
//	                // 更新商品信息
//	                pService.update(product);
//
//	                return ResponseEntity.ok("商品图片信息已成功更新");
//	            } else {
//	                return ResponseEntity.notFound().build(); // 如果商品不存在，返回404响应
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新商品图片信息时出现错误");
//	        }
//	    }
//	
	@PostMapping("/updateImg/{id}")
	public ResponseEntity<String> updateProductPictures(
	    @PathVariable Integer id,
	    @RequestParam("image") MultipartFile[] pictureFiles) {
	    try {
	        // 根據商品ID查詢商品信息
	        Product product = pService.findById(id);

	        if (product != null) {
	            // 根據傳入的圖片檔案列表，創建新的 ProductPicture 實例並添加到商品的圖片列表中
	            List<ProductPicture> productPictures = new ArrayList<>();
	            for (MultipartFile pictureFile : pictureFiles) {
	                String pictureUrl = savePictureAndGetUrl(pictureFile, id);
	                if (pictureUrl != null) {
	                    ProductPicture productPicture = new ProductPicture();
	                    productPicture.setPictureURL(pictureUrl);
	                    // 設置其他圖片屬性（如果需要）
	                    productPictures.add(productPicture);
	                }
	            }
	            product.setPictures(productPictures);

	            // 更新商品信息
	            pService.update(product);

	            return ResponseEntity.ok("商品圖片信息已成功更新");
	        } else {
	            return ResponseEntity.notFound().build(); // 如果商品不存在，返回404響應
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新商品圖片信息時出現錯誤");
	    }
	}

	private String savePictureAndGetUrl(MultipartFile pictureFile, Integer productId) throws IOException {
	    String uploadDir = "C:/workspace/dessertoasis-vue/public/images/product/" + productId;
	    File dir = new File(uploadDir);
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    String imagePath = uploadDir + "/" + pictureFile.getOriginalFilename();
	    String sqlPath = "/" + "images/product/" + productId + "/" + pictureFile.getOriginalFilename();
	    File destination = new File(imagePath);
	    pictureFile.transferTo(destination);

	    // 在此處添加將圖片 URL 儲存到數據庫的邏輯（如果需要）

	    return sqlPath; // 返回圖片的 URL
	}


	/*----------------------﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀發送base64給前端範例﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀--------------------------*/
    
	
//	@PostMapping("/edit/{id}")
//    public ResponseEntity<Product> editProduct(@PathVariable Integer id, @RequestBody Product product) {
//        Product existingProduct = pService.findProductById(id);
//        if (existingProduct != null) {
//            pService.update(product); // Use the provided product object
//            return ResponseEntity.ok(product);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
	@PostMapping("/edit/{id}")
	public ResponseEntity<Integer> editProductAndCategory(@PathVariable Integer id, @RequestBody Map<String, Object> requestPayload) {
	    try {
	        // 从 JSON 数据中提取“Product”和“Category”的信息
	        Map<String, Object> productData = (Map<String, Object>) requestPayload.get("product");
	        Map<String, Object> categoryData = (Map<String, Object>) requestPayload.get("category");

	        // 查找现有商品
	        Product existingProduct = pService.findProductById(id);
	        if (existingProduct == null) {
	            return ResponseEntity.notFound().build();
	        }

	        // 更新商品属性
	        existingProduct.setProdName((String) productData.get("prodName"));
	        existingProduct.setProdDescription((String) productData.get("prodDescription"));
	        existingProduct.setProdStock((Integer) productData.get("prodStock"));
	        existingProduct.setProdPrice((Integer) productData.get("prodPrice")); // 使用 BigDecimal 存储价格
	        String frontendTimestamp = (String) productData.get("updateTime");
	        SimpleDateFormat frontendDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	        Date date = frontendDateFormat.parse(frontendTimestamp);
	        Timestamp backendTimestamp = new Timestamp(date.getTime());
	        existingProduct.setUpdateTime(backendTimestamp); // 设置后端格式的时间戳
	        existingProduct.setProdRemark((String) productData.get("prodRemark"));

	        // 更新关联的分类信息
	        Category existingCategory = existingProduct.getCategory();
	        if (categoryData != null) {
	            Integer categoryId = (Integer) categoryData.get("id");
	            if (categoryId != null) {
	                // 检查分类是否存在于数据库中
	                Category category = cRepo.findById(categoryId).orElse(null);
	                if (category != null) {
	                    // 分配现有分类
	                    existingProduct.setCategory(category);
	                }
	            }
	        }
//	        Category category = existingProduct.getCategory();
//	        category.setId((Integer) categoryData.get("id"));
	        // 设置其他 Category 属性（如果需要）

	        // 保存 Category 和更新后的 Product 到数据库
	        //cRepo.save(category); // 保存类别
	        cRepo.save(existingCategory); 
	        pRepo.save(existingProduct); // 更新产品

	        // 返回编辑后的 Product 的 ID
	        return ResponseEntity.ok(existingProduct.getId());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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