import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Form.css";

function AuthForm() {
    const [isLogin, setIsLogin] = useState(true);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const payload = {
            userDTO: {
                email: email,
                password: password
            }
        };

        const url = isLogin
            ? "http://localhost:8080/franchise-manager/auth/login"
            : "http://localhost:8080/franchise-manager/auth/register";

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                credentials: "include",
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                if (isLogin) {
                    const data = await response.json();
                    const user = data.userDTO;

                    if (!data.token) {
                        alert("Eroare severa: Backend-ul nu a returnat un token! Te rog opreste backend-ul, apasa pe 'Reload All Maven Projects' si da-i un 'Rebuild Project' inainte sa il pornesti iar.");
                        return;
                    }

                    localStorage.setItem("currentUser", JSON.stringify(user));
                    localStorage.setItem("token", data.token);

                    const userRole = user["systemRole"];

                    if (userRole === "SYSTEM_ADMIN") {
                        navigate("/admin-home");
                    } else if (userRole === 'NORMAL_USER') {
                        navigate("/user-home");
                    }

                } else {
                    alert("Registration successful! Now you can login.");
                    setIsLogin(true);
                    setPassword("");
                }

            } else {
                console.error("Authentication error, status:", response.status);
                alert("Login error. Check the entered data.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("Server connection error.");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className="grid-item">
                <input
                    type="text"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
            </div>
            <div className="grid-item">
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
            </div>
            <div>
                <button type="submit">
                    {isLogin ? "Login" : "Register"}
                </button>
            </div>

            <span
                style={{ cursor: "pointer", color: "blue", textDecoration: "underline", display: "block", marginTop: "10px" }}
                onClick={() => setIsLogin(!isLogin)}
            >
                {isLogin
                    ? "Don't have an account? Click here to register."
                    : "Already have an account? Click here to login."}
            </span>
        </form>
    );
}

export default AuthForm;