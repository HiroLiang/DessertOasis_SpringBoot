package com.dessertoasis.demo.model.recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipes, Integer> {
	
	//食譜名稱模糊搜尋
	@Query("from Recipes where recipeTitle like %?1% ")
	List<Recipes> findRecipeByTiltleLike(String recipeTitle);
	
//	//食譜分類搜尋
//	@Query("from Recipes where categoryID = :cid")
//	List<Recipes> findRecipeByCategoryID(@Param("cid") String categoryID);
//	
	//食譜難易度搜尋
	@Query("from Recipes where difficulty = :dif")
	List<Recipes> findRecipeByDifficulty(@Param("dif") String difficulty);
	
	
	/*--------------------------------------------食譜主頁使用query ------------------------------------------------*/
	
	//取得最新的10筆食譜
	@Query(value="select top 10 * from Recipes order by recipeCreateDate ASC", nativeQuery = true)
	List<Recipes> findTop10RecipeByCreateTime();
	
	//取得訪問數最高的10筆食譜
	@Query(value="select top 10 * from Recipes order by recipeMonthlyVisitCount ASC", nativeQuery = true)
	List<Recipes> findTop10RecipeByVisitCount();
	
	
	
//	List<Recipes> findRecipeByCategory();
	
	

}
