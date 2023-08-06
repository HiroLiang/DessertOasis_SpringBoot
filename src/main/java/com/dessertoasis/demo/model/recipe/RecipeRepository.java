package com.dessertoasis.demo.model.recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	
	//食譜名稱模糊搜尋
	@Query("from Recipe where recipeTitle like %?1% ")
	List<Recipe> findRecipeByTiltleLike(String recipeTitle);
	
	//食譜分類搜尋
	@Query("from Recipe where categoryID = :cid")
	List<Recipe> findRecipeByCategoryID(@Param("cid") String categoryID);
	
	//食譜難易度搜尋
	@Query("from Recipe where difficulty = :dif")
	List<Recipe> findRecipeByDifficulty(@Param("dif") String difficulty);
	

}
