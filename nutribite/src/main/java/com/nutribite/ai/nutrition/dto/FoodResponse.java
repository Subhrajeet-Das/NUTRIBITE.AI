package com.nutribite.ai.nutrition.dto;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.model.MealType;
import lombok.*;
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class FoodResponse {
    private Long id; private String usdaId; private String name; private String category;
    private MealType mealType; private DietType dietType; private Double calories; private Double protein;
    private Double carbs; private Double fat; private Double fiber; private Double sugar; private Double sodium;
    private Double servingSize; private String servingUnit; private Boolean vegetarian; private Boolean vegan;
    private Boolean glutenFree; private Boolean dairyFree; private Boolean nutFree; private Boolean soyFree;
}
