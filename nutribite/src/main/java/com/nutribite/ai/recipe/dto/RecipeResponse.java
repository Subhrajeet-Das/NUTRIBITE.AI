package com.nutribite.ai.recipe.dto;
import com.nutribite.ai.model.enums.DietType; import com.nutribite.ai.nutrition.model.MealType; import lombok.*; import java.time.LocalDateTime; import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecipeResponse {
 private Long id; private String title; private String description; private String instructions; private String imageUrl; private Integer prepMinutes; private Integer cookMinutes; private Integer servings;
 private MealType mealType; private DietType dietType; private Double calories; private Double protein; private Double carbs; private Double fat; private Double fiber; private boolean favorite; private LocalDateTime createdAt; private List<Ingredient> ingredients;
 @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder public static class Ingredient {private Long id; private String name; private Double quantity; private String unit;}
}
