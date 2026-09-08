import { api } from "./apiClient";

export const recipesApi = {
  list: (params = {}) => api.get("/foods", { params }).then(r => r.data),
  search: (name, params = {}) => api.get("/foods/search", { params: { name, ...params } }).then(r => r.data)
};
