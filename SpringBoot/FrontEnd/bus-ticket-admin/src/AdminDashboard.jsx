// src/AdminDashboard.jsx
import { useEffect, useState } from "react";
import {
    getRoutes,
    getAllTickets,
    getAllValidations,
    createRoute,
    updateRoute,
    deleteRoute,
    getTicketsByRoute,
} from "./api";

export default function AdminDashboard({ token, onLogout }) {
    const [routes, setRoutes] = useState([]);
    const [tickets, setTickets] = useState([]);
    const [allTickets, setAllTickets] = useState([]);
    const [validations, setValidations] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    // Create/Edit Route form state
    const [start, setStart] = useState("");
    const [stop, setStop] = useState("");
    const [fare, setFare] = useState("");
    const [creating, setCreating] = useState(false);
    const [createError, setCreateError] = useState("");
    const [createSuccess, setCreateSuccess] = useState("");
    const [editingRouteId, setEditingRouteId] = useState(null);

    // Tickets filter
    const [ticketRouteFilter, setTicketRouteFilter] = useState("all");

    useEffect(() => {
        async function loadData() {
            try {
                setLoading(true);
                setError("");
                const [routesRes, ticketsRes, validationsRes] = await Promise.all([
                    getRoutes(token),
                    getAllTickets(token),
                    getAllValidations(token),
                ]);
                setRoutes(routesRes);
                setTickets(ticketsRes);
                setAllTickets(ticketsRes);
                setValidations(validationsRes);
            } catch (err) {
                console.error(err);
                setError(
                    "Failed to load admin data. Check if this user has ADMIN role or if backend is running."
                );
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, [token]);

    const handleCreateOrUpdateRoute = async (e) => {
        e.preventDefault();
        setCreateError("");
        setCreateSuccess("");
        setCreating(true);

        try {
            const routeData = {
                start,
                stop,
                fare: parseFloat(fare),
            };

            if (editingRouteId) {
                // update
                const updated = await updateRoute(token, editingRouteId, routeData);
                setRoutes((prev) =>
                    prev.map((r) => (r.id === editingRouteId ? updated : r))
                );
                setCreateSuccess("Route updated successfully");
            } else {
                // create
                const newRoute = await createRoute(token, routeData);
                setRoutes((prev) => [...prev, newRoute]);
                setCreateSuccess("Route created successfully");
            }

            // reset form
            setStart("");
            setStop("");
            setFare("");
            setEditingRouteId(null);
        } catch (err) {
            console.error(err);
            setCreateError(err.message || "Failed to save route");
        } finally {
            setCreating(false);
        }
    };

    const handleEditClick = (route) => {
        setStart(route.start);
        setStop(route.stop);
        setFare(route.fare);
        setEditingRouteId(route.id);
        setCreateError("");
        setCreateSuccess("");
    };

    const handleCancelEdit = () => {
        setStart("");
        setStop("");
        setFare("");
        setEditingRouteId(null);
        setCreateError("");
        setCreateSuccess("");
    };

    const handleDeleteClick = async (routeId) => {
        const confirmed = window.confirm(
            `Are you sure you want to delete route #${routeId}?`
        );
        if (!confirmed) return;

        try {
            await deleteRoute(token, routeId);
            setRoutes((prev) => prev.filter((r) => r.id !== routeId));
            // Note: tickets linked to this route still exist in DB; you can handle cascade in backend if needed
        } catch (err) {
            console.error(err);
            alert(err.message || "Failed to delete route");
        }
    };

    const handleTicketFilterChange = async (e) => {
        const value = e.target.value;
        setTicketRouteFilter(value);

        if (value === "all") {
            setTickets(allTickets);
        } else {
            try {
                const filtered = await getTicketsByRoute(token, value);
                setTickets(filtered);
            } catch (err) {
                console.error(err);
                alert("Failed to load tickets for selected route");
            }
        }
    };

    if (loading) return <p style={styles.info}>Loading dashboard…</p>;
    if (error) return <p style={styles.error}>{error}</p>;

    return (
        <div style={styles.container}>
            <header style={styles.header}>
                <h1 style={styles.title}>Bus Ticket Admin Dashboard</h1>
                <button style={styles.logoutBtn} onClick={onLogout}>
                    Logout
                </button>
            </header>

            {/* Create / Edit Route Form */}
            <section style={styles.section}>
                <h2>{editingRouteId ? "Edit Route" : "Create Route"}</h2>
                <form style={styles.form} onSubmit={handleCreateOrUpdateRoute}>
                    <div style={styles.formRow}>
                        <div style={styles.formField}>
                            <label style={styles.label}>Start</label>
                            <input
                                type="text"
                                value={start}
                                onChange={(e) => setStart(e.target.value)}
                                style={styles.input}
                                required
                            />
                        </div>
                        <div style={styles.formField}>
                            <label style={styles.label}>Stop</label>
                            <input
                                type="text"
                                value={stop}
                                onChange={(e) => setStop(e.target.value)}
                                style={styles.input}
                                required
                            />
                        </div>
                        <div style={styles.formField}>
                            <label style={styles.label}>Fare</label>
                            <input
                                type="number"
                                value={fare}
                                onChange={(e) => setFare(e.target.value)}
                                style={styles.input}
                                required
                                min="0"
                            />
                        </div>
                    </div>
                    <div style={{ display: "flex", gap: "8px", alignItems: "center" }}>
                        <button type="submit" style={styles.primaryBtn} disabled={creating}>
                            {creating
                                ? editingRouteId
                                    ? "Updating..."
                                    : "Creating..."
                                : editingRouteId
                                    ? "Update Route"
                                    : "Create Route"}
                        </button>
                        {editingRouteId && (
                            <button
                                type="button"
                                style={styles.secondaryBtn}
                                onClick={handleCancelEdit}
                            >
                                Cancel
                            </button>
                        )}
                    </div>
                </form>
                {createError && <p style={styles.errorSmall}>{createError}</p>}
                {createSuccess && <p style={styles.successSmall}>{createSuccess}</p>}
            </section>

            {/* Routes Table */}
            <section style={styles.section}>
                <h2>Routes ({routes.length})</h2>
                <div style={styles.tableWrapper}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>ID</th>
                                <th style={styles.th}>Start</th>
                                <th style={styles.th}>Stop</th>
                                <th style={styles.th}>Fare</th>
                                <th style={styles.th}>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {routes.map((r) => (
                                <tr key={r.id}>
                                    <td style={styles.td}>{r.id}</td>
                                    <td style={styles.td}>{r.start}</td>
                                    <td style={styles.td}>{r.stop}</td>
                                    <td style={styles.td}>{r.fare}</td>
                                    <td style={styles.td}>
                                        <button
                                            style={styles.smallButton}
                                            onClick={() => handleEditClick(r)}
                                        >
                                            Edit
                                        </button>
                                        <button
                                            style={{ ...styles.smallButton, ...styles.deleteBtn }}
                                            onClick={() => handleDeleteClick(r.id)}
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>

            {/* Tickets Table with Route Filter */}
            <section style={styles.section}>
                <div style={styles.sectionHeader}>
                    <h2>Tickets ({tickets.length})</h2>
                    <div>
                        <label style={styles.label}>Filter by Route: </label>
                        <select
                            value={ticketRouteFilter}
                            onChange={handleTicketFilterChange}
                            style={styles.select}
                        >
                            <option value="all">All routes</option>
                            {routes.map((r) => (
                                <option key={r.id} value={r.id}>
                                    #{r.id} {r.start} → {r.stop}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>

                <div style={styles.tableWrapper}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>ID</th>
                                <th style={styles.th}>Route</th>
                                <th style={styles.th}>Fare</th>
                                <th style={styles.th}>Issued At</th>
                                <th style={styles.th}>Expires At</th>
                                <th style={styles.th}>Validated</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tickets.map((t) => (
                                <tr key={t.ticketId}>
                                    <td style={styles.td}>{t.ticketId}</td>
                                    <td style={styles.td}>
                                        {t.start} → {t.stop}
                                    </td>
                                    <td style={styles.td}>{t.fare}</td>
                                    <td style={styles.td}>{formatDate(t.issuedAt)}</td>
                                    <td style={styles.td}>{formatDate(t.expiresAt)}</td>
                                    <td style={styles.td}>{t.validated ? "✅" : "❌"}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>

            {/* Validation Logs Table */}
            <section style={styles.section}>
                <h2>Validation Logs ({validations.length})</h2>
                <div style={styles.tableWrapper}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>ID</th>
                                <th style={styles.th}>Ticket ID</th>
                                <th style={styles.th}>Passenger</th>
                                <th style={styles.th}>Collector</th>
                                <th style={styles.th}>Route</th>
                                <th style={styles.th}>Fare</th>
                                <th style={styles.th}>Status</th>
                                <th style={styles.th}>Validated At</th>
                            </tr>
                        </thead>
                        <tbody>
                            {validations.map((v) => (
                                <tr key={v.id}>
                                    <td style={styles.td}>{v.id}</td>
                                    <td style={styles.td}>{v.ticketId}</td>
                                    <td style={styles.td}>{v.passengerName}</td>
                                    <td style={styles.td}>{v.collectorName}</td>
                                    <td style={styles.td}>
                                        {v.start} → {v.stop}
                                    </td>
                                    <td style={styles.td}>{v.fare}</td>
                                    <td style={styles.td}>{v.status}</td>
                                    <td style={styles.td}>{formatDate(v.validatedAt)}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    );
}

function formatDate(value) {
    if (!value) return "-";
    try {
        return new Date(value).toLocaleString();
    } catch {
        return value;
    }
}

const styles = {
    container: {
        padding: "16px 24px",
        fontFamily:
            "system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif",
        background: "#f4f6f8",
        minHeight: "100vh",
        maxWidth: "1100px",   // limit width
        margin: "0 auto",     // center horizontally
        boxSizing: "border-box",
    },
    header: {
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        marginBottom: "16px",
    },
    title: {
        fontSize: "24px",
        fontWeight: 600,
        margin: 0,
        color: "#222",
    },
    logoutBtn: {
        padding: "6px 12px",
        borderRadius: "6px",
        border: "none",
        background: "#b00020",
        color: "#fff",
        cursor: "pointer",
    },
    section: {
        background: "#fff",
        padding: "12px 16px",
        borderRadius: "8px",
        marginBottom: "16px",
        boxShadow: "0 2px 6px rgba(0,0,0,0.05)",
    },
    tableWrapper: {
        width: "100%",
        overflowX: "auto",
        marginTop: "8px",
    },
    table: {
        width: "100%",
        borderCollapse: "collapse",
        fontSize: "0.9rem",
    },
    th: {
        textAlign: "left",
        padding: "8px 6px",
        fontSize: "0.8rem",
        fontWeight: 600,
        color: "#555",
        borderBottom: "1px solid #e0e0e0",
        background: "#fafafa",
    },
    td: {
        padding: "8px 6px",
        fontSize: "0.85rem",
        color: "#222",
        borderBottom: "1px solid #f0f0f0",
    },
    form: {
        marginTop: "8px",
        marginBottom: "4px",
    },
    formRow: {
        display: "flex",
        gap: "8px",
        marginBottom: "8px",
        flexWrap: "wrap",
    },
    formField: {
        flex: "1",
        minWidth: "120px",
    },
    label: {
        display: "block",
        marginBottom: "4px",
        fontSize: "0.85rem",
        color: "#555",
    },
    input: {
        width: "100%",
        padding: "6px 8px",
        borderRadius: "6px",
        border: "1px solid #ccc",
        fontSize: "0.9rem",
        background: "#ffffff",
        color: "#222",
        outline: "none",
    },
    primaryBtn: {
        padding: "8px 16px",
        borderRadius: "6px",
        border: "none",
        background: "#0f88a1",
        color: "#fff",
        cursor: "pointer",
        fontSize: "0.9rem",
    },
    secondaryBtn: {
        padding: "8px 16px",
        borderRadius: "6px",
        border: "1px solid #999",
        background: "#fff",
        color: "#333",
        cursor: "pointer",
        fontSize: "0.9rem",
    },
    smallButton: {
        padding: "4px 8px",
        borderRadius: "4px",
        border: "none",
        marginRight: "4px",
        cursor: "pointer",
        fontSize: "0.8rem",
        background: "#e0e0e0",
    },
    deleteBtn: {
        background: "#ff5252",
        color: "#fff",
    },
    info: {
        padding: "20px",
        textAlign: "center",
    },
    error: {
        padding: "20px",
        textAlign: "center",
        color: "#b00020",
    },
    errorSmall: {
        marginTop: "4px",
        color: "#b00020",
        fontSize: "0.85rem",
    },
    successSmall: {
        marginTop: "4px",
        color: "#1b5e20",
        fontSize: "0.85rem",
    },
    sectionHeader: {
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
    },
    select: {
        padding: "4px 8px",
        borderRadius: "6px",
        border: "1px solid #ccc",
        fontSize: "0.9rem",
        background: "#ffffff",
        color: "#222",
    },
};
