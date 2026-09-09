package com.nutribite.ai.recipe.dto;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.model.MealType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecipeRequest {
 @NotBlank private String title; private String description; private String instructions; private String imageUrl;
 private Integer prepMinutes; private Integer cookMinutes; private Integer servings; private MealType mealType; private DietType dietType;
 private Double calories; private Double protein; private Double carbs; private Double fat; private Double fiber;
 private List<IngredientRequest> ingredients;
 @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder public static class IngredientRequest {private String name; private Double quantity; private String unit;}
}
