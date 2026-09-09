package com.nutribite.ai.nutrition.controller;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.dto.FoodResponse;
import com.nutribite.ai.nutrition.model.MealType;
import com.nutribite.ai.nutrition.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/foods") @RequiredArgsConstructor
public class FoodController {
 private final FoodService service;
 @GetMapping public Page<FoodResponse> getFoods(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.getFoods(page,size);}
 @GetMapping("/{id}") public FoodResponse getFood(@PathVariable Long id){return service.getFoodById(id);}
 @GetMapping("/search") public Page<FoodResponse> search(@RequestParam String query,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.searchFoods(query,page,size);}
 @GetMapping("/category/{category}") public Page<FoodResponse> category(@PathVariable String category,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.getByCategory(category,page,size);}
 @GetMapping("/meal/{mealType}") public Page<FoodResponse> meal(@PathVariable MealType mealType,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.getByMealType(mealType,page,size);}
 @GetMapping("/diet/{dietType}") public Page<FoodResponse> diet(@PathVariable DietType dietType,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.getByDietType(dietType,page,size);}
 @GetMapping("/meal/{mealType}/diet/{dietType}") public Page<FoodResponse> mealDiet(@PathVariable MealType mealType,@PathVariable DietType dietType,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.getByMealTypeAndDietType(mealType,dietType,page,size);}
 @GetMapping("/high-protein") public Page<FoodResponse> highProtein(@RequestParam(defaultValue="20") double minimumProtein,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.getHighProteinFoods(minimumProtein,page,size);}
 @GetMapping("/low-calorie") public Page<FoodResponse> lowCalories(@RequestParam(defaultValue="300") double maximumCalories,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size){return service.getLowCalorieFoods(maximumCalories,page,size);}
}
