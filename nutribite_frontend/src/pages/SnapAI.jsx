import { useRef, useState } from "react";
import { Camera, ImagePlus, Sparkles, X } from "lucide-react";

export default function SnapAI(){
 const input=useRef(); const [file,setFile]=useState(null); const [preview,setPreview]=useState(""); const [busy,setBusy]=useState(false);
 function choose(e){const f=e.target.files?.[0];if(!f)return;setFile(f);setPreview(URL.createObjectURL(f));}
 function clear(){setFile(null);setPreview("");}
 async function analyze(){if(!file)return;setBusy(true);await new Promise(r=>setTimeout(r,900));setBusy(false);}
 return <div className="page snap-page"><div className="snap-head"><div><div className="eyebrow">NUTRIBITE / VISION</div><h1>What's on your <em>plate?</em></h1><p>Snap a photo. NutriBite will be ready to interpret it when your vision endpoint is connected.</p></div><div className="ai-badge"><Sparkles size={17}/> AI SNAP</div></div>
 <div className={`snap-stage ${preview?"has-image":""}`}>
  {preview?<><img src={preview} alt="Selected food"/><button className="remove-image" onClick={clear}><X size={18}/></button></>:<div className="snap-empty"><div className="camera-ring"><Camera size={30}/></div><h2>Make the plate the prompt.</h2><p>Upload a food photo to begin.</p><div className="snap-actions"><button className="primary-btn" onClick={()=>input.current?.click()}><ImagePlus size={18}/> Choose photo</button><input ref={input} hidden type="file" accept="image/*" onChange={choose}/></div></div>}
 </div>
 {preview&&<button className="analyze-btn" disabled={busy} onClick={analyze}>{busy?"Reading your plate…":"Analyze with Snap AI"}<Sparkles size={18}/></button>}
 <div className="snap-features"><div><span>01</span><b>Recognize</b><p>Identify likely Indian dishes and ingredients.</p></div><div><span>02</span><b>Understand</b><p>Turn the image into useful food information.</p></div><div><span>03</span><b>Decide</b><p>Use the result as a starting point, not a diagnosis.</p></div></div>
 </div>
}
