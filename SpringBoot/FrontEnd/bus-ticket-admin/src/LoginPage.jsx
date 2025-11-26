import { useState } from "react";
import { login } from "./api";

function LoginPage({ onLogin }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const token = await login(username, password);

            // store token (you can also use context / redux later)
            localStorage.setItem("authToken", token);

            if (onLogin) {
                onLogin(token);
            }
        } catch (err) {
            console.error(err);
            setError("Invalid username or password");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={styles.container}>
            <form onSubmit={handleSubmit} style={styles.form}>
                <h2 style={styles.title}>Bus Ticket Admin Login</h2>

                <div style={styles.field}>
                    <label style={styles.label}>Username</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        style={styles.input}
                        placeholder="Enter username"
                        required
                    />
                </div>

                <div style={styles.field}>
                    <label style={styles.label}>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={styles.input}
                        placeholder="Enter password"
                        required
                    />
                </div>

                {error && <div style={styles.error}>{error}</div>}

                <button type="submit" style={styles.button} disabled={loading}>
                    {loading ? "Logging in..." : "Login"}
                </button>
            </form>
        </div>
    );
}

const styles = {
    container: {
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "#f4f6f8",
    },
    form: {
        width: "100%",
        maxWidth: "360px",
        padding: "24px",
        borderRadius: "12px",
        background: "#ffffff",
        boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
    },
    title: {
        marginBottom: "16px",
        textAlign: "center",
        color: "#222",
    },
    field: {
        marginBottom: "12px",
    },
    label: {
        display: "block",
        marginBottom: "4px",
        fontSize: "0.9rem",
        color: "#555",
    },
    input: {
        width: "100%",
        padding: "8px 10px",
        borderRadius: "6px",
        border: "1px solid #ccc",
        fontSize: "0.95rem",
    },
    error: {
        marginBottom: "8px",
        color: "#b00020",
        fontSize: "0.85rem",
    },
    button: {
        width: "100%",
        padding: "10px",
        borderRadius: "6px",
        border: "none",
        background: "#0f88a1",
        color: "#fff",
        fontSize: "1rem",
        cursor: "pointer",
    },
};

export default LoginPage;
