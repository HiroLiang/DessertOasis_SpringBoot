package com.dessertoasis.demo.controller.recipe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.service.recipe.RecipeService;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeRepository;

@RestController
public class RecipeController {

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeRepository recipeRepo;

	@PostMapping("/recipe/add")
	@ResponseBody
	public String AddRecipe(@RequestBody Recipes recipe) {
		recipeService.insert(recipe);
		return "食譜新增成功";
	}
	
	

	@GetMapping("/recipe/all")
	public List<Recipes> findAllRecipes() {
		List<Recipes> recipes = recipeService.findAllRecipes();
		return recipes;
	}
	
	@GetMapping("/recipe/{id}")
	public RecipeDTO findRecipeById(@PathVariable("id") Integer id) {
		RecipeDTO recipe = recipeService.findById(id);
		
		return recipe;
	}
	
	@DeleteMapping("/recipe/delete")
	public String deleteRecipeById(@RequestParam("id") Integer id) {
		String deleteRecipe = recipeService.deleteById(id);
		return deleteRecipe;
	}
	
	//透過食譜名稱模糊搜尋食譜
	@GetMapping("recipe/recipeTitle")
	public List<Recipes> findRecipeByTitle(@RequestParam("rt") String recipeTitle){
		String nameLikeRecipe = "%" + recipeTitle + "%";
		List<Recipes> optional = recipeRepo.findRecipeByTiltleLike(nameLikeRecipe);
		
		if(optional != null) {
			return optional;	
		}
		return null;
	}
	
	//透過類別搜尋食譜
//	@GetMapping("recipe/categoryID")
//	public List<Recipes> findRecipeByCategoryID(@RequestParam("cid") String categoryID){
//		return recipeRepo.findRecipeByCategoryID(categoryID);
//	}
	
	//透過難易度搜尋食譜
	@GetMapping("recipe/difficulty")
	public List<Recipes> findRecipeByDifficulty(@RequestParam("dif") String difficulty){
		return recipeRepo.findRecipeByDifficulty(difficulty);
	}
	
	/*--------------------------------------------食譜主頁使用controller ------------------------------------------------*/
	
	//取得最新的10筆食譜
	@GetMapping("recipe/latest10Recipes")
	public List<RecipeDTO> findTop10RecipeByCreateTime(){
		List<RecipeDTO> recipes = recipeService.findTop10RecipesByCreateTime();
		
		if(recipes !=null && !recipes.isEmpty()) {
			return recipes;
		}
		return null;
	}
	
	//取得訪問數最高的10筆食譜
	@GetMapping("recipe/hottest10Recipes")
	public List<RecipeDTO> findTop10RecipeByVisitCount(){
		List<RecipeDTO> recipes = recipeService.findTop10RecipesByVisitCount();
		
		if(recipes !=null && !recipes.isEmpty()) {
			return recipes;
		}
		return null;
	}
	
	//取得特定類別的10筆食譜
	@GetMapping("recipe/category")
	public List<RecipeDTO> find10RecipeByCategory(@RequestParam Integer categoryId){
		Category category = new Category();
		category.setId(categoryId);
		return recipeService.find10RecipeByCategory(category);
	}

	
	
	

}
