import { api } from "./apiClient";

export const foodApi = {
  search: (query) => api.get(`/foods/search?query=${encodeURIComponent(query)}`),
  byMeal: (mealType) => api.get(`/foods/meal/${mealType}`),
  byDiet: (dietType) => api.get(`/foods/diet/${dietType}`),
  highProtein: () => api.get("/foods/high-protein"),
  lowCalorie: () => api.get("/foods/low-calorie"),
};
