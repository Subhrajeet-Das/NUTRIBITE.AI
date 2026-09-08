import { api } from "./apiClient";

export const mealApi = {
  getRecommendation: () => api.get("/meals/recommendation"),
};
