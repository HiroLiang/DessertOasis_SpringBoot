package com.dessertoasis.demo.service.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.recipe.RecipeCategory;
import com.dessertoasis.demo.model.recipe.RecipeCategoryRepository;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeCarouselDTO;
import com.dessertoasis.demo.model.recipe.RecipeRepository;


@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private CategoryRepository cateRepo;

	//利用id查詢
	public RecipeDTO findById(Integer id) {
		Optional<Recipes> optional = recipeRepo.findById(id);

		if (optional.isPresent()) {
			Recipes recipe = optional.get();
			RecipeDTO rDto = new RecipeDTO();
			rDto.setId(recipe.getId());
			rDto.setRecipeAuthorId(recipe.getRecipeAuthor().getId());
			rDto.setRecipeTitle(recipe.getRecipeTitle());
			List<RecipeCategory> recipeCategories = recipe.getRecipeCategories();
			List<Integer> categoryIds = new ArrayList<>();
			for (RecipeCategory rc : recipeCategories) {
                categoryIds.add(rc.getCategory().getId());
            }
			rDto.setRecipeCategoryIds(categoryIds);
			rDto.setPictureURL(recipe.getPictureURL());
			rDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
			rDto.setCookingTime(recipe.getCookingTime());
			rDto.setDifficulty(recipe.getDifficulty());
			rDto.setRecipeStatus(recipe.getRecipeStatus());
			
		return rDto;
		}
		return null;
	}

	//查詢全部
	public List<Recipes> findAllRecipes() {
		List<Recipes> result = recipeRepo.findAll();
		return result;
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
	/*--------------------------------------------食譜建立頁使用service ------------------------------------------------*/

	//新增食譜
	public Recipes addRecipe(RecipeDTO rDto) {
		Recipes recipe = new Recipes();
		recipe.setRecipeTitle(rDto.getRecipeTitle());
		recipe.setPictureURL(rDto.getPictureURL());
		recipe.setRecipeIntroduction(rDto.getRecipeIntroduction());
		recipe.setCookingTime(rDto.getCookingTime());
		recipe.setDifficulty(rDto.getDifficulty());
		recipe.setRecipeCreateDate(rDto.getRecipeCreateDate());
		recipe.setRecipeStatus(rDto.getRecipeStatus());
		if(rDto.getRecipeAuthorId() != null) {
			Optional<Member> optional = memberRepo.findById(rDto.getRecipeAuthorId());
			if(optional.isPresent()) {
				Member author = optional.get();
				recipe.setRecipeAuthor(author);
			}
		}
		if(rDto.getRecipeCategoryIds() != null) {
			ArrayList<RecipeCategory> categories = new ArrayList<>();
			for (Integer categoryId : rDto.getRecipeCategoryIds()) {
				Optional<Category> optional = cateRepo.findById(categoryId);
				if(optional.isPresent()) {
					Category category = optional.get();
					RecipeCategory rc = new RecipeCategory();
					rc.setCategory(category);
					rc.setRecipe(recipe);
					
					categories.add(rc);
				}
			}
			recipe.setRecipeCategories(categories);
		}
		recipe.setRecipeSteps(null);
		return recipeRepo.save(recipe);
	}
	
	//修改食譜
	public String updateRecipe(Integer id) {
		Optional<Recipes> optional = recipeRepo.findById(id);
		
		if(optional.isPresent()) {
			Recipes recipe = optional.get();
			RecipeDTO rDto = new RecipeDTO();
			rDto.setId(recipe.getId());
			rDto.setRecipeAuthorId(recipe.getRecipeAuthor().getId());
			rDto.setRecipeTitle(recipe.getRecipeTitle());
			List<RecipeCategory> recipeCategories = recipe.getRecipeCategories();
			List<Integer> categoryIds = new ArrayList<>();
			for (RecipeCategory rc : recipeCategories) {
                categoryIds.add(rc.getCategory().getId());
            }
			rDto.setRecipeCategoryIds(categoryIds);
			rDto.setPictureURL(recipe.getPictureURL());
			rDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
			rDto.setCookingTime(recipe.getCookingTime());
			rDto.setDifficulty(recipe.getDifficulty());
			rDto.setRecipeStatus(recipe.getRecipeStatus());
			
			return "修改成功";
		}
		return"查無此筆資料";
	}
	
	//刪除
	public String deleteById(Integer recipeId) {
		Optional<Recipes> optional = recipeRepo.findById(recipeId);

		if (optional.isPresent()) {
			recipeRepo.deleteById(recipeId);
			return "第" + recipeId + "筆刪除成功";
		}
		return "查無此筆資料";
	}

	

}