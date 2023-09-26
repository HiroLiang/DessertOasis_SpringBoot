package com.dessertoasis.demo.controller.recipe;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.recipe.Ingredient;
import com.dessertoasis.demo.model.recipe.IngredientList;
import com.dessertoasis.demo.model.recipe.IngredientListRepository;
import com.dessertoasis.demo.model.recipe.IngredientRepository;
import com.dessertoasis.demo.model.recipe.PicturesDTO;
import com.dessertoasis.demo.model.recipe.RecipeCarouselDTO;
import com.dessertoasis.demo.model.recipe.RecipeCmsTable;
import com.dessertoasis.demo.model.recipe.RecipeCreateDTO;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeFrontDTO;
import com.dessertoasis.demo.model.recipe.RecipeIngredientKey;
import com.dessertoasis.demo.model.recipe.RecipeRepository;
import com.dessertoasis.demo.model.recipe.RecipeSteps;
import com.dessertoasis.demo.model.recipe.RecipeStepsRepository;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.CategoryService;
import com.dessertoasis.demo.service.recipe.RecipeService;

import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;

@RestController
@MultipartConfig
public class RecipeController {

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeRepository recipeRepo;

	@Autowired
	private RecipeStepsRepository stepRepo;

	@Autowired
	private CategoryService cateService;

	@Autowired
	private ImageUploadUtil imgUtil;

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

	// 透過食譜名稱模糊搜尋食譜
	@GetMapping("recipe/recipeTitle")
	public List<Recipes> findRecipeByTitle(@RequestParam("rt") String recipeTitle) {
		String nameLikeRecipe = "%" + recipeTitle + "%";
		List<Recipes> optional = recipeRepo.findRecipeByTiltleLike(nameLikeRecipe);

		if (optional != null) {
			return optional;
		}
		return null;
	}

	// 透過難易度搜尋食譜
	@GetMapping("recipe/difficulty")
	public List<Recipes> findRecipeByDifficulty(@RequestParam("dif") String difficulty) {
		return recipeRepo.findRecipeByDifficulty(difficulty);
	}

	// 測試取得食譜
	@GetMapping("recipe/getRecipe")
	@ResponseBody
	public Recipes getRecipeById(@RequestParam String recipeId) {
		System.out.println(recipeId);
		Optional<Recipes> findById = recipeRepo.findById(Integer.parseInt(recipeId));

		if (findById.isPresent() && findById.get().getRecipeStatus() != -1) {
			Recipes recipe = findById.get();
			Integer recipeMonthlyVisitCount = recipe.getRecipeMonthlyVisitCount();
			System.err.println(recipeMonthlyVisitCount);
			recipe.setRecipeMonthlyVisitCount(recipeMonthlyVisitCount+1);
			recipeRepo.save(recipe);
			return recipe;
		}
		return null;
	}
	
	@GetMapping("recipe/getCmsRecipeById")
	@ResponseBody
	public Recipes getCmsRecipeById(@RequestParam String recipeId) {
		System.out.println(recipeId);
		Optional<Recipes> findById = recipeRepo.findById(Integer.parseInt(recipeId));

		if (findById.isPresent() && findById.get().getRecipeStatus() != -1) {
			Recipes recipe = findById.get();
			return recipe;
		}
		return null;
	}

	/*--------------------------------------------食譜主頁使用controller ------------------------------------------------*/

	// 取得最新的10筆食譜
	@GetMapping("recipe/latest10Recipes")
	public List<RecipeCarouselDTO> findTop10RecipeByCreateTime() {
		List<RecipeCarouselDTO> recipes = recipeService.findTop10RecipesByCreateTime();

		if (recipes != null && !recipes.isEmpty()) {
			return recipes;
		}
		return null;
	}

	// 取得訪問數最高的10筆食譜
	@GetMapping("recipe/hottest10Recipes")
	public List<RecipeCarouselDTO> findTop10RecipeByVisitCount() {
		List<RecipeCarouselDTO> recipes = recipeService.findTop10RecipesByVisitCount();

		if (recipes != null && !recipes.isEmpty()) {
			return recipes;
		}
		return null;
	}

