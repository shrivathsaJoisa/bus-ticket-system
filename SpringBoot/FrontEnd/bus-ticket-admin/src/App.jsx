// src/App.jsx
import { useState } from "react";
import LoginPage from "./LoginPage.jsx";
import AdminDashboard from "./AdminDashboard.jsx";

export default function App() {
  const [token, setToken] = useState(() => localStorage.getItem("authToken"));

  const handleLogin = (newToken) => {
    setToken(newToken);
  };

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    setToken(null);
  };

  // Not logged in → show login form
  if (!token) {
    return <LoginPage onLogin={handleLogin} />;
  }

  // Logged in → show admin dashboard
  return <AdminDashboard token={token} onLogout={handleLogout} />;
}
