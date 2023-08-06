package com.dessertoasis.demo.model.recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	
	@Query("from Recipe where recipeTitle like %?1% ")
	List<Recipe> findRecipeByTiltleLike(String recipeTitle);
	
	@Query("from Recipe where categoryID = :cid")
	List<Recipe> findRecipeByCategoryID(@Param("cid") String categoryID);
	
	@Query("from Recipe where difficulty = :dif")
	List<Recipe> findRecipeByDifficulty(@Param("dif") String difficulty);
	

}
