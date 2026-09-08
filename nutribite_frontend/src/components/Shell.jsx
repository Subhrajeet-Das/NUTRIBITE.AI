import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { Camera, ChefHat, Home, LogOut, UserRound } from "lucide-react";
import Logo from "./Logo";
import { useAuth } from "../auth/AuthContext";

const links = [
  { to: "/home", label: "Home", icon: Home },
  { to: "/recipes", label: "Recipes", icon: ChefHat },
  { to: "/snap", label: "Snap AI", icon: Camera },
  { to: "/profile", label: "Profile", icon: UserRound }
];

export default function Shell() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const name = user?.firstName || user?.name || "You";
  return (
    <div className="app-shell">
      <header className="topbar">
        <Logo />
        <div className="top-actions">
          <button className="avatar" onClick={() => navigate("/profile")}>
            {(name[0] || "N").toUpperCase()}
          </button>
          <button className="profile-pill" onClick={() => navigate("/profile")}>
            <span>{name}</span><span className="chevron">⌄</span>
          </button>
          <button className="icon-btn logout-btn" title="Sign out" onClick={logout}><LogOut size={17}/></button>
        </div>
      </header>

      <aside className="sidebar">
        <div className="sidebar-label">YOUR SPACE</div>
        <nav>
          {links.map(({to,label,icon:Icon}) => (
            <NavLink key={to} to={to} end className={({isActive}) => `nav-link ${isActive ? "active" : ""}`}>
              <Icon size={20}/><span>{label}</span>
            </NavLink>
          ))}
        </nav>
        <div className="sidebar-bottom">
          <div className="quote">“Better food.<br/>brighter you.”</div>
          <Logo compact />
        </div>
      </aside>

      <main className="main-content"><Outlet /></main>

      <nav className="mobile-nav">
        {links.map(({to,label,icon:Icon}) => (
          <NavLink key={to} to={to} end className={({isActive}) => isActive ? "mobile-active" : ""}>
            <Icon size={19}/><span>{label}</span>
          </NavLink>
        ))}
      </nav>
    </div>
  );
}
