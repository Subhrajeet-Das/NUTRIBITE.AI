package com.nutribite.ai.recipe.entity;

import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.model.MealType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="recipes", indexes={@Index(name="idx_recipe_title",columnList="title"),@Index(name="idx_recipe_meal",columnList="meal_type"),@Index(name="idx_recipe_diet",columnList="diet_type")})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Recipe {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=180) private String title;
    @Column(length=2000) private String description;
    @Column(length=4000) private String instructions;
    @Column(length=500) private String imageUrl;
    private Integer prepMinutes;
    private Integer cookMinutes;
    private Integer servings;
    @Enumerated(EnumType.STRING) @Column(name="meal_type",length=20) private MealType mealType;
    @Enumerated(EnumType.STRING) @Column(name="diet_type",length=20) private DietType dietType;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
    private Double fiber;
    @OneToMany(mappedBy="recipe",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
    @Builder.Default private List<RecipeIngredient> ingredients=new ArrayList<>();
    @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @Column(nullable=false) private LocalDateTime updatedAt;
    @PrePersist void create(){createdAt=LocalDateTime.now();updatedAt=createdAt;}
    @PreUpdate void update(){updatedAt=LocalDateTime.now();}
    public void replaceIngredients(List<RecipeIngredient> items){ingredients.clear(); if(items!=null) items.forEach(this::addIngredient);}
    public void addIngredient(RecipeIngredient item){item.setRecipe(this);ingredients.add(item);}
}
