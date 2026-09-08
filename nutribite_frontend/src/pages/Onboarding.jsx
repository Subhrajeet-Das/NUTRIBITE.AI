import { useMemo, useState } from "react";
import { Activity, ArrowLeft, ArrowRight, Bike, CalendarDays, Check, HeartPulse, Leaf, Scale, Target, Utensils, Weight, Zap } from "lucide-react";
import { useNavigate } from "react-router-dom";
import Logo from "../components/Logo";
import { profileApi } from "../api/profileApi";
import { useAuth, isProfileComplete } from "../auth/AuthContext";

const steps = [
  { key: "dateOfBirth", title: "When were you born?", text: "Your date of birth helps the backend calculate your age accurately." },
  { key: "heightCm", title: "How tall are you?", text: "Height is sent to Spring Boot for its nutrition calculations." },
  { key: "weightKg", title: "What's your current weight?", text: "Your current weight gives the nutrition engine a useful starting point." },
  { key: "targetWeightKg", title: "What's your target weight?", text: "Choose the target you want NutriBiteAI to keep in context." },
  { key: "activityLevel", title: "How active are you?", text: "Choose the activity level that matches your routine." },
  { key: "goal", title: "What's your goal?", text: "This directly shapes the goal used by the backend." },
  { key: "dietType", title: "What's your diet preference?", text: "Use the diet preference you actually follow." },
];
const activities = [["SEDENTARY","Sedentary","Mostly sitting",Activity],["LIGHTLY_ACTIVE","Lightly active","Regular walking and light exercise",Bike],["MODERATELY_ACTIVE","Moderately active","Exercise or active movement most days",Zap],["VERY_ACTIVE","Very active","Hard training or active work",Activity],["EXTRA_ACTIVE","Extra active","Very high daily activity",Zap]];
const goals = [["LOSE_WEIGHT","Lose weight","Work toward a lower body weight",Scale],["MAINTAIN_WEIGHT","Maintain weight","Keep your current weight steady",Target],["GAIN_WEIGHT","Gain weight","Support healthy weight gain",Weight]];
const diets = [["VEG","Vegetarian","Vegetarian foods",Leaf],["NON_VEG","Non-vegetarian","Includes meat and fish",Utensils],["VEGAN","Vegan","Plant-based foods",Leaf],["EGGETARIAN","Eggetarian","Vegetarian foods plus eggs",HeartPulse],["PESCATARIAN","Pescatarian","Fish plus plant foods",Utensils]];

