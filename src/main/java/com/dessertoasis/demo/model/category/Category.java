package com.dessertoasis.demo.model.category;

import java.util.List;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.recipe.RecipeCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private Integer id;

    @Column(name = "categoryName", columnDefinition = "nvarchar(100)")
    private String categoryName;
    
    @Column(name = "parentId", insertable = false, updatable = false)
    private Integer parentId;
    
    @JsonIgnoreProperties({"children", "parent"})
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
   
    //課程分類
    @OneToMany(mappedBy = "category")
    private List<Course> courses;
}
