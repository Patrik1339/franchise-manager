import { useState } from "react";
import { createPortal } from "react-dom";
import "./Form.css";

function AddUserForm({ franchiseId, onClose }) {
    const [emailToSearch, setEmailToSearch] = useState("");
    const [foundUser, setFoundUser] = useState(null);
    const [selectedRole, setSelectedRole] = useState("MANAGER");
    const [searchMessage, setSearchMessage] = useState("");

    const handleSearch = async (e) => {
        e.preventDefault();
        setSearchMessage("");
        setFoundUser(null);
        
        if (!emailToSearch) return;

        try {
            const response = await fetch(`http://localhost:8080/franchise-manager/users/${emailToSearch}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                credentials: 'include'
            });

            if (response.ok) {
                const data = await response.json();
                if (data && data.length > 0) {
                    setFoundUser(data[0]);
                    setSearchMessage("User found!");
                } else {
                    setSearchMessage("User not found.");
                }
            } else {
                setSearchMessage("Error fetching user.");
            }
        } catch (error) {
            console.error("Error searching user: ", error);
            setSearchMessage("Network error.");
        }
    };

    const handleAddUser = async (e) => {
        e.preventDefault();
        if (!foundUser || !franchiseId) return;

        try {
            const response = await fetch(`http://localhost:8080/franchise-manager/legal_entity/franchises/${franchiseId}/users`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({
                    userDTO: foundUser,
                    businessRole: selectedRole
                })
            });

            if (response.ok) {
                alert("User assigned successfully!");
                onClose();
            } else {
                alert("Error assigning user.");
            }
        } catch (error) {
            console.error("Error assigning user: ", error);
            alert("Network error.");
        }
    };

    return createPortal(
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" style={{ maxWidth: '500px' }} onClick={(e) => e.stopPropagation()}>
                <div className="grid-container">
                    <div className="grid-item full-width">
                        <h2>Manage Users</h2>
                    </div>

                    <div className="grid-item full-width" style={{ display: 'flex', gap: '10px' }}>
                        <input 
                            type="email" 
                            placeholder="Enter user email..." 
                            value={emailToSearch}
                            onChange={(e) => setEmailToSearch(e.target.value)}
                            style={{ flex: 1 }}
                        />
                        <button type="button" onClick={handleSearch}>Search</button>
                    </div>

                    {searchMessage && (
                        <div className="grid-item full-width" style={{ color: foundUser ? 'green' : 'red' }}>
                            {searchMessage}
                        </div>
                    )}

                    {foundUser && (
                        <>
                            <div className="grid-item full-width">
                                <label>User Email:</label>
                                <input type="text" value={foundUser.email} disabled />
                            </div>
                            <div className="grid-item full-width">
                                <label>Assign Role:</label>
                                <select 
                                    value={selectedRole}
                                    onChange={(e) => setSelectedRole(e.target.value)}
                                >
                                    <option value="FRANCHISOR">Franchisor</option>
                                    <option value="FRANCHISEE">Franchisee</option>
                                    <option value="MANAGER">Manager</option>
                                    <option value="EMPLOYEE">Employee</option>
                                </select>
                            </div>
                        </>
                    )}

                    <div className="grid-item full-width form-actions" style={{ marginTop: '20px' }}>
                        <button type="button" onClick={handleAddUser} disabled={!foundUser}>Assign Role</button>
                        <button type="button" onClick={onClose}>Close</button>
                    </div>
                </div>
            </div>
        </div>,
        document.body
    );
}

export default AddUserForm;
