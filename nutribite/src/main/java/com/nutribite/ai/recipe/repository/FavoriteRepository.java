package com.nutribite.ai.recipe.repository;
import com.nutribite.ai.model.User; import com.nutribite.ai.recipe.entity.Recipe; import com.nutribite.ai.recipe.entity.RecipeFavorite; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface FavoriteRepository extends JpaRepository<RecipeFavorite,Long>{ Optional<RecipeFavorite> findByUserAndRecipe(User user,Recipe recipe); List<RecipeFavorite> findByUserOrderByCreatedAtDesc(User user); boolean existsByUserAndRecipe(User user,Recipe recipe);}
