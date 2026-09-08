import { getToken, clearSession } from "../auth/authStorage";

const BASE_URL = (import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api").replace(/\/$/, "");

function friendlyMessage(status, data) {
  const serverMessage = data && typeof data === "object" ? data.message || data.error : null;
  if (serverMessage && !/Whitelabel|No static resource|AxiosError/i.test(serverMessage)) return serverMessage;
  if (status === 400) return "Please check the information you entered.";
  if (status === 401) return "Your session has expired. Please sign in again.";
  if (status === 403) return "You don't have permission to access this information.";
  if (status === 404) return "This information isn't available yet.";
  if (status >= 500) return "NutriBiteAI couldn't load this right now. Please try again.";
  return "Something went wrong. Please try again.";
}

async function parseBody(response) {
  const type = response.headers.get("content-type") || "";
  if (response.status === 204) return null;
  if (type.includes("application/json")) return response.json();
  const text = await response.text();
  return text ? { message: text } : null;
}

async function request(path, options = {}) {
  const headers = new Headers(options.headers || {});
  if (options.body !== undefined && !(options.body instanceof FormData)) headers.set("Content-Type", "application/json");
  const token = getToken();
  if (token) headers.set("Authorization", `Bearer ${token}`);

  let response;
  try {
    response = await fetch(`${BASE_URL}${path}`, { ...options, headers });
  } catch {
    const error = new Error("Unable to connect to the NutriBiteAI server.");
    error.status = 0;
    throw error;
  }

  const data = await parseBody(response);
  if (response.status === 401) {
    clearSession();
    window.dispatchEvent(new Event("nutribite:unauthorized"));
  }
  if (!response.ok) {
    const error = new Error(friendlyMessage(response.status, data));
    error.status = response.status;
    error.data = data;
    throw error;
  }
  return data;
}

export const api = {
  get: (path) => request(path),
  post: (path, body) => request(path, { method: "POST", body: JSON.stringify(body) }),
  put: (path, body) => request(path, { method: "PUT", body: JSON.stringify(body) }),
};

export { friendlyMessage };
