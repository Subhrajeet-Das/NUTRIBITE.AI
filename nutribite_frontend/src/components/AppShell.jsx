import { useEffect, useState } from "react";
import { Camera, ChevronDown, Home, LogOut, Menu, UserRound, Utensils, X } from "lucide-react";
import { NavLink, useLocation } from "react-router-dom";
import Logo from "./Logo";
import { useAuth } from "../auth/AuthContext";

export default function AppShell({ children }) {
  const { user, logout } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);
  const [navOpen, setNavOpen] = useState(false);
  const location = useLocation();
  const initials = (user?.firstName?.[0] || user?.email?.[0] || "N").toUpperCase();

  useEffect(() => { setMenuOpen(false); setNavOpen(false); }, [location.pathname]);
  useEffect(() => { const close = () => setMenuOpen(false); window.addEventListener("click", close); return () => window.removeEventListener("click", close); }, []);

  const links = [["/home", "Home", Home], ["/recipes", "Recipes", Utensils], ["/snap", "Snap AI", Camera], ["/profile", "Profile", UserRound]];

  return <div className="app-shell">
    <header className="topbar">
      <button className="mobile-menu" onClick={(e) => { e.stopPropagation(); setNavOpen(v => !v); }} aria-label="Toggle navigation">{navOpen ? <X size={20}/> : <Menu size={20}/>}</button>
      <NavLink to="/home" className="brand-link"><Logo compact /></NavLink>
      <div className="top-actions">
        <div className="account-wrap" onClick={e => e.stopPropagation()}>
          <button className="account-button" onClick={() => setMenuOpen(v => !v)} aria-label="Open account menu"><span className="avatar">{initials}</span><ChevronDown size={15}/></button>
          {menuOpen && <div className="account-menu">
            <div className="account-user"><span className="avatar avatar-lg">{initials}</span><div><strong>{user?.firstName || "Account"}</strong><small>{user?.email || "Signed in"}</small></div></div>
            <NavLink to="/profile"><UserRound size={16}/> Profile</NavLink>
            <NavLink to="/onboarding"><span className="menu-dot"/> Complete profile</NavLink>
            <button className="danger-link" onClick={logout}><LogOut size={16}/> Sign out</button>
          </div>}
        </div>
      </div>
    </header>
    <div className="shell-body">
      <aside className={`sidebar ${navOpen ? "open" : ""}`}>
        <div className="sidebar-inner">
          <span className="sidebar-label">YOUR SPACE</span>
          <nav className="side-nav">{links.map(([to,label,Icon]) => <NavLink key={to} to={to} className={({isActive}) => `side-link ${isActive ? "active" : ""}`}><Icon size={19}/><span>{label}</span></NavLink>)}</nav>
          <div className="sidebar-footer"><p>Better food.<br/><em>brighter you.</em></p><Logo compact/><button onClick={logout} className="signout"><LogOut size={15}/> Sign out</button></div>
        </div>
      </aside>
      {navOpen && <button className="mobile-scrim" onClick={() => setNavOpen(false)} aria-label="Close navigation"/>}
      <main className="main-content">{children}</main>
    </div>
    <nav className="mobile-nav">{links.map(([to,label,Icon]) => <NavLink key={to} to={to} className={({isActive}) => isActive ? "active" : ""}><Icon size={18}/><span>{label}</span></NavLink>)}</nav>
  </div>;
}
