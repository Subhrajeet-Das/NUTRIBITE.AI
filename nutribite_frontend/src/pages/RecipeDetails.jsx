import { ArrowLeft } from "lucide-react";
import { Link, useParams } from "react-router-dom";
import AppShell from "../components/AppShell";
export default function RecipeDetails(){const {id}=useParams();return <AppShell><div className="page empty-route"><span className="eyebrow">FOOD RECORD</span><h1>Food details</h1><p>The current backend exposes food records rather than a recipe-detail endpoint. Use the Recipes library to open a live nutrition record.</p><Link className="primary-action" to="/recipes"><ArrowLeft size={17}/> Back to recipes</Link><small>Requested record: {id}</small></div></AppShell>}
