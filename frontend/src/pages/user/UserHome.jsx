import { useEffect, useState } from "react";
import "./UserHome.css";
import UserNavBar from "../../components/navbars/UserNavBar.jsx";
import FranchiseForm from "../../components/forms/FranchiseForm.jsx";

function UserHome() {
    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        const storedUser = localStorage.getItem("currentUser");

        if (storedUser) {
            setCurrentUser(JSON.parse(storedUser));
        }
    }, []);

    if (!currentUser) {
        return <span>No user set!</span>;
    }

    return (
        <main>
            <UserNavBar/>
            <div>
                <h1>User dashboard</h1>
                <p><strong>Email:</strong> {currentUser.email}</p>
            </div>
        </main>
    );
}

export default UserHome;