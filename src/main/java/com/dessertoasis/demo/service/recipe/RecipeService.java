package com.dessertoasis.demo.service.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeRepository;


@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepo;

	//利用id查詢
	public RecipeDTO findById(Integer id) {
		Optional<Recipes> optional = recipeRepo.findById(id);

		if (optional.isPresent()) {
			Recipes recipe = optional.get();
			RecipeDTO rDto = new RecipeDTO();
			rDto.setId(recipe.getId());
			rDto.setRecipeAuthorId(recipe.getRecipeAuthor().getId());
			rDto.setRecipeTitle(recipe.getRecipeTitle());
			rDto.setRecipeCategories(recipe.getRecipeCategories());
			rDto.setPictureURL(recipe.getPictureURL());
			rDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
			rDto.setCookingTime(recipe.getCookingTime());
			
		return rDto;
		}
		return null;
	}

	//查詢全部
	public List<Recipes> findAllRecipes() {
		List<Recipes> result = recipeRepo.findAll();
		return result;
	}

	//刪除
	public String deleteById(Integer id) {
		Optional<Recipes> optional = recipeRepo.findById(id);

		if (optional.isPresent()) {
			recipeRepo.deleteById(id);
			return "第" + id + "筆刪除成功";
		}
		return "查無此筆資料";
	}

	//新增
	public void insert(Recipes recipe) {
		recipeRepo.save(recipe);
	}
	
	/*--------------------------------------------食譜主頁使用service ------------------------------------------------*/

	//取得最新的10筆食譜
	public List<RecipeDTO> findTop10RecipeByCreateTime(){
		List<Recipes> latest10Recipes = recipeRepo.findTop10RecipeByCreateTime();
		List<RecipeDTO>latest10RecipeDTO = new ArrayList<>();
		
		if(latest10Recipes != null && !latest10Recipes.isEmpty()) {
			for (int i = 0; i < latest10Recipes.size(); i++) {
				Recipes recipe = latest10Recipes.get(i);
				RecipeDTO rDto = new RecipeDTO();
				rDto.setRecipeAuthorId(recipe.getRecipeAuthor().getId());
				rDto.setRecipeTitle(recipe.getRecipeTitle());
				rDto.setRecipeCategories(recipe.getRecipeCategories());
				rDto.setPictureURL(recipe.getPictureURL());
				rDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
				
				latest10RecipeDTO.add(rDto);
			}
		}
		return latest10RecipeDTO;
	}

	//取得訪問數最高的10筆食譜
	public List<RecipeDTO>findTop10RecipeByVisitCount(){
		List<Recipes> hottest10Recipes = recipeRepo.findTop10RecipeByVisitCount();
		List<RecipeDTO>hottest10RecipeDTO = new ArrayList<>();
		
		if(hottest10Recipes != null && !hottest10Recipes.isEmpty()) {
			for (int i = 0; i < hottest10Recipes.size(); i++) {
				Recipes recipe = hottest10Recipes.get(i);
				RecipeDTO rDto = new RecipeDTO();
				rDto.setRecipeAuthorId(recipe.getRecipeAuthor().getId());
				rDto.setRecipeTitle(recipe.getRecipeTitle());
				rDto.setRecipeCategories(recipe.getRecipeCategories());
				rDto.setPictureURL(recipe.getPictureURL());
				rDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
				
				hottest10RecipeDTO.add(rDto);
			}
		}
		return hottest10RecipeDTO;
	}
		

	
	

}