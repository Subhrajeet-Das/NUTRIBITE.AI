package com.nutribite.ai.nutrition.service.impl;
import com.nutribite.ai.exception.FoodNotFoundException;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.dto.FoodResponse;
import com.nutribite.ai.nutrition.entity.Food;
import com.nutribite.ai.nutrition.model.MealType;
import com.nutribite.ai.nutrition.repository.FoodRepository;
import com.nutribite.ai.nutrition.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
 private final FoodRepository repository;
 private Pageable page(int p,int s){return PageRequest.of(Math.max(0,p),Math.min(Math.max(1,s),100),Sort.by("name").ascending());}
 private FoodResponse map(Food f){return FoodResponse.builder().id(f.getId()).usdaId(f.getUsdaId()).name(f.getName()).category(f.getCategory()).mealType(f.getMealType()).dietType(f.getDietType()).calories(f.getCalories()).protein(f.getProtein()).carbs(f.getCarbs()).fat(f.getFat()).fiber(f.getFiber()).sugar(f.getSugar()).sodium(f.getSodium()).servingSize(f.getServingSize()).servingUnit(f.getServingUnit()).vegetarian(f.getVegetarian()).vegan(f.getVegan()).glutenFree(f.getGlutenFree()).dairyFree(f.getDairyFree()).nutFree(f.getNutFree()).soyFree(f.getSoyFree()).build();}
 public Page<FoodResponse> getFoods(int p,int s){return repository.findAll(page(p,s)).map(this::map);}
 public FoodResponse getFoodById(Long id){return repository.findById(id).map(this::map).orElseThrow(()->new FoodNotFoundException("Food not found: "+id));}
 public Page<FoodResponse> searchFoods(String q,int p,int s){return repository.findByNameContainingIgnoreCase(q==null?"":q.trim(),page(p,s)).map(this::map);}
 public Page<FoodResponse> getByCategory(String c,int p,int s){return repository.findByCategoryIgnoreCase(c,page(p,s)).map(this::map);}
 public Page<FoodResponse> getByMealType(MealType m,int p,int s){return repository.findByMealType(m,page(p,s)).map(this::map);}
 public Page<FoodResponse> getByDietType(DietType d,int p,int s){return repository.findByDietType(d,page(p,s)).map(this::map);}
 public Page<FoodResponse> getByMealTypeAndDietType(MealType m,DietType d,int p,int s){return repository.findByMealTypeAndDietType(m,d,page(p,s)).map(this::map);}
 public Page<FoodResponse> getHighProteinFoods(double m,int p,int s){return repository.findHighProtein(m,page(p,s)).map(this::map);}
 public Page<FoodResponse> getLowCalorieFoods(double m,int p,int s){return repository.findLowCalorie(m,page(p,s)).map(this::map);}
}