	// 取得特定類別的10筆食譜
	@GetMapping("recipe/get10categoryRecipes")
	public List<RecipeCarouselDTO> find10RecipeByCategory(@RequestParam("cid") Integer cid) {

		return recipeService.find10RecipeByCategory(cid);
	}

	/*--------------------------------------------食譜建立頁使用controller ------------------------------------------------*/
	@Autowired
	private IngredientRepository ingreRepo;

	@Autowired
	private IngredientListRepository ingreListRepo;

	@Autowired
	private EntityManager entityManager;

	// 新增食譜
//	@Transactional
	@PostMapping(value = "/recipe/addrecipe")
	@ResponseBody
	public String addRecipe(@RequestBody RecipeCreateDTO createDto, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		if (member != null) {
			String add = recipeService.addRecipe(member.getId(), createDto);
			/*-------------------------------使用ImageUploadUtil儲存圖片並接收回傳儲存位置區塊--------------------------------------*/
//			Recipes recipe = new Recipes();
//			recipe.setRecipeTitle(recipeTitle);
//			recipe.setRecipeAuthor(member);
//			recipe.setCookingTime(cookingTime);
//			recipe.setRecipeCreateDate(LocalDateTime.now());
//			recipe.setRecipeIntroduction(recipeIntroduction);
//			recipe.setRecipeStatus(1);
//
//			String mainPicUrl = imgUtil.savePicture(pictureURL, 1, "recipe", recipeTitle);
//			recipe.setPictureURL(mainPicUrl);
//
//			IngredientList ingredientList = new IngredientList();
//			Ingredient ingredient = new Ingredient();
//			int ingredientsIndex = ingredients.size() / 2;
//			for (int i = 1; i <= ingredientsIndex; i++) {
//				String ingredientNameKey = "ingredientName" + i;
//				String ingredientQtyKey = "ingredientQuantity" + i;
//				String ingredientName = ingredients.get(ingredientNameKey);
//				String ingredientQty = ingredients.get(ingredientQtyKey);
//				if (ingredientQty != null && !ingredientQty.isEmpty()) {
//					try {
//						ingredientList.setIngredientQuantity(Integer.parseInt(ingredientQty));
//					} catch (NumberFormatException e) {
//						// 处理无法解析为整数的情况
//					}
//				}
////	    		ingredientList.setIngredientQuantity(Integer.parseInt(ingredientQty));
//				ingredient.setIngredientName(ingredientName);
//			}
//			RecipeSteps recipeSteps = new RecipeSteps();
//			recipeSteps.setRecipe(recipe);
//			int stepsIndex = steps.size() / 2;
//			for (int i = 1; i <= stepsIndex; i++) {
//				recipeSteps.setStepNumber(i);
//				String stepImgKey = "recipeStep" + i;
//				MultipartFile stepImg = steps.get(stepImgKey);
//				String picUrl = imgUtil.savePicture(stepImg, 1, "recipe", recipeTitle);
//				recipeSteps.setStepPicture(picUrl);
//			}
//			int stepIntroIndex = stepDescriptions.size() / 2;
//			for (int i = 1; i <= stepIntroIndex; i++) {
//				String stepIntroIndexKey = "recipeIntroduction" + i;
//				String stepIntro = stepDescriptions.get(stepIntroIndexKey);
//				recipeSteps.setStepContext(stepIntro);
//			}
//
//			Boolean add = recipeService.addRecipe(1, recipe);
			if (add != null) {
				return add; // 新增成功,回傳食譜id
			}
			return "F";// 新增失敗 recipebean資料不符或是找不到對應使用者
			/*-------------------------------使用ImageUploadUtil儲存圖片並接收回傳儲存位置區塊--------------------------------------*/
		}
		return "N";// 新增失敗 找不到session中的使用者資料
	}

