import React from "react";
import { RefreshCw } from "lucide-react";
import Logo from "./Logo";

export default class GlobalErrorBoundary extends React.Component {
  state = { crashed: false };
  static getDerivedStateFromError() { return { crashed: true }; }
  render() {
    if (this.state.crashed) return <div className="fatal-screen"><Logo/><span className="eyebrow">TEMPORARY ISSUE</span><h1>NutriBiteAI needs a refresh.</h1><p>Something unexpected happened in the interface. Your session is still safe.</p><button className="primary-action" onClick={() => window.location.reload()}><RefreshCw size={17}/> Refresh</button></div>;
    return this.props.children;
  }
}
