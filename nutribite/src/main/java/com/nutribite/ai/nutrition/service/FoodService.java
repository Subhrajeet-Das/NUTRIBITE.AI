package com.nutribite.ai.nutrition.service;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.dto.FoodResponse;
import com.nutribite.ai.nutrition.model.MealType;
import org.springframework.data.domain.Page;
public interface FoodService {
 Page<FoodResponse> getFoods(int page,int size); FoodResponse getFoodById(Long id); Page<FoodResponse> searchFoods(String query,int page,int size);
 Page<FoodResponse> getByCategory(String category,int page,int size); Page<FoodResponse> getByMealType(MealType mealType,int page,int size);
 Page<FoodResponse> getByDietType(DietType dietType,int page,int size); Page<FoodResponse> getByMealTypeAndDietType(MealType mealType,DietType dietType,int page,int size);
 Page<FoodResponse> getHighProteinFoods(double minimumProtein,int page,int size); Page<FoodResponse> getLowCalorieFoods(double maximumCalories,int page,int size);
}
