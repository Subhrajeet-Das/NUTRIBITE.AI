package com.nutribite.ai.recipe.controller;
import com.nutribite.ai.model.enums.DietType; import com.nutribite.ai.nutrition.model.MealType; import com.nutribite.ai.recipe.dto.*; import com.nutribite.ai.recipe.service.RecipeService; import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import org.springframework.data.domain.Page; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/recipes") @RequiredArgsConstructor
public class RecipeController { private final RecipeService service;
 @GetMapping public Page<RecipeResponse> list(@RequestParam(required=false) String q,@RequestParam(required=false) MealType mealType,@RequestParam(required=false) DietType dietType,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="12") int size){return service.list(q,mealType,dietType,page,size);}
 @GetMapping("/{id}") public RecipeResponse get(@PathVariable Long id){return service.get(id);}
 @PostMapping public RecipeResponse create(@Valid @RequestBody RecipeRequest r){return service.create(r);}
 @PutMapping("/{id}") public RecipeResponse update(@PathVariable Long id,@Valid @RequestBody RecipeRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){service.delete(id);}
 @PostMapping("/{id}/favorite") public RecipeResponse favorite(@PathVariable Long id){return service.favorite(id);}
 @DeleteMapping("/{id}/favorite") public void unfavorite(@PathVariable Long id){service.unfavorite(id);}
 @GetMapping("/favorites") public Page<RecipeResponse> favorites(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="12") int size){return service.favorites(page,size);}
}
