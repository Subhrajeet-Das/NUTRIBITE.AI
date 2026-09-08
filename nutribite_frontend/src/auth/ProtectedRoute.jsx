import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function ProtectedRoute() {
  const { token, checking } = useAuth();
  const location = useLocation();
  if (checking) return <div className="boot-screen"><div className="boot-mark">NB</div><strong>NutriBiteAI</strong><span>Loading your space…</span></div>;
  if (!token) return <Navigate to="/login" replace state={{ from: location }} />;
  return <Outlet />;
}
