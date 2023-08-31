package com.dessertoasis.demo.model.category;

import java.util.List;

import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.recipe.RecipeCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "category")
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Integer categoryId;

    @Column(name = "categoryName", columnDefinition = "nvarchar(100)")
    private String categoryName;
    
    @Column(name = "parentId", insertable = false, updatable = false)
    private Integer parentId;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;
   
    //食譜分類
    @OneToMany(mappedBy = "category")
    private List<RecipeCategory> recipeCategories;
    //商品
    @OneToMany(mappedBy = "category")
    private List<Product> products;
   
    
}
