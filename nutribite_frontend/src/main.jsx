import React from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import { AuthProvider } from "./auth/AuthContext";
import GlobalErrorBoundary from "./components/GlobalErrorBoundary";
import "./styles.css";
createRoot(document.getElementById("root")).render(<React.StrictMode><BrowserRouter><AuthProvider><GlobalErrorBoundary><App/></GlobalErrorBoundary></AuthProvider></BrowserRouter></React.StrictMode>);
