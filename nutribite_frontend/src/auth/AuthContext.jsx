import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { authApi } from "../api/authApi";
import { profileApi } from "../api/profileApi";
import { clearSession, getStoredUser, getToken, saveSession } from "./authStorage";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const navigate = useNavigate();
  const [token, setToken] = useState(getToken());
  const [user, setUser] = useState(getStoredUser());
  const [profile, setProfile] = useState(null);
  const [checking, setChecking] = useState(Boolean(getToken()));
  const [needsProfile, setNeedsProfile] = useState(false);

  async function refreshProfile() {
    try {
      const result = await profileApi.getProfile();
      const nextProfile = result?.profile || result;
      setProfile(nextProfile || null);
      setNeedsProfile(!isProfileComplete(nextProfile));
      return nextProfile;
    } catch (error) {
      if (error.status === 404) {
        setProfile(null);
        setNeedsProfile(true);
        return null;
      }
      throw error;
    }
  }

  async function login(credentials) {
    const result = await authApi.login(credentials);
    // Confirmed backend LoginResponse constructor receives token first and UserResponse third.
    const nextToken = result?.token;
    if (!nextToken) throw new Error("Login succeeded, but no session token was returned.");
    const nextUser = result?.user || null;
    saveSession(nextToken, nextUser);
    setToken(nextToken);
    setUser(nextUser);
    let nextProfile = null;
    try { nextProfile = await refreshProfile(); }
    catch (error) { if (error.status !== 404) throw error; }
    return { ...result, profileComplete: isProfileComplete(nextProfile) };
  }

  async function register(payload) { return authApi.register(payload); }

  function logout() {
    clearSession();
    setToken(null); setUser(null); setProfile(null); setNeedsProfile(false);
    navigate("/login", { replace: true });
  }

  useEffect(() => {
    let active = true;
    if (!token) { setChecking(false); return undefined; }
    refreshProfile().catch((error) => {
      if (error.status === 401 || error.status === 404) {
        if (error.status === 401) clearSession();
        if (active && error.status === 401) { setToken(null); setUser(null); setProfile(null); setNeedsProfile(false); }
      }
    }).finally(() => { if (active) setChecking(false); });
    return () => { active = false; };
  }, [token]);

  useEffect(() => {
    const onUnauthorized = () => {
      clearSession(); setToken(null); setUser(null); setProfile(null); setNeedsProfile(false);
    };
    window.addEventListener("nutribite:unauthorized", onUnauthorized);
    return () => window.removeEventListener("nutribite:unauthorized", onUnauthorized);
  }, []);

  const value = useMemo(() => ({ token, user, profile, setProfile, checking, needsProfile, login, register, logout, refreshProfile }), [token, user, profile, checking, needsProfile]);
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function isProfileComplete(profile) {
  if (!profile) return false;
  const fields = ["dateOfBirth", "heightCm", "weightKg", "targetWeightKg", "activityLevel", "goal", "dietType"];
  return fields.every((key) => profile[key] !== null && profile[key] !== undefined && profile[key] !== "");
}

export function useAuth() { return useContext(AuthContext); }
