package com.dessertoasis.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.recipe.RecipeRepository;


@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepo;

	//利用id查詢
	public Recipes findById(Integer id) {
		Optional<Recipes> optional = recipeRepo.findById(id);

		if (optional.isPresent()) {
			return optional.get();
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


}