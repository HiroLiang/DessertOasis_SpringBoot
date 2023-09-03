package com.dessertoasis.demo.service.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.recipe.RecipeCategory;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeCarouselDTO;
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
	public List<RecipeCarouselDTO> findTop10RecipesByCreateTime(){
		List<Recipes> latest10Recipes = recipeRepo.findTop10RecipeByCreateTime();
		List<RecipeCarouselDTO>latest10RecipeDTO = new ArrayList<>();
		
		if(latest10Recipes != null && !latest10Recipes.isEmpty()) {
			for (int i = 0; i < latest10Recipes.size(); i++) {
				Recipes recipe = latest10Recipes.get(i);
				RecipeCarouselDTO rcDto = new RecipeCarouselDTO();
				rcDto.setId(recipe.getId());
				rcDto.setRecipeTitle(recipe.getRecipeTitle());
				List<RecipeCategory> recipeCategories = recipe.getRecipeCategories();
				List<Integer> categoryIds = new ArrayList<>();
				for (RecipeCategory rc : recipeCategories) {
	                categoryIds.add(rc.getCategory().getId());
	            }
				rcDto.setRecipeCategoryIds(categoryIds);
				rcDto.setPictureURL(recipe.getPictureURL());
				rcDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
				
				latest10RecipeDTO.add(rcDto);
			}
		}
		return latest10RecipeDTO;
	}

	//取得訪問數最高的10筆食譜
	public List<RecipeCarouselDTO> findTop10RecipesByVisitCount(){
		List<Recipes> hottest10Recipes = recipeRepo.findTop10RecipeByVisitCount();
		List<RecipeCarouselDTO>hottest10RecipeDTO = new ArrayList<>();
		
		if(hottest10Recipes != null && !hottest10Recipes.isEmpty()) {
			for (int i = 0; i < hottest10Recipes.size(); i++) {
				Recipes recipe = hottest10Recipes.get(i);
				RecipeCarouselDTO rcDto = new RecipeCarouselDTO();
				rcDto.setId(recipe.getId());
				rcDto.setRecipeTitle(recipe.getRecipeTitle());
				List<RecipeCategory> recipeCategories = recipe.getRecipeCategories();
				List<Integer> categoryIds = new ArrayList<>();
				for (RecipeCategory rc : recipeCategories) {
	                categoryIds.add(rc.getCategory().getId());
	            }
				rcDto.setRecipeCategoryIds(categoryIds);
				rcDto.setPictureURL(recipe.getPictureURL());
				rcDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
				
				hottest10RecipeDTO.add(rcDto);
			}
		}
		return hottest10RecipeDTO;
	}
	
	public List<RecipeCarouselDTO> find10RecipeByCategory(Category category){
		List<Recipes> recipes = recipeRepo.findRecipesByCategory(category);
		List<RecipeCarouselDTO> categoryRecipeDTO = new ArrayList<>();
		if(recipes != null && !recipes.isEmpty()) {
			for (int i = 0; i < Math.min(10, recipes.size()); i++) {
				Recipes recipe = recipes.get(i);
				RecipeCarouselDTO rcDto = new RecipeCarouselDTO();
				rcDto.setId(recipe.getId());
				rcDto.setRecipeTitle(recipe.getRecipeTitle());
				List<RecipeCategory> recipeCategories = recipe.getRecipeCategories();
				List<Integer> categoryIds = new ArrayList<>();
				for (RecipeCategory rc : recipeCategories) {
	                categoryIds.add(rc.getCategory().getId());
	            }
				rcDto.setRecipeCategoryIds(categoryIds);
				rcDto.setPictureURL(recipe.getPictureURL());
				rcDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
				
				categoryRecipeDTO.add(rcDto);
			}
		}
		return categoryRecipeDTO;
	}

	
	

}