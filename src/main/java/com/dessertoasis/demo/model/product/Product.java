package com.dessertoasis.demo.model.product;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.order.ProdOrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data	
@Entity 
@Table(name = "product")

public class Product {

	@Id 
	@Column(name = "id", columnDefinition = "int")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
//	@Column(name = "categoryId")
//	private Integer categoryId;
	
	@ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	//@JsonProperty("categoryId")
	@JsonIgnoreProperties({"products"})
    private Category category;
	//private Integer categoryId;
//	@Column(name = "categoryName", insertable = false, updatable = false)
//	private String categoryName;
	
	@Column(name = "prodName",columnDefinition = "nvarchar(100)")
	private String prodName;
	
	@Column(name = "prodDescription",columnDefinition = "nvarchar(max)")
	private String prodDescription;
	
	@Column(name = "prodStock")
	private Integer prodStock;
	
	@Column(name = "prodPrice", columnDefinition = "DECIMAL(10, 2)")
	private Integer prodPrice;
	
	
	@Column(name = "prodPurchase")
	private Integer prodPurchase;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") // 在資料進 Java 環境時，做格式化
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime")
	private Timestamp updateTime;
	
	@Column(name = "saleAfterUpdate")
	private Integer saleAfterUpdate;
	
	
	
	@Column(name = "productStatus",columnDefinition = "nvarchar(100)")
	private String productStatus;
	
	@Column(name = "prodRemark",columnDefinition = "nvarchar(500)")
	private String prodRemark;
	
	
	 @OneToMany(mappedBy = "product")
	    private List<ProductPicture> pictures;
	 
	 @JsonIgnore
	 @OneToMany(mappedBy = "product")
	 private List<ProdOrderItem> prodOrderItems;


	public Product() {
		super();
	}

	public Product(Integer id, Category category, String prodName, String prodDescription, Integer prodStock,
			Integer prodPrice, Integer prodPurchase, Timestamp updateTime, Integer saleAfterUpdate,
			String productStatus, String prodRemark, List<ProductPicture> pictures,
			List<ProdOrderItem> prodOrderItems) {
		super();
		this.id = id;
		this.category = category;
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		this.prodStock = prodStock;
		this.prodPrice = prodPrice;
		this.prodPurchase = prodPurchase;
		this.updateTime = updateTime;
		this.saleAfterUpdate = saleAfterUpdate;
		this.productStatus = productStatus;
		this.prodRemark = prodRemark;
		this.pictures = pictures;
		this.prodOrderItems = prodOrderItems;
	}

}