	/*----------------------------------------------圖檔處理回傳儲存路徑Controller------------------------------------------------------------------*/
	@SuppressWarnings("resource")
	@PostMapping(path = "test/uploadimg")
	public List<String> sendPic(@RequestBody List<PicturesDTO> pictures, HttpSession session) {
		/*---------設定儲存路徑---------*/
		Member member = (Member) session.getAttribute("loggedInMember");

//TODO 調整路徑
//		final String uploadPath = "D:/dessertoasis-vue/public/";
		final String uploadPath = "C:/Users/iSpan/Documents/dessertoasis-vue/public/";
//		Member member = (Member) session.getAttribute("loggedInMember");
//		Recipes recipe = (Recipes) session.getAttribute("recipeId");

		/*---------msg儲存準備回傳的字串陣列---------*/
		List<String> msg = new ArrayList<>();

		/*---------判斷是否有接收到資料---------*/
		if (!pictures.isEmpty() && pictures != null) {

			/*---------儲存成品圖區塊---------*/
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
			String timestamp = LocalDateTime.now().format(formatter);
			if (member != null) {
				// 調整存入資料庫的路徑位置
				String sqlPath = "images/recipe" + "/" + member.getId() + "/";
				String userFolder = uploadPath + sqlPath;

				File folder = new File(userFolder);
				if (!folder.exists()) {
					folder.mkdirs();
				}
//			Optional<Recipes> getRecipe = recipeRepo.findById(recipe.getId());
//			if (getRecipe.isPresent()) {
//				Recipes useRecipe = getRecipe.get();

				// 判斷陣列內有無檔案 避免讀取空值錯誤
				if (pictures.get(0) != null) {
					/*----------取得前端傳來的圖檔字串----------*/
					String mainPic = pictures.get(0).getBase64Content();
					String mainPicName = pictures.get(0).getFileName();
					/*----------取得前端傳來的圖檔字串----------*/

					/*----------寫入檔案及儲存位置串接字串----------*/
					String mainFileName = mainPicName.substring(0, mainPicName.lastIndexOf("."));// 將副檔名與檔名拆開 取得檔名
					String mainExtension = mainPicName.substring(mainPicName.lastIndexOf("."));// 將副檔名與檔名拆開 取得副檔名
					String mainUniqueName = mainFileName + "_" + timestamp + mainExtension;// 將時間串入檔名

					byte[] mainDecode = Base64.getDecoder().decode(mainPic);
					File mainfile = new File(userFolder + mainUniqueName);

					try {
						FileOutputStream mainfileOutputStream = new FileOutputStream(mainfile);
						mainfileOutputStream.write(mainDecode);
						mainfileOutputStream.flush();
						/*----------寫入檔案及儲存位置串接字串----------*/

						/*----------將儲存位置準備回傳前端----------*/
						msg.add("-1");
						msg.add(sqlPath + mainUniqueName);
						/*----------將儲存位置準備回傳前端----------*/
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						msg.add("N");
						msg.add("MainFile not found");
						return msg;
					} catch (IOException e) {
						e.printStackTrace();
						msg.add("N");
						msg.add("MainFile upload failed");
						return msg;
					}
				}
				/*---------儲存成品圖區塊---------*/
//					useRecipe.setPictureURL(userFolder + mainUniqueName);
//					recipeRepo.save(useRecipe);

				// 處理步驟圖片
//						List<RecipeSteps> steps = stepRepo.findRecipeStepsByRecipeId(recipe.getId());
//					if (!steps.isEmpty()) {

				/*---------儲存步驟圖區塊---------*/
				for (int i = 1; i < pictures.size(); i++) {
//							RecipeSteps step = steps.get(i);

					// 判斷陣列內有無檔案 避免讀取空值錯誤
					if (pictures.get(i) != null) {
						String steptimestamp = LocalDateTime.now().format(formatter);

						/*----------取得前端傳來的圖檔字串----------*/
						String stepPic = pictures.get(i).getBase64Content();
						String originalfileName = pictures.get(i).getFileName();
						/*----------取得前端傳來的圖檔字串----------*/

						/*----------寫入檔案及儲存位置串接字串----------*/
						String fileName = originalfileName.substring(0, originalfileName.lastIndexOf("."));
						String extension = originalfileName.substring(originalfileName.lastIndexOf("."));
						String uniqueName = fileName + "_" + steptimestamp + extension;

						try {
							byte[] decode = Base64.getDecoder().decode(stepPic);
							File file = new File(userFolder + uniqueName);
							FileOutputStream fileOutputStream = new FileOutputStream(file);
							fileOutputStream.write(decode);
							fileOutputStream.flush();
							fileOutputStream.close();
							/*----------寫入檔案及儲存位置串接字串----------*/

							/*----------將儲存位置準備回傳前端----------*/
							msg.add(sqlPath + uniqueName);
							/*----------將儲存位置準備回傳前端----------*/

						} catch (FileNotFoundException e) {
							e.printStackTrace();
							msg.add("N");
							msg.add("MainFile not found");
							return msg;
						} catch (IOException e) {
							e.printStackTrace();
							msg.add("N");
							msg.add("MainFile upload failed");
							return msg;
						}
//							step.setStepPicture(userFolder + uniqueName);
//							stepRepo.save(step);
					}
				}
				return msg; // 送出處理好的圖片儲存位址,成品圖找到-1後的一筆，其後皆為步驟圖片
				/*---------儲存步驟圖路徑串接區塊---------*/
			}
//				}
//			}

		}
		msg.add("F");
		return msg; // 沒有資料則回傳F
	}

