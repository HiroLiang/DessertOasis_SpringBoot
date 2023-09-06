package com.dessertoasis.demo.service.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.recipe.RecipeCategory;
import com.dessertoasis.demo.model.recipe.RecipeCategoryRepository;
import com.dessertoasis.demo.model.recipe.RecipeCreateDTO;
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
	public Boolean addRecipe(Integer id, Recipes recipe) {
		Optional<Member> optional = memberRepo.findById(id);
		if(optional.isPresent()) {
			Member member = optional.get();
			recipe.setRecipeAuthor(member);
			recipeRepo.save(recipe);
			return true;
		}
		return false;
	}
	
	//修改食譜
	public Boolean updateRecipe(Integer id,Recipes recipe) {
		Optional<Member> member = memberRepo.findById(id);
		Optional<Recipes> optional = recipeRepo.findById(recipe.getId());
		
		if(optional.isPresent()) {
			Recipes recipeData = optional.get();
			if(recipeData.getRecipeAuthor().getId().equals(member.get().getId())) {
				recipeRepo.save(recipe);
			}
			
			
//			RecipeDTO rDto = new RecipeDTO();
//			rDto.setId(recipe.getId());
//			rDto.setRecipeAuthorId(recipe.getRecipeAuthor().getId());
//			rDto.setRecipeTitle(recipe.getRecipeTitle());
//			List<RecipeCategory> recipeCategories = recipe.getRecipeCategories();
//			List<Integer> categoryIds = new ArrayList<>();
//			for (RecipeCategory rc : recipeCategories) {
//                categoryIds.add(rc.getCategory().getId());
//            }
//			rDto.setRecipeCategoryIds(categoryIds);
//			rDto.setPictureURL(recipe.getPictureURL());
//			rDto.setRecipeIntroduction(recipe.getRecipeIntroduction());
//			rDto.setCookingTime(recipe.getCookingTime());
//			rDto.setDifficulty(recipe.getDifficulty());
//			rDto.setRecipeStatus(recipe.getRecipeStatus());
//			
			return true;
		}
		return false;
	}
	
	//以id刪除食譜
	public Boolean deleteById(Integer recipeId, Integer memberId) {
		Optional<Member> member = memberRepo.findById(memberId);
		Optional<Recipes> recipe = recipeRepo.findById(recipeId);

		if (recipe.isPresent()) {
			Recipes recipeData = recipe.get();
			if(recipeData.getRecipeAuthor().getId().equals(member.get().getId())) {
				recipeRepo.deleteById(recipeId);
				return true;
			}
		}
		return false;
	}
	/*--------------------------------------------測試Criteria ------------------------------------------------*/
	@PersistenceContext
	private EntityManager em;
	
	//動態條件搜索
	public List<Recipes> getRecipePage(SortCondition sortCod){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		// 決定select table
		CriteriaQuery<Recipes> cq = cb.createQuery(Recipes.class);
		Root<Recipes> root = cq.from(Recipes.class);
		Predicate[] predicates = new Predicate[20];
		int counter = 0;
		if(sortCod.getSortBy() != null) {
			predicates[counter] = cb.like(root.get("recipeTitle"), "%" + sortCod.getSortBy() + "%");
			counter++;
		}
		CriteriaQuery<Recipes> select = cq.select(root).where(predicates);
		TypedQuery<Recipes> query = em.createQuery(select);
		query.setFirstResult((sortCod.getPage()-1)*sortCod.getPageSize());
		query.setMaxResults(sortCod.getPageSize());
		List<Recipes> list = query.getResultList();
		System.out.println(list);
		
		return list;

	}
	

	

}