export default function Onboarding() {
  const { profile, refreshProfile } = useAuth(); const navigate = useNavigate();
  const [step,setStep] = useState(0); const [busy,setBusy] = useState(false); const [error,setError] = useState(""); const [heightUnit,setHeightUnit] = useState("cm");
  const [data,setData] = useState({ dateOfBirth: profile?.dateOfBirth || "", heightCm: profile?.heightCm ?? 170, weightKg: profile?.weightKg ?? 65, targetWeightKg: profile?.targetWeightKg ?? 60, activityLevel: profile?.activityLevel || "MODERATELY_ACTIVE", goal: profile?.goal || "MAINTAIN_WEIGHT", dietType: profile?.dietType || "VEG", fitnessLevel: profile?.fitnessLevel || "BEGINNER", dailyWaterGoal: profile?.dailyWaterGoal ?? 3, sleepGoal: profile?.sleepGoal ?? 8 });
  const current = steps[step]; const progress = Math.round(((step+1)/steps.length)*100);
  const age = useMemo(() => { if (!data.dateOfBirth) return null; const d = new Date(`${data.dateOfBirth}T00:00:00`), n = new Date(); let a=n.getFullYear()-d.getFullYear(); if(n.getMonth()<d.getMonth() || n.getMonth()===d.getMonth()&&n.getDate()<d.getDate()) a--; return a; }, [data.dateOfBirth]);
  const set = patch => setData(v => ({...v,...patch}));
  const valid = current.key === "dateOfBirth" ? Boolean(data.dateOfBirth) && age >= 13 && age <= 100 : current.key === "heightCm" ? Number(data.heightCm)>=80 && Number(data.heightCm)<=250 : current.key === "weightKg" ? Number(data.weightKg)>=20 && Number(data.weightKg)<=300 : current.key === "targetWeightKg" ? Number(data.targetWeightKg)>=20 && Number(data.targetWeightKg)<=300 : Boolean(data[current.key]);
  async function next() { setError(""); if(!valid){setError(current.key === "dateOfBirth" ? "Please enter a valid date of birth." : "Please choose a valid value."); return;} if(step < steps.length-1){setStep(v=>v+1); return;} setBusy(true); try { await profileApi.updateProfile({ dateOfBirth:data.dateOfBirth, heightCm:Number(data.heightCm), weightKg:Number(data.weightKg), targetWeightKg:Number(data.targetWeightKg), activityLevel:data.activityLevel, goal:data.goal, dietType:data.dietType, fitnessLevel:data.fitnessLevel, dailyWaterGoal:Number(data.dailyWaterGoal), sleepGoal:Number(data.sleepGoal) }); const fresh = await refreshProfile(); if(!isProfileComplete(fresh)) throw new Error("Your profile was saved, but a required field is still missing."); sessionStorage.setItem("nutribite_profile_prompt_seen","1"); navigate("/home",{replace:true}); } catch(err){setError(err.message||"Could not save your profile.");} finally{setBusy(false);} }
  function renderStep(){
    if(current.key === "dateOfBirth") return <><div className="setup-icon"><CalendarDays/></div><span className="eyebrow">01 / BIRTH DATE</span><h2>{current.title}</h2><p>{current.text}</p><label className="big-input"><CalendarDays size={19}/><input type="date" max={new Date().toISOString().slice(0,10)} value={data.dateOfBirth} onChange={e=>set({dateOfBirth:e.target.value})}/></label>{age && <div className="age-chip"><b>{age}</b> years old</div>}</>;

    if(["heightCm","weightKg","targetWeightKg"].includes(current.key)){
      const meta={heightCm:[Activity,"02 / HEIGHT",data.heightCm,"cm",80,250],weightKg:[Weight,"03 / CURRENT WEIGHT",data.weightKg,"kg",20,300],targetWeightKg:[Target,"04 / TARGET WEIGHT",data.targetWeightKg,"kg",20,300]}[current.key];
      const [Icon,eyebrow,storedValue,defaultUnit,min,max]=meta;
      const isHeight=current.key==="heightCm";
      const totalInches=isHeight ? Number(storedValue)/2.54 : 0;
      const roundedInches=Math.max(36,Math.min(96,Math.round(totalInches)));
      const feet=Math.floor(roundedInches/12);
      const inches=roundedInches%12;
      const shownValue=isHeight&&heightUnit==="ft" ? `${feet}′ ${inches}″` : String(Number(storedValue));
      const adjust=(dir)=>{
        if(isHeight && heightUnit==="ft"){
          const minInches=Math.ceil(min/2.54);
          const maxInches=Math.floor(max/2.54);
          const nextTotal=Math.max(minInches,Math.min(maxInches,roundedInches+dir));
          set({heightCm:Number((nextTotal*2.54).toFixed(1))});
          return;
        }
        const nextValue=Number(storedValue)+(dir*(isHeight?1:1));
        const clamped=Math.min(max,Math.max(min,nextValue));
        set({[current.key]:Number(clamped.toFixed(1))});
      };
      return <>
        <div className="setup-icon"><Icon/></div>
        <span className="eyebrow">{eyebrow}</span>
        <h2>{current.title}</h2>
        <p>{current.text}</p>
        {isHeight&&<div className="unit-switch" role="group" aria-label="Height unit">
          <button type="button" className={heightUnit==="cm"?"active":""} onClick={()=>setHeightUnit("cm")}>CM</button>
          <button type="button" className={heightUnit==="ft"?"active":""} onClick={()=>setHeightUnit("ft")}>FEET + INCHES</button>
        </div>}
        <div className="metric-display"><strong>{shownValue}</strong><span>{isHeight?(heightUnit==="ft"?"feet + inches":"cm"):defaultUnit}</span></div>
        <div className="stepper"><button type="button" aria-label="Decrease" onClick={()=>adjust(-1)}>−</button><div><b>{shownValue}</b><span>{isHeight?(heightUnit==="ft"?"feet + inches":"cm"):defaultUnit}</span></div><button type="button" aria-label="Increase" onClick={()=>adjust(1)}>+</button></div>
        {isHeight&&<small className="unit-note">Height is always saved as centimeters for accurate backend calculations.</small>}
      </>;
    }
    const options = current.key === "activityLevel" ? activities : current.key === "goal" ? goals : diets;
    return <><span className="eyebrow">0{step+1} / {current.key === "activityLevel" ? "ACTIVITY" : current.key === "goal" ? "GOAL" : "FOOD STYLE"}</span><h2>{current.title}</h2><p>{current.text}</p><div className="choice-grid">{options.map(([key,title,text,Icon])=><button type="button" className={`choice ${data[current.key]===key?"selected":""}`} key={key} onClick={()=>set({[current.key]:key})}><span className="choice-icon"><Icon size={18}/></span><span><b>{title}</b><small>{text}</small></span>{data[current.key]===key&&<Check className="choice-check" size={18}/>}</button>)}</div></>;
  }
  return <div className="setup-page"><header className="setup-header"><Logo compact/><div className="setup-status"><span>PROFILE SETUP</span><b>{step+1} / {steps.length}</b></div></header><div className="setup-progress"><i style={{width:`${progress}%`}}/></div><main className="setup-main"><div className="setup-heading"><span className="eyebrow">YOUR NUTRITION SPACE</span><h1>Let's make NutriBite <em>yours.</em></h1><p>Seven essentials. No unnecessary questionnaires.</p></div><section className="setup-card"><div className="step-dots">{steps.map((_,i)=><span key={i} className={i<=step?"done":""}/>)}</div>{renderStep()}{error&&<div className="form-error">{error}</div>}</section></main><footer className="setup-footer"><button className="secondary-action" disabled={step===0} onClick={()=>setStep(v=>Math.max(0,v-1))}><ArrowLeft size={17}/> Back</button><button className="primary-action" disabled={busy} onClick={next}>{busy?"Saving…":step===steps.length-1?"Finish setup":"Continue"}<ArrowRight size={17}/></button></footer></div>;
}