	/*----------------------------------------------圖檔處理回傳儲存路徑Controller------------------------------------------------------------------*/
	/*----------------------------------------------處理前端請求回傳base64給前端顯示Controller------------------------------------------------------------------*/

//	@PostMapping("recipe/getPic")
//	@ResponseBody
	public ResponseEntity<String> getPic(@RequestBody Integer recipeId) {
		Optional<Recipes> findById = recipeRepo.findById(recipeId);
		if (findById.isPresent()) {
			// TODO 調整路徑
			String userPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\";
//			String userPath = "D:/dessertoasis-vue/public/";
			Recipes recipe = findById.get();
			String pictureURL = recipe.getPictureURL();
			try {
				BufferedImage read = ImageIO.read(new File(userPath + pictureURL));

				if (read != null) {
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					int lastDotIndex = pictureURL.lastIndexOf(".");
					if (lastDotIndex > 0) {
						String formatName = pictureURL.substring(lastDotIndex + 1);
						ImageIO.write(read, formatName, byteArrayOutputStream);
						byte[] img = byteArrayOutputStream.toByteArray();
						String base64Img = Base64.getEncoder().encodeToString(img);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.parseMediaType("image/" + formatName));

						return ResponseEntity.ok().headers(headers).body(base64Img);
					}

				} else {
					ResponseEntity.ok("Image not found");
				}

			} catch (IOException e) {
				e.printStackTrace();
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("F");
			}
		}
		return ResponseEntity.ok("recipe not found");
	}

	/*----------------------﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀發送base64給前端範例﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀--------------------------*/
	@PostMapping("recipe/getPic")
	@ResponseBody
	public ResponseEntity<String> getPicByGetPicture(@RequestBody Integer recipeId) {
		Optional<Recipes> findById = recipeRepo.findById(recipeId);
		if (findById.isPresent()) {
			// TODO 調整路徑
//			String userPath ="D:/dessertoasis-vue/public/";
			String userPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\";
			Recipes recipe = findById.get();
			String pictureURL = recipe.getPictureURL();

			/*-------------------getPicture方法   第一個參數接收自己的儲存路徑， 第二個參數接收存於資料庫的路徑(範例: images/recipe/1/3584160_20230914005256937.jpg  等等)---------------------*/
			List<String> picture = imgUtil.getPicture(userPath, pictureURL);

			HttpHeaders headers = new HttpHeaders();
			/*-------------------getPicture回傳值[0]為檔案MIME字串(如image/png 等)  將其設定到 headers中----------------------------*/
			headers.setContentType(MediaType.parseMediaType(picture.get(0)));

			/*-------------------getPicture回傳值[1]為檔案base64字串  將其設定到body中----------------------------*/
			return ResponseEntity.ok().headers(headers).body(picture.get(1));
			/*------------------------------------前端接收圖片方式請看vue專案 OneRecipePage.vue 中的getImg函式------------------------------------*/
		}
		return ResponseEntity.ok("recipe not found");
	}
	/*----------------------﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀發送base64給前端範例﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀--------------------------*/

	/* 取得步驟圖片 base64回傳前端 */
	@PostMapping("recipe/getStepPics")
	@ResponseBody
	public List<String> getStepPicture(@RequestBody Integer recipeId) {
		Optional<Recipes> findById = recipeRepo.findById(recipeId);
		List<String> pictureDatas = new ArrayList<>();
		if (findById.isPresent()) {

			// TODO 調整路徑
//			String userPath ="D:/dessertoasis-vue/public/";
			String userPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\";
			Recipes recipe = findById.get();
			for (int i = 0; i < recipe.getRecipeSteps().size(); i++) {
				String stepPicPath = recipe.getRecipeSteps().get(i).getStepPicture();
				List<String> StepPictures = imgUtil.getPicture(userPath, stepPicPath);
				for (int j = 0; j < StepPictures.size(); j++) {
					pictureDatas.add(StepPictures.get(j));
				}
			}
			System.out.println(pictureDatas.size());
			return pictureDatas;

		}
		pictureDatas.add("recipe not found");
		return pictureDatas;
	}

	/*----------------------------------------------處理前端請求回傳base64給前端顯示Controller------------------------------------------------------------------*/

