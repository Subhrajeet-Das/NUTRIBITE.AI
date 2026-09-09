package com.nutribite.ai.recipe.repository;
import com.nutribite.ai.recipe.entity.RecipeIngredient; import org.springframework.data.jpa.repository.JpaRepository;
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long>{}
