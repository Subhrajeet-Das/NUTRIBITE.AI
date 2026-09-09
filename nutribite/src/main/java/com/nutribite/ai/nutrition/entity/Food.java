package com.nutribite.ai.nutrition.entity;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.model.MealType;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "foods", indexes = {
    @Index(name="idx_food_name",columnList="name"), @Index(name="idx_food_meal_type",columnList="meal_type"),
    @Index(name="idx_food_diet_type",columnList="diet_type"), @Index(name="idx_food_category",columnList="category")})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Food {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(unique=true,length=40) private String usdaId;
    @Column(nullable=false,length=500) private String name;
    @Column(length=80) private String category;
    @Enumerated(EnumType.STRING) @Column(name="meal_type",nullable=false,length=20) private MealType mealType;
    @Enumerated(EnumType.STRING) @Column(name="diet_type",nullable=false,length=20) private DietType dietType;
    @Column(nullable=false) private Double calories;
    @Column(nullable=false) private Double protein;
    @Column(nullable=false) private Double carbs;
    @Column(nullable=false) private Double fat;
    @Column(nullable=false) private Double fiber;
    private Double sugar;
    private Double sodium;
    @Column(name="serving_size",nullable=false) private Double servingSize;
    @Column(name="serving_unit",length=20) private String servingUnit;
    private Boolean vegetarian;
    private Boolean vegan;
    private Boolean glutenFree;
    private Boolean dairyFree;
    private Boolean nutFree;
    private Boolean soyFree;
}
