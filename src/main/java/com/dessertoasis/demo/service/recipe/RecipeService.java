package com.dessertoasis.demo.service.recipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.recipe.RecipeCategory;
import com.dessertoasis.demo.model.recipe.RecipeCategoryRepository;
import com.dessertoasis.demo.model.recipe.RecipeCmsTable;
import com.dessertoasis.demo.model.recipe.RecipeCreateDTO;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeFrontDTO;
import com.dessertoasis.demo.model.recipe.RecipeIngredientKey;
import com.dessertoasis.demo.model.recipe.Ingredient;
import com.dessertoasis.demo.model.recipe.IngredientList;
import com.dessertoasis.demo.model.recipe.IngredientListRepository;
import com.dessertoasis.demo.model.recipe.IngredientRepository;
import com.dessertoasis.demo.model.recipe.RecipeCarouselDTO;
import com.dessertoasis.demo.model.recipe.RecipeRepository;
import com.dessertoasis.demo.model.recipe.RecipeSteps;
import com.dessertoasis.demo.model.recipe.RecipeStepsRepository;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private RecipeStepsRepository stepRepo;

	@Autowired
	private RecipeCategoryRepository recaRepo;

	@Autowired
	private IngredientRepository ingreRepo;

	@Autowired
	private IngredientListRepository ingreListRepo;

	// 利用id查詢
	public RecipeDTO findById(Integer id) {
		Optional<Recipes> optional = recipeRepo.findById(id);

		if (optional.isPresent()) {
			Recipes recipe = optional.get();
			if (recipe.getRecipeStatus() != 2) {

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
		}
		return null;
	}

	// 查詢全部
	public List<Recipes> findAllRecipes() {
		List<Recipes> result = recipeRepo.findAll();
		return result;
	}

	// 新增
	public void insert(Recipes recipe) {
		recipeRepo.save(recipe);
	}

	/*--------------------------------------------食譜主頁使用service ------------------------------------------------*/

	// 取得最新的10筆食譜
	public List<RecipeCarouselDTO> findTop10RecipesByCreateTime() {
		List<Recipes> latest10Recipes = recipeRepo.findTop10RecipeByCreateTime();
		List<RecipeCarouselDTO> latest10RecipeDTO = new ArrayList<>();

		if (latest10Recipes != null && !latest10Recipes.isEmpty()) {
			for (int i = 0; i < latest10Recipes.size(); i++) {
				if (latest10Recipes.get(i).getRecipeStatus() != 2) {

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
		}
		return latest10RecipeDTO;
	}

	// 取得訪問數最高的10筆食譜
	public List<RecipeCarouselDTO> findTop10RecipesByVisitCount() {
		List<Recipes> hottest10Recipes = recipeRepo.findTop10RecipeByVisitCount();
		List<RecipeCarouselDTO> hottest10RecipeDTO = new ArrayList<>();

		if (hottest10Recipes != null && !hottest10Recipes.isEmpty()) {
			for (int i = 0; i < hottest10Recipes.size(); i++) {
				if (hottest10Recipes.get(i).getRecipeStatus() != 2) {

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
		}
		return hottest10RecipeDTO;
	}

	public List<RecipeCarouselDTO> find10RecipeByCategory(Integer categoryId) {
		Optional<Category> categories = cateRepo.findById(categoryId);
		if (categories.isPresent()) {
			Category category = categories.get();

			List<Recipes> recipes = recipeRepo.findRecipesByCategory(category);
			List<RecipeCarouselDTO> categoryRecipeDTO = new ArrayList<>();
			if (recipes != null && !recipes.isEmpty()) {
				for (int i = 0; i < Math.min(10, recipes.size()); i++) {
					if (recipes.get(i).getRecipeStatus() != 2) {

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
			}
			return categoryRecipeDTO;
		}
		return null;
	}
	/*--------------------------------------------食譜建立頁使用service ------------------------------------------------*/

	// 新增食譜
	public String addRecipe(Integer id, RecipeCreateDTO cDto) {
		Optional<Member> optional = memberRepo.findById(id);
		if (optional.isPresent()) {

			int counter = 0;
//			System.out.println("out for");
			for (Ingredient ingredientData : cDto.getIngredients()) {
//				System.out.println("in for");
//				System.out.println(
//						"name-------------------------------------------" + ingredientData.getIngredientName());
				Ingredient existIngredient = ingreRepo.findByIngredientName(ingredientData.getIngredientName());
				if (existIngredient != null) {
					ingredientData = existIngredient;
				} else {
					ingreRepo.save(ingredientData);
				}
				cDto.getRecipe().getIngredientList().get(counter).setIngredient(ingredientData);
				counter++;
			}
			cDto.getRecipe().setRecipeAuthor(optional.get());
			cDto.getRecipe().setRecipeCreateDate(LocalDateTime.now());
			cDto.getRecipe().setRecipeStatus(1);

			if (cDto.getRecipe().getCookingTime() == null) {
				cDto.getRecipe().setCookingTime(0);
			}
			cDto.getRecipe().setRecipeMonthlyVisitCount(0);
//			System.out.println(cDto.getRecipe().getId()); // 持久化前沒有id
			Recipes save = recipeRepo.save(cDto.getRecipe());
//			System.out.println(cDto.getRecipe().getId()); // 持久化後能取得id
			/*--------------------------------------------儲存食譜類別區塊-----------------------------------------------------*/
			List<RecipeCategory> recipecategories = new ArrayList<>();
			List<Category> categories = cDto.getCategories();
			/*------無論有無設定分類一律設定為食譜分類---------*/
			Optional<Category> rCate = cateRepo.findById(3);
			Category cateForReci = rCate.get();
			RecipeCategory defualtCategory = new RecipeCategory();
			defualtCategory.setCategory(cateForReci);
			defualtCategory.setRecipe(save);
			/*------無論有無設定分類一律設定為食譜分類---------*/
//			System.out.println("before for");

			/*------有設定分類則將分類逐一設定---------*/
			if (!categories.isEmpty()) {
//				System.out.println("in if");
				for (int i = 0; i < categories.size(); i++) {
//				System.out.println("for");
					Integer categoryId = categories.get(i).getId();
					if (categoryId != null) {
						Optional<Category> existCategory = cateRepo.findById(categoryId);
//				System.out.println("before if"+existCategory.isPresent());
						if (existCategory.isPresent()) {
							Category category = existCategory.get();
							RecipeCategory recipeCategory = new RecipeCategory();
							recipeCategory.setCategory(category);
							recipeCategory.setRecipe(save);
							recipecategories.add(recipeCategory);
//					System.out.println(" before save");
//					System.out.println(" after save");
						}
					}
				}
			}
			/*------有無設定分類一律設定則將分類逐一設定---------*/
			recipecategories.add(defualtCategory);
			recaRepo.saveAll(recipecategories);
			/*--------------------------------------------儲存食譜類別區塊-----------------------------------------------------*/

			return save.getId().toString();
		}
		return null;
	}

	// 修改食譜
	public Boolean updateRecipe(Recipes recipe) {
//		Optional<Member> member = memberRepo.findById(memberId);
		Optional<Recipes> optional = recipeRepo.findById(recipe.getId());
		if (optional.isPresent()) {
			Recipes existRecipe = optional.get();
			if (existRecipe != null) {
				existRecipe.setRecipeTitle(recipe.getRecipeTitle());
				existRecipe.setRecipeIntroduction(recipe.getRecipeIntroduction());
				existRecipe.setCookingTime(recipe.getCookingTime());
				existRecipe.setIngredientPersons(recipe.getIngredientPersons());
				existRecipe.setPictureURL(recipe.getPictureURL());
				existRecipe.setRecipeMonthlyVisitCount(recipe.getRecipeMonthlyVisitCount());
//				System.err.println(recipe.getIngredientPersons());
//				List<IngredientList> existIngredientList = existRecipe.getIngredientList();
				for (int i = 0; i < recipe.getIngredientList().size(); i++) {
					IngredientList newIngredientList = recipe.getIngredientList().get(i);

					if (newIngredientList.getIngredient().getIngredientName() != null) {
						Ingredient findByIngredientName = ingreRepo
								.findByIngredientName(newIngredientList.getIngredient().getIngredientName());
						System.err.println(findByIngredientName);
						if (findByIngredientName != null) {
//							Ingredient existIngredient = ingredientFindById.get();
//							System.err.println(findByIngredientName);
//							System.err.println("find exist ingre");
							recipe.getIngredientList().get(i).setIngredient(findByIngredientName);
						} else {
							// 初始化原先帶有的id
							newIngredientList.getIngredient().setId(null);
							Ingredient savedIngre = ingreRepo.save(newIngredientList.getIngredient());
//							System.err.println(savedIngre);
							recipe.getIngredientList().get(i).setIngredient(savedIngre);
//							System.err.println("new ingre");
//							System.err.println(savedIngre.getIngredientName());
						}
					}

					if (newIngredientList.getId() != null) {

						Optional<IngredientList> findOldIngreList = ingreListRepo.findById(newIngredientList.getId());
						if (findOldIngreList.isPresent()) {

							IngredientList oldIngreList = findOldIngreList.get();
							oldIngreList.setIngredient(recipe.getIngredientList().get(i).getIngredient());
							oldIngreList
									.setIngredientQuantity(recipe.getIngredientList().get(i).getIngredientQuantity());
							oldIngreList.setIngredientUnit(recipe.getIngredientList().get(i).getIngredientUnit());
						}
					} else {
						ingreListRepo.save(newIngredientList);
					}
				}
//				List<Integer> stepIds = new ArrayList<>();
				List<RecipeSteps> newRecipeSteps = recipe.getRecipeSteps();
				for (int i = 0; i < newRecipeSteps.size(); i++) {
//					stepIds.add(newRecipeSteps.get(i).getId());
					if (newRecipeSteps.get(i).getId() != null) {
						System.err.println("have id");
						Optional<RecipeSteps> stepFindById = stepRepo.findById(newRecipeSteps.get(i).getId());
						if (stepFindById.isPresent()) {
							System.err.println("find old");
							RecipeSteps oldRecipeSteps = stepFindById.get();
							oldRecipeSteps.setStepContext(newRecipeSteps.get(i).getStepContext());
							if (newRecipeSteps.get(i).getStepPicture() != null
									&& !newRecipeSteps.get(i).getStepPicture().isEmpty()) {
								oldRecipeSteps.setStepPicture(newRecipeSteps.get(i).getStepPicture());
								System.err.println("get new pic");
							}
						}
					} else {
						stepRepo.save(newRecipeSteps.get(i));
						System.err.println("save new ");
					}
				}

				existRecipe.setRecipeSteps(recipe.getRecipeSteps());
				recipeRepo.save(existRecipe);
			}
			return true;
		}
		return false;
	}

	// 以id刪除食譜
	public Boolean deleteById(Integer recipeId, Integer memberId) {
		Optional<Member> member = memberRepo.findById(memberId);
		Optional<Recipes> recipe = recipeRepo.findById(recipeId);

		if (recipe.isPresent()) {
			Recipes recipeData = recipe.get();
//			if (recipeData.getRecipeAuthor().getId().equals(member.get().getId())) {
			// 註銷食譜 使狀態設為-1
			recipeData.setRecipeStatus(2);
			recipeRepo.save(recipeData);
			return true;
//			}
		}
		return false;
	}
	
	public Boolean republishById(Integer recipeId, Integer memberId) {
		Optional<Member> member = memberRepo.findById(memberId);
		Optional<Recipes> recipe = recipeRepo.findById(recipeId);

		if (recipe.isPresent()) {
			Recipes recipeData = recipe.get();
//			if (recipeData.getRecipeAuthor().getId().equals(member.get().getId())) {
			// 註銷食譜 使狀態設為-1
			recipeData.setRecipeStatus(1);
			recipeRepo.save(recipeData);
			return true;
//			}
		}
		return false;
	}

	/*--------------------------------------------後台Criteria ------------------------------------------------*/
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PageSortService pService;

	// 動態條件搜索
	public List<RecipeCmsTable> getRecipePagenation(SortCondition sortCon) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		// 決定select table
		CriteriaQuery<RecipeCmsTable> cq = cb.createQuery(RecipeCmsTable.class);

		// 決定select.join表格
		Root<Recipes> root = cq.from(Recipes.class);
		Join<Recipes, Member> join = root.join("recipeAuthor");

		// 決定查詢 column
		cq.multiselect(root.get("id"), root.get("recipeTitle"), join.get("fullName"), root.get("recipeCreateDate"),
				root.get("recipeStatus"), root.get("recipeMonthlyVisitCount"));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Recipes recipes = new Recipes();
		Predicate pre = pService.checkRecipeCondition(root, join, predicate, sortCon, cb, recipes);

		// 填入 where 條件
		cq.where(pre);

		// 排序條件
		if (sortCon.getSortBy() != null) {
			if (pService.hasProperty(recipes, sortCon.getSortBy())) {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(root.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(root.get(sortCon.getSortBy())));
				}
			} else {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(join.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(join.get(sortCon.getSortBy())));
				}
			}

		}

		// 分頁
		TypedQuery<RecipeCmsTable> query = em.createQuery(cq);
		query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
		query.setMaxResults(sortCon.getPageSize());

		// 送出請求
		List<RecipeCmsTable> list = query.getResultList();
		if (list != null) {
			return list;
		}
		return null;
	}

	public Integer getPages(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		// 決定select.join表格
		Root<Recipes> root = cq.from(Recipes.class);
		Join<Recipes, Member> join = root.join("recipeAuthor");

		// 決定查詢 column
		cq.select(cb.count(root));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Recipes recipes = new Recipes();
		Predicate pre = pService.checkRecipeCondition(root, join, predicate, sortCon, cb, recipes);
		cq.where(pre);

		// 查詢傯頁數
		Long totalRecords = em.createQuery(cq).getSingleResult();
		Integer totalPages = (int) Math.ceil((double) totalRecords / sortCon.getPageSize());

		return totalPages;
	}

	/*--------------------------------------------前台Criteria ------------------------------------------------*/

	public List<RecipeFrontDTO> getFrontRecipePagenation(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定select table
		CriteriaQuery<RecipeFrontDTO> cq = cb.createQuery(RecipeFrontDTO.class);

		// 決定select.join表格
		Root<Recipes> root = cq.from(Recipes.class);
		Join<Recipes, Member> join = root.join("recipeAuthor");
//		Join<Recipes,Category> categoryJoin = root.join("recipeCategories");

//	    root.fetch("recipeCategories", JoinType.INNER);

		// 決定查詢 column
		cq.multiselect(root.get("id"), root.get("recipeTitle"),join.get("fullName"), root.get("pictureURL"),
				root.get("difficulty"), root.get("recipeIntroduction"));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Recipes recipes = new Recipes();
		Predicate pre = pService.checkRecipeCondition(root, join, predicate, sortCon, cb, recipes);

		// 填入 where 條件
		cq.where(pre);

		// 分頁
		TypedQuery<RecipeFrontDTO> query = em.createQuery(cq);
		query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
		query.setMaxResults(sortCon.getPageSize());

		// 送出請求
		List<RecipeFrontDTO> list = query.getResultList();
		if (list != null) {
			return list;
		}
		return null;
	}

}
