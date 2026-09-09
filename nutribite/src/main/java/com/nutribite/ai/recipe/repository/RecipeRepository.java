package com.nutribite.ai.recipe.repository;
import com.nutribite.ai.model.enums.DietType; import com.nutribite.ai.nutrition.model.MealType; import com.nutribite.ai.recipe.entity.Recipe; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface RecipeRepository extends JpaRepository<Recipe,Long>{
 Page<Recipe> findByTitleContainingIgnoreCase(String title, Pageable pageable);
 Page<Recipe> findByMealType(MealType mealType, Pageable pageable);
 Page<Recipe> findByDietType(DietType dietType, Pageable pageable);
 Page<Recipe> findByMealTypeAndDietType(MealType mealType,DietType dietType,Pageable pageable);
}
