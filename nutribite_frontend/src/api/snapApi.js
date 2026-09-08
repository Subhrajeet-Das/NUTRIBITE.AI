/**
 * Snap AI service boundary.
 * The current Spring Boot source does not expose a food-image analysis endpoint,
 * so the UI deliberately does not fabricate a response. Connect the real
 * multipart endpoint here when the backend is available.
 */
export async function analyzeFoodImage() {
  const error = new Error("Snap AI image analysis is not connected to the backend yet.");
  error.code = "SNAP_AI_NOT_CONNECTED";
  throw error;
}
