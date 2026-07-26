import {Link, useNavigate} from "react-router-dom";
import "./UserNavBar.css";

function UserNavBar() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('currentUser');
        navigate('/');
    };

    return (
        <aside>
            <ul>
                <li>
                    <Link to="/user-home">
                        Home
                    </Link>
                </li>
                <li>
                    <Link to="/franchises">
                        Franchises
                    </Link>
                </li>
                <li>
                    <Link to="/profile">
                        Profile
                    </Link>
                </li>
                <li>
                    <button onClick={handleLogout} style={{ background: 'none', border: 'none', color: 'inherit', font: 'inherit', cursor: 'pointer', padding: 0, textDecoration: 'underline' }}>
                        Logout
                    </button>
                </li>
            </ul>
        </aside>
    )
}

export default UserNavBar;