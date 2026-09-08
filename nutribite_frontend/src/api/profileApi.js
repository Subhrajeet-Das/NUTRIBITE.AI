import { api } from "./apiClient";

export const profileApi = {
  getProfile: () => api.get("/profile"),
  updateProfile: (payload) => api.put("/profile", payload),
  getHealth: () => api.get("/profile/health"),
};
