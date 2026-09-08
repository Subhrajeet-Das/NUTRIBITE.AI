import { api } from "./apiClient";

function normalizeNutrition(data = {}) {
  return {
    ...data,
    dailyCalories: data.dailyCalories ?? data.calories ?? data.calorieTarget ?? data.targetCalories,
    protein: data.protein ?? data.proteinTarget ?? data.targetProtein,
    carbs: data.carbs ?? data.carbsTarget ?? data.targetCarbs,
    fat: data.fat ?? data.fatTarget ?? data.targetFat,
    fiber: data.fiber ?? data.fibre ?? data.fiberTarget ?? data.targetFiber ?? data.plannedFiber,
    waterGoal: data.waterGoal ?? data.dailyWaterGoal,
  };
}

export const nutritionApi = {
  // Current backend/docs expose GET /api/nutrition. Older builds used /api/nutrition/recommendation.
  getNutrition: async () => {
    try {
      return normalizeNutrition(await api.get("/nutrition"));
    } catch (error) {
      if (error.status !== 404) throw error;
      return normalizeNutrition(await api.get("/nutrition/recommendation"));
    }
  },
};
