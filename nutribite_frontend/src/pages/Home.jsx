import { useCallback, useEffect, useState } from "react";
import { ArrowRight, Droplets, HeartPulse, Moon, RefreshCw, Scale, Sparkles } from "lucide-react";
import { Link } from "react-router-dom";
import AppShell from "../components/AppShell";
import ProfilePrompt from "../components/ProfilePrompt";
import NutritionCard from "../components/NutritionCard";
import StatCard from "../components/StatCard";
import LoadingState from "../components/LoadingState";
import { useAuth, isProfileComplete } from "../auth/AuthContext";
import { profileApi } from "../api/profileApi";
import { nutritionApi } from "../api/nutritionApi";
import { mealApi } from "../api/mealApi";

const fmt = (v,d=0)=>v==null||Number.isNaN(Number(v))?"—":Number(v).toLocaleString("en-IN",{maximumFractionDigits:d,minimumFractionDigits:d});
const label=v=>String(v||"—").replaceAll("_"," ");
export default function Home(){
  const {user,profile,setProfile,needsProfile}=useAuth(); const [nutrition,setNutrition]=useState(null); const [health,setHealth]=useState(null); const [loading,setLoading]=useState(true); const [nutritionError,setNutritionError]=useState(""); const [showPrompt,setShowPrompt]=useState(false);
  const load=useCallback(async()=>{setLoading(true);setNutritionError("");try{const p=await profileApi.getProfile().catch(e=>e.status===404?null:Promise.reject(e));const next=p?.profile||p;if(next)setProfile(next);if(!isProfileComplete(next))setShowPrompt(true);const results=await Promise.allSettled([nutritionApi.getNutrition(),profileApi.getHealth(),mealApi.getRecommendation()]);
      const healthData = results[1].status==="fulfilled" ? (results[1].value?.health||results[1].value) : null;
      if(healthData) setHealth(healthData);
      const mealResult = results[2].status==="fulfilled" ? (results[2].value?.mealPlan||results[2].value) : null;
      const summary = mealResult?.summary;
      if(results[0].status==="fulfilled") {
        const base = results[0].value?.nutrition||results[0].value||{};
        setNutrition({ ...base, fiber: base.fiber ?? base.fibre ?? summary?.targetFiber ?? summary?.plannedFiber, source: base.fiber == null && (summary?.targetFiber != null || summary?.plannedFiber != null) ? "NUTRITION + MEAL PLAN" : base.source });
      } else {
        const summary = mealResult?.summary;
        if(summary) {
          setNutrition({
            dailyCalories: summary.targetCalories,
            protein: summary.targetProtein,
            carbs: summary.targetCarbs,
            fat: summary.targetFat,
            fiber: summary.targetFiber,
            waterGoal: p?.dailyWaterGoal,
            source: "MEAL PLAN"
          });
          setNutritionError("");
        } else if(healthData?.tdee != null && p?.goal) {
          // Resilience fallback: mirrors the verified Spring Boot calorie adjustment.
          const goal = String(p.goal);
          const adjustment = goal === "LOSE_WEIGHT" ? -400 : goal === "GAIN_WEIGHT" ? 300 : 0;
          const dailyCalories = Math.max(1200, Number(healthData.tdee) + adjustment);
          const protein = p.weightKg != null ? Number(p.weightKg) * 1.6 : null;
          const fat = protein == null ? null : (dailyCalories * 0.25) / 9;
          const carbs = protein == null || fat == null ? null : Math.max(0, (dailyCalories - protein * 4 - fat * 9) / 4);
          setNutrition({ dailyCalories, protein, fat, carbs, waterGoal: p?.dailyWaterGoal, source: "PROFILE FALLBACK" });
          setNutritionError("");
        } else {
          setNutrition(null);
          setNutritionError("Nutrition service is unavailable.");
        }
      } }catch(e){setNutritionError(e.message||"Nutrition data unavailable");}finally{setLoading(false);}},[setProfile]);
  useEffect(()=>{if(needsProfile&&!sessionStorage.getItem("nutribite_profile_prompt_seen"))setShowPrompt(true);},[needsProfile]); useEffect(()=>{load();},[load]);
  const p=profile||{}; const h=health||{}; const first=p.firstName||user?.firstName||"there"; const hour=new Date().getHours(); const greeting=hour<12?"Good morning":hour<17?"Good afternoon":"Good evening";
  return <AppShell><div className="page home-page"><header className="hero-head"><div><span className="eyebrow">NUTRIBITE / TODAY</span><h1>{greeting}, {first} <i>✦</i></h1><p>Your nutrition, without the noise.</p></div><div className="date-card"><span>Today</span><b>{new Date().toLocaleDateString("en-IN",{day:"2-digit",month:"short",year:"numeric"})}</b></div></header>{loading?<LoadingState label="Preparing your nutrition space"/>:<><div className="dashboard-grid"><NutritionCard nutrition={nutrition}/><StatCard icon={Scale} tone="mint" label="Body weight" value={p.weightKg!=null?`${fmt(p.weightKg,1)} kg`:"—"} note={p.targetWeightKg!=null?`Target ${fmt(p.targetWeightKg,1)} kg`:"Complete your profile"}/><StatCard icon={Droplets} tone="blue" label="Water goal" value={(nutrition?.waterGoal??p.dailyWaterGoal)!=null?`${fmt(nutrition?.waterGoal??p.dailyWaterGoal,1)} L`:"—"} note="Daily hydration target"/><StatCard icon={Moon} tone="violet" label="Sleep goal" value={p.sleepGoal!=null?`${fmt(p.sleepGoal)} hrs`:"—"} note="Daily recovery target"/><StatCard icon={HeartPulse} tone="lime" label="BMI" value={fmt(h.bmi??h.bodyMassIndex,2)} note="From profile health metrics"/></div>{nutritionError&&<div className="error-banner"><div><b>Nutrition data unavailable</b><span>Please complete your profile or try again.</span></div><button onClick={load}><RefreshCw size={16}/> Retry</button></div>}<section className="insight-strip"><div><span>BMR</span><b>{h.bmr==null?"—":`${fmt(h.bmr,1)} kcal`}</b></div><div><span>TDEE</span><b>{h.tdee==null?"—":`${fmt(h.tdee,1)} kcal`}</b></div><div><span>FOCUS</span><b>{label(p.goal)}</b></div></section><section className="discover-card"><div><span className="eyebrow">DISCOVER / INDIA</span><h2>Food that feels familiar.<br/><em>Nutrition that feels clear.</em></h2><p>Explore Indian food from your NutriBiteAI database, or use Snap AI when the image-analysis backend is connected.</p><div className="discover-actions"><Link to="/recipes">Explore recipes <ArrowRight size={16}/></Link><Link to="/snap" className="outline-link"><Sparkles size={16}/> Try Snap AI</Link></div></div><div className="food-cluster"><span>🍛</span><span>🫓</span><span>🥣</span><span>🍚</span></div></section></>}</div>{showPrompt&&<ProfilePrompt onClose={()=>{sessionStorage.setItem("nutribite_profile_prompt_seen","1");setShowPrompt(false);}}/>}</AppShell>;
}
