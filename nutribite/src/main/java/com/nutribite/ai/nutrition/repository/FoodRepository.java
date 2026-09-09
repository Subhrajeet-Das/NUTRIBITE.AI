package com.nutribite.ai.nutrition.repository;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.entity.Food;
import com.nutribite.ai.nutrition.model.MealType;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface FoodRepository extends JpaRepository<Food,Long> {
    Optional<Food> findByUsdaId(String usdaId);
    Page<Food> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Food> findByCategoryIgnoreCase(String category, Pageable pageable);
    Page<Food> findByMealType(MealType mealType, Pageable pageable);
    Page<Food> findByDietType(DietType dietType, Pageable pageable);
    Page<Food> findByMealTypeAndDietType(MealType mealType,DietType dietType,Pageable pageable);
    @Query("select f from Food f where f.protein >= :minProtein") Page<Food> findHighProtein(@Param("minProtein") Double minProtein,Pageable pageable);
    @Query("select f from Food f where f.calories <= :maxCalories") Page<Food> findLowCalorie(@Param("maxCalories") Double maxCalories,Pageable pageable);
}
