package com.dessertoasis.demo.controller.recipe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.recipe.RecipeService;

import jakarta.servlet.http.HttpSession;

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.recipe.RecipeCarouselDTO;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeRepository;

@RestController
public class RecipeController {

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeRepository recipeRepo;

//	@PostMapping("/recipe/add")
//	@ResponseBody
//	public String AddRecipe(@RequestBody Recipes recipe) {
//		recipeService.insert(recipe);
//		return "食譜新增成功";
//	}
	
	

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
	
//	@DeleteMapping("/recipe/delete")
//	public String deleteRecipeById(@RequestParam("id") Integer id) {
//		String deleteRecipe = recipeService.deleteById(id);
//		return deleteRecipe;
//	}
	
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
	
	//透過難易度搜尋食譜
	@GetMapping("recipe/difficulty")
	public List<Recipes> findRecipeByDifficulty(@RequestParam("dif") String difficulty){
		return recipeRepo.findRecipeByDifficulty(difficulty);
	}
	
	/*--------------------------------------------食譜主頁使用controller ------------------------------------------------*/
	
	//取得最新的10筆食譜
	@GetMapping("recipe/latest10Recipes")
	public List<RecipeCarouselDTO> findTop10RecipeByCreateTime(){
		List<RecipeCarouselDTO> recipes = recipeService.findTop10RecipesByCreateTime();
		
		if(recipes !=null && !recipes.isEmpty()) {
			return recipes;
		}
		return null;
	}
	
	//取得訪問數最高的10筆食譜
	@GetMapping("recipe/hottest10Recipes")
	public List<RecipeCarouselDTO> findTop10RecipeByVisitCount(){
		List<RecipeCarouselDTO> recipes = recipeService.findTop10RecipesByVisitCount();
		
		if(recipes !=null && !recipes.isEmpty()) {
			return recipes;
		}
		return null;
	}
	
	//取得特定類別的10筆食譜
	@GetMapping("/recipe/get10categoryRecipes")
	public List<RecipeCarouselDTO> find10RecipeByCategory(@RequestParam("cid") Integer cid){
		Category category = new Category();
		category.setId(cid);
		return recipeService.find10RecipeByCategory(category);
	}

	/*--------------------------------------------食譜建立頁使用controller ------------------------------------------------*/

	//新增食譜
//	@PostMapping("/recipe/addrecipe")
//	@ResponseBody
//	public String addRecipe(@RequestBody Recipes recipe,HttpSession session) {
//		Member member = (Member) session.getAttribute("loggedInMember");
//		if(member != null) {
//			
//			Boolean add = recipeService.addRecipe(member.getId(), recipe);
//			if(add) {
//				return "Y";
//			}
//			return "F";
//		}
//		return"N";
//	}
//	
	
	@PostMapping("test/uploadimg")
	public void sendPic(@RequestBody MultipartFile file) {
		ImageUploadUtil util = new ImageUploadUtil();
		System.out.println(file.getOriginalFilename());
		util.savePicture(file, 1, "recipe", "test");
		
		System.out.println(file);
		
	}
	
	//更新食譜
	@PutMapping("recipe/updaterecipe")
	public String updateRecipe(@RequestBody Recipes recipe,HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		if(member != null) {
			Boolean update = recipeService.updateRecipe(member.getId(), recipe);
			if(update) {
				return "Y";
			}
			return "F";
		}
		return "N";
	}
	
	//刪除食譜
	@DeleteMapping()
	public String deleteRecipe(@RequestParam("id") Integer id, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		if(member != null) {
			Boolean delete = recipeService.deleteById(id, member.getId());
			if(delete) {
				return "Y";
			}
			return "F";
		}
		return "N";
	}
	
	@GetMapping("/recipe/search")
	public ResponseEntity<List<Recipes>> searchRecipes(@RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize){
		
		SortCondition sortCondition = new SortCondition();
		sortCondition.setSortBy(keyword);
		sortCondition.setPage(page);
		sortCondition.setPageSize(pageSize);
		
		List<Recipes> resultList = recipeService.getRecipePage(sortCondition);
		
		return ResponseEntity.ok(resultList);
		
	}
	

}
