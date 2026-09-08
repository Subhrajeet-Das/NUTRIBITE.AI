import { ArrowRight, BarChart3, HeartPulse, LockKeyhole, Sparkles, Target, X } from "lucide-react";
import { useNavigate } from "react-router-dom";
import Logo from "./Logo";

export default function ProfilePrompt({ onClose }) {
  const navigate = useNavigate();
  return <div className="modal-backdrop" role="dialog" aria-modal="true" aria-labelledby="profile-prompt-title"><div className="profile-modal">
    <button className="modal-close" onClick={onClose} aria-label="Close"><X size={18}/></button>
    <Logo compact />
    <div className="modal-visual"><div className="modal-orb"><Sparkles size={28}/></div><span/><span/><span/></div>
    <span className="eyebrow">A BETTER START</span>
    <h2 id="profile-prompt-title">Complete your profile</h2>
    <p className="modal-copy">Help us personalize your NutriBiteAI experience with accurate nutrition insights, recipes and future AI recommendations.</p>
    <div className="benefits">
      <Benefit Icon={Target} title="Personalized recommendations" text="Built around your goals"/>
      <Benefit Icon={BarChart3} title="Accurate nutrition" text="Based on your profile"/>
      <Benefit Icon={HeartPulse} title="Better health insights" text="Useful body metrics"/>
      <Benefit Icon={Sparkles} title="A tailored experience" text="Less noise, more relevance"/>
    </div>
    <div className="modal-actions"><button className="secondary-action" onClick={onClose}>Maybe later</button><button className="primary-action" onClick={() => navigate("/onboarding")} >Complete profile <ArrowRight size={17}/></button></div>
    <div className="privacy-line"><LockKeyhole size={14}/> Your data is safe and private.</div>
  </div></div>;
}
function Benefit({ Icon, title, text }) { return <div><span className="benefit-icon"><Icon size={17}/></span><span><b>{title}</b><small>{text}</small></span></div>; }