//	public List<Category> getRecipeCategory(@RequestBody Integer categoryId){
//		
//	}

	// 更新食譜
	@PutMapping("recipe/updaterecipe")
	@ResponseBody
	public String updateRecipe(@RequestBody Recipes recipe, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
//		if (member != null) {
		Boolean update = recipeService.updateRecipe(recipe);
		if (update) {
			return "Y";
		}
		return "F";
//		}
//		return "N";
	}

	// 刪除食譜
	@PostMapping("recipe/deleterecipe")
	public String deleteRecipe(@RequestParam("recipeId") Integer recipeId, HttpSession session) {

		Member member = (Member) session.getAttribute("loggedInMember");
		if (member != null && member.getAccess().equals(MemberAccess.ADMIN)) {
			Boolean delete = recipeService.deleteById(recipeId, member.getId());
			if (delete) {
				return "Y";
			}
			return "F";
		}
		return "N";
	}

	// 重新啟用食譜
	@PostMapping("recipe/republishrecipe")
	public String republishRecipe(@RequestParam("recipeId") Integer recipeId, HttpSession session) {
		Member member = (Member) session.getAttribute("loggedInMember");
		if (member != null && member.getAccess().equals(MemberAccess.ADMIN)) {
			Boolean republish = recipeService.republishById(recipeId, member.getId());
			if (republish) {
				return "Y";
			}
			return "F";
		}
		return "N";
	}

	/*----------------------------------------------處理食譜總頁數Controller------------------------------------------------------------------*/
	@PostMapping("/recipe/pages")
	public Integer getPages(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		// 送出條件查詢總頁數
		Integer pages = recipeService.getPages(sortCon);
		return pages;
	}

	/*----------------------------------------------後台資料查詢Controller------------------------------------------------------------------*/
	@PostMapping("/recipe/pagenation")
	public List<RecipeCmsTable> getRecipePage(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		// 送出查詢條件給service，若有結果則回傳list
		List<RecipeCmsTable> result = recipeService.getRecipePagenation(sortCon);
		if (result != null) {
			System.out.println(result);
			return result;
		}
		return null;
	}

	/*----------------------------------------------前台資料查詢Controller------------------------------------------------------------------*/

	@PostMapping("/recipe/recipeFrontPagenation")
	public List<RecipeFrontDTO> getFrontRecipePage(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		// 送出查詢條件給service，若有結果則回傳list
		List<RecipeFrontDTO> result = recipeService.getFrontRecipePagenation(sortCon);
		if (result != null) {
			System.out.println(result);
			return result;
		}
		return null;
	}

}
