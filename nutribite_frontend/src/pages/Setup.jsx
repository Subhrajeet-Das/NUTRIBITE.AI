import { useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, ArrowRight, CalendarDays, Ruler, Scale, Activity, Target, Utensils, Dumbbell, Check } from 'lucide-react';
import Logo from '../components/Logo';
import { updateProfile } from '../api/profileApi';
import { useAuth } from '../auth/AuthContext';

const steps = [
  { key:'dob', title:'When were you born?', sub:'Your date of birth helps us understand your age accurately.', icon:CalendarDays },
  { key:'height', title:'How tall are you?', sub:'Use your current height. You can change it later.', icon:Ruler },
  { key:'weight', title:'What is your current weight?', sub:'A simple starting point for your nutrition profile.', icon:Scale },
  { key:'activityLevel', title:'How active are you?', sub:'Choose the option that best matches your normal week.', icon:Activity },
  { key:'goal', title:'What are you working toward?', sub:'Pick the result you care about most right now.', icon:Target },
  { key:'dietType', title:'How do you eat?', sub:'We will use this preference for recipe discovery.', icon:Utensils },
  { key:'fitnessLevel', title:'What is your fitness level?', sub:'There is no wrong answer.', icon:Dumbbell },
];

const choices = {
 activityLevel:[['SEDENTARY','Mostly sitting'],['LIGHT','Lightly active'],['MODERATE','Regularly active'],['VERY_ACTIVE','Very active']],
 goal:[['LOSE_WEIGHT','Lose weight'],['GAIN_WEIGHT','Gain weight'],['MAINTAIN_WEIGHT','Maintain weight'],['HEALTHY_EATING','Eat healthier']],
 dietType:[['VEG','Vegetarian'],['NON_VEG','Non-vegetarian'],['EGGETARIAN','Eggetarian'],['VEGAN','Vegan']],
 fitnessLevel:[['BEGINNER','Beginner'],['INTERMEDIATE','Intermediate'],['ADVANCED','Advanced']],
};

export default function Setup(){
 const nav=useNavigate(); const {profile,setProfile}=useAuth();
 const [i,setI]=useState(0); const [busy,setBusy]=useState(false); const [error,setError]=useState('');
 const [form,setForm]=useState({dateOfBirth:profile?.dateOfBirth||'',heightCm:profile?.heightCm||'',weightKg:profile?.weightKg||'',targetWeightKg:profile?.targetWeightKg||'',activityLevel:profile?.activityLevel||'',goal:profile?.goal||'',dietType:profile?.dietType||'',fitnessLevel:profile?.fitnessLevel||'',dailyWaterGoal:profile?.dailyWaterGoal||3,sleepGoal:profile?.sleepGoal||8});
 const step=steps[i]; const progress=Math.round(((i+1)/steps.length)*100);
 const valid=useMemo(()=>{ if(step.key==='dob') return !!form.dateOfBirth; if(step.key==='height') return Number(form.heightCm)>50; if(step.key==='weight') return Number(form.weightKg)>20; return !!form[step.key]; },[form,step]);
 const set=(k,v)=>setForm(x=>({...x,[k]:v}));
 async function next(){setError(''); if(!valid)return; if(i<steps.length-1){setI(x=>x+1);return;} setBusy(true); try{const saved=await updateProfile({...form,heightCm:Number(form.heightCm),weightKg:Number(form.weightKg),targetWeightKg:form.targetWeightKg?Number(form.targetWeightKg):null,dailyWaterGoal:Number(form.dailyWaterGoal),sleepGoal:Number(form.sleepGoal)});setProfile(saved);nav('/home',{replace:true});}catch(e){setError(e?.response?.data?.message||e?.response?.data?.error||'Could not save your profile. Check that the backend is running.')}finally{setBusy(false)}}
 return <div className="setupPage"><header className="setupTop"><Logo/><span>{progress}% complete</span></header><main className="setupShell"><div className="setupProgress"><div style={{width:`${progress}%`}}/></div><div className="setupIcon"><step.icon size={25}/></div><span className="eyebrow">PROFILE SETUP · {i+1} / {steps.length}</span><h1>{step.title}</h1><p className="setupSub">{step.sub}</p>{error&&<div className="formError setupError">{error}</div>}
 {step.key==='dob'&&<input className="bigInput" type="date" value={form.dateOfBirth} onChange={e=>set('dateOfBirth',e.target.value)} max={new Date().toISOString().slice(0,10)}/>} 
 {step.key==='height'&&<div className="measureInput"><input className="bigInput" type="number" min="80" max="250" placeholder="170" value={form.heightCm} onChange={e=>set('heightCm',e.target.value)}/><span>cm</span></div>}
 {step.key==='weight'&&<div className="weightFields"><label>Current weight<input className="bigInput" type="number" min="20" max="400" step="0.1" placeholder="65" value={form.weightKg} onChange={e=>set('weightKg',e.target.value)}/><small>kg</small></label><label>Target weight <span>(optional)</span><input className="bigInput" type="number" min="20" max="400" step="0.1" placeholder="60" value={form.targetWeightKg} onChange={e=>set('targetWeightKg',e.target.value)}/><small>kg</small></label></div>}
 {choices[step.key]&&<div className="choiceGrid">{choices[step.key].map(([v,label])=><button key={v} className={`choice ${form[step.key]===v?'selected':''}`} onClick={()=>set(step.key,v)}>{label}{form[step.key]===v&&<Check size={18}/>}</button>)}</div>}
 <div className="setupActions"><button className="backBtn" onClick={()=>i?setI(x=>x-1):nav('/home')}><ArrowLeft/> Back</button><button className="nextBtn" disabled={!valid||busy} onClick={next}>{busy?'Saving…':i===steps.length-1?'Finish profile':'Continue'}<ArrowRight/></button></div></main></div>
}
