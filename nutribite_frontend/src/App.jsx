import { Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./auth/ProtectedRoute";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Onboarding from "./pages/Onboarding";
import Home from "./pages/Home";
import Recipes from "./pages/Recipes";
import RecipeDetails from "./pages/RecipeDetails";
import Snap from "./pages/Snap";
import Profile from "./pages/Profile";
export default function App(){return <Routes><Route path="/" element={<Navigate to="/home" replace/>}/><Route path="/login" element={<Login/>}/><Route path="/register" element={<Register/>}/><Route element={<ProtectedRoute/>}><Route path="/home" element={<Home/>}/><Route path="/recipes" element={<Recipes/>}/><Route path="/recipes/:id" element={<RecipeDetails/>}/><Route path="/snap" element={<Snap/>}/><Route path="/profile" element={<Profile/>}/><Route path="/onboarding" element={<Onboarding/>}/></Route><Route path="*" element={<Navigate to="/home" replace/>}/></Routes>}
