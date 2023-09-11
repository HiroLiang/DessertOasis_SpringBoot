package com.dessertoasis.demo.model.recipe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Ingredient findByIngredientName(String ingredientName);

}
