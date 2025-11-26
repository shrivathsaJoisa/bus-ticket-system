// src/api.js
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

function authHeaders(token) {
  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };
}

// 🔹 LOGIN
export async function login(username, password) {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || "Login failed");
  }

  const token = await response.text();
  return token;
}

// 🔹 GET: all routes
export async function getRoutes(token) {
  const res = await fetch(`${API_BASE_URL}/routes`, {
    headers: authHeaders(token),
  });
  if (!res.ok) throw new Error("Failed to load routes");
  return res.json();
}

// 🔹 GET all tickets
export async function getAllTickets(token) {
  const res = await fetch(`${API_BASE_URL}/admin/tickets`, {
    headers: authHeaders(token),
  });
  if (!res.ok) throw new Error("Failed to load tickets");
  return res.json();
}

// 🔹 GET validation logs
export async function getAllValidations(token) {
  const res = await fetch(`${API_BASE_URL}/admin/validations`, {
    headers: authHeaders(token),
  });
  if (!res.ok) throw new Error("Failed to load validations");
  return res.json();
}

// 🔹 CREATE route
export async function createRoute(token, route) {
  const res = await fetch(`${API_BASE_URL}/routes`, {
    method: "POST",
    headers: authHeaders(token),
    body: JSON.stringify(route),
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to create route");
  }
  return res.json();
}

// 🔹 UPDATE route
export async function updateRoute(token, id, route) {
  const res = await fetch(`${API_BASE_URL}/routes/${id}`, {
    method: "PUT",
    headers: authHeaders(token),
    body: JSON.stringify(route),
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to update route");
  }
  return res.json();
}

// 🔹 DELETE route
export async function deleteRoute(token, id) {
  const res = await fetch(`${API_BASE_URL}/routes/${id}`, {
    method: "DELETE",
    headers: authHeaders(token),
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to delete route");
  }
  // no body expected
}

// 🔹 TICKETS BY ROUTE (for filter)
export async function getTicketsByRoute(token, routeId) {
  const res = await fetch(`${API_BASE_URL}/admin/tickets/by-route/${routeId}`, {
    headers: authHeaders(token),
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Failed to load tickets for route");
  }
  return res.json();
}
