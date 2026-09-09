package com.nutribite.ai.recipe.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="recipe_ingredients") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecipeIngredient {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="recipe_id",nullable=false) private Recipe recipe;
 @Column(nullable=false,length=180) private String name;
 private Double quantity;
 @Column(length=30) private String unit;
}
