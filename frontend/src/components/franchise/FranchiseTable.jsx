import { useState, useEffect } from "react";
import "./FranchiseTable.css";

function FranchiseTable({ franchisorId, selectedFranchise, onSelectFranchise, refreshTrigger }) {
    const [ franchises, setFranchises ] = useState([]);

    useEffect(() => {
        const fetchFranchises = async () => {
            try {
                const user = JSON.parse(localStorage.getItem('currentUser'));
                if (!user || !user.id) return;
                
                const response = await fetch(`http://localhost:8080/franchise-manager/legal_entity/my-entities/${user.id}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                });

                if (response.ok) {
                    const data = await response.json();
                    setFranchises(data.franchises || []);
                } else {
                    console.log("Error getting franchises: ", response.status);
                }
            } catch (error) {
                console.error("Network error: ", error);
            }
        };

        fetchFranchises();
    }, [franchisorId, refreshTrigger]);

    return (
        <table className="franchise-table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Tax Identification Number</th>
                <th>Address</th>
            </tr>
            </thead>
            <tbody>
            {franchises.map((franchise) => (
                <tr
                    key={franchise.id}
                    onClick={() => onSelectFranchise(franchise)}
                    className={`franchise-tr ${selectedFranchise?.id === franchise.id ? 'selected' : ''}`}
                >
                    <td>{franchise.name}</td>
                    <td>{franchise.taxIdentificationNumber}</td>
                    <td>
                        {franchise.address
                            ? `${franchise.address.city} / ${franchise.address.street} ${franchise.address.streetNumber}`
                            : 'N/A'
                        }
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

export default FranchiseTable;