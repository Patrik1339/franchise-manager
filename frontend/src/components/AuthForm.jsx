import { useState } from "react";
import "./authForm.css";

function AuthForm() {
    const [isLogin, setIsLogin] = useState(true);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();
        console.log(isLogin ? "Sending login request" : "Sending register request");

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
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                const data = await response.json();
                console.log("Succes:", data);
            } else {
                console.error("Authentication error, status:", response.status);
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" placeholder="Email" value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <input type="password" placeholder="Password" value={password}
                onChange={(e) => setPassword(e.target.value)}
            />

            <button type="submit">
                {isLogin ? "Login" : "Register"}
            </button>

            <span onClick={() => setIsLogin(!isLogin)}>
                {isLogin
                    ? "Don't have an account? Click here to register."
                    : "Already have an account? Click here to login."}
            </span>
        </form>
    );
}

export default AuthForm;