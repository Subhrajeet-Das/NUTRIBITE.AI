import { RefreshCw } from "lucide-react";
export default function ErrorState({ title = "Something went wrong", message = "Please try again.", onRetry }) { return <div className="error-state"><div><b>{title}</b><span>{message}</span></div>{onRetry && <button className="secondary-action" onClick={onRetry}><RefreshCw size={16}/> Retry</button>}</div>; }
