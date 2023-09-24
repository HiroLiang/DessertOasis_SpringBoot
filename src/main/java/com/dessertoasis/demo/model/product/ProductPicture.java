package com.dessertoasis.demo.model.product;
import java.io.File;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data	
@Entity 
@Table(name = "prodPicture")

public class ProductPicture {

	@Id 
	@Column(name = "id", columnDefinition = "int")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
	@JsonIgnoreProperties({"prodPicture"})
	//@JsonIgnore
	private Product product;
	
	@Column(name = "pictureURL",columnDefinition = "nvarchar(max)")
	private String pictureURL;

	public ProductPicture(Integer id, Product product, String pictureURL) {
		super();
		this.id = id;
		this.product = product;
		this.pictureURL = pictureURL;
	}

	public ProductPicture() {
		super();
	}

	public void deleteImageFile() {
		  if (pictureURL != null && !pictureURL.isEmpty()) {
		        try {
		            // 圖片儲存的根路徑，根據你的實際情況修改
//		            String rootPath = "C:/workspace/dessertoasis-vue/public/images/product/";
		            String rootPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\images\\product\\";
		            Integer productId = product.getId();
		            
		            // 獲取完整的資料夾路徑（資料夾名稱即為productId）
		            String folderPath = rootPath + productId;
		            
		            // 創建File對象表示要刪除的資料夾
		            File folder = new File(folderPath);

		            // 檢查資料夾是否存在
		            if (folder.exists()) {
		                deleteRecursive(folder); // 刪除資料夾及其內容
		                System.out.println("圖片文件及其資料夾刪除成功：" + folderPath);
		            } else {
		                System.out.println("圖片資料夾不存在：" + folderPath);
		            }
		        } catch (Exception e) {
		            System.err.println("刪除圖片文件及其資料夾時出現異常：" + e.getMessage());
		        }
		    } else {
		        System.out.println("圖片URL為空，無法刪除圖片文件及其資料夾");
		    }
		}

		private void deleteRecursive(File file) {
		    if (file.isDirectory()) {
		        File[] contents = file.listFiles();
		        if (contents != null) {
		            for (File f : contents) {
		                deleteRecursive(f);
		            }
		        }
		    }

		    if (file.delete()) {
		        System.out.println("文件或資料夾刪除成功：" + file.getAbsolutePath());
		    } else {
		        System.out.println("文件或資料夾刪除失敗：" + file.getAbsolutePath());
		    }
		}

	
	
	
	
//	public ProductPicture() {
//	}
//	
//	
//
//	public Product(int prodID, int typeID, String prodName, String prodDescription, int prodStock, int prodPrice,
//			String prodPicURL, Integer prodPurchase, Timestamp prodAddtime, Timestamp prodShelftime,
//			String prodRemark) {
//		super();
//		this.prodID = prodID;
//		this.typeID = typeID;
//		this.prodName = prodName;
//		this.prodDescription = prodDescription;
//		this.prodStock = prodStock;
//		this.prodPrice = prodPrice;
//		this.prodPicURL = prodPicURL;
//		this.prodPurchase = prodPurchase;
//		this.prodAddtime = prodAddtime;
//		this.prodShelftime = prodShelftime;
//		this.prodRemark = prodRemark;
//	}
//
//
//
//	public Product(int typeID, String prodName, String prodDescription, int prodStock, int prodPrice,
//			String prodPicURL,String prodRemark) {
//		super();
//		this.typeID = typeID;
//		this.prodName = prodName;
//		this.prodDescription = prodDescription;
//		this.prodStock = prodStock;
//		this.prodPrice = prodPrice;
//		this.prodPicURL = prodPicURL;
//		//this.prodAddtime = prodAddtime;
//		//this.prodShelftime = prodShelftime;
//		this.prodRemark = prodRemark;
//	}
//	
//	public Product(int typeID, String prodName, String prodDescription, int prodStock, int prodPrice,
//			String prodRemark) {
//		super();
//		this.typeID = typeID;
//		this.prodName = prodName;
//		this.prodDescription = prodDescription;
//		this.prodStock = prodStock;
//		this.prodPrice = prodPrice;
//		//this.prodPicURL = prodPicURL;
//		//this.prodAddtime = prodAddtime;
//		//this.prodShelftime = prodShelftime;
//		this.prodRemark = prodRemark;
//	}
//	
//	
//	public Product(
//			String prodPicURL) {
//		super();
//		
//		this.prodPicURL = prodPicURL;
//	
//	}



//	public Integer getId() {
//		return id;
//	}
//
//
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//
//
//	public Product getProduct() {
//		return product;
//	}
//
//
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//
//
//
//	public String getPictureURL() {
//		return pictureURL;
//	}
//
//
//
//	public void setPictureURL(String pictureURL) {
//		this.pictureURL = pictureURL;
//	}


	


//	
}