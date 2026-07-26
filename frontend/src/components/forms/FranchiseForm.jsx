import { useState, useEffect } from "react";
import { createPortal } from "react-dom";
import AddressForm from "./AddressForm.jsx";
import "./Form.css";

function FranchiseForm({ franchise, isRoot, parentFranchisorId, onClose, onSave }) {
    const emptyForm = {
        name: "",
        email: "",
        phoneNumber: "",
        establishmentDate: "",
        taxIdentificationNumber: "",
        tradeRegistryNumber: "",
        address: {
            country: "",
            county: "",
            city: "",
            street: "",
            streetNumber: "",
            block: "",
            staircase: "",
            floor: "",
            apartmentNumber: ""
        }
    };

    const [formData, setFormData] = useState(emptyForm);

    useEffect(() => {
        if (franchise) {
            setFormData(franchise);
        } else {
            setFormData(emptyForm);
        }
    }, [franchise]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleAddressChange = (e) => {
        setFormData({
            ...formData,
            address: {
                ...formData.address,
                [e.target.name]: e.target.value
            }
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const isUpdate = !!franchise?.id;
        let url = "";
        let method = "";
        const user = JSON.parse(localStorage.getItem('currentUser'));

        let payload = {};

        if (isUpdate) {
            url = `http://localhost:8080/franchise-manager/legal_entity/franchises/${franchise.id}`;
            method = 'PUT';
            payload = { legalEntityDTO: formData };
        } else if (isRoot) {
            url = `http://localhost:8080/franchise-manager/legal_entity/create`;
            method = 'POST';
            payload = { userDTO: user, legalEntityDTO: formData };
        } else {
            url = `http://localhost:8080/franchise-manager/legal_entity/franchises/create`;
            method = 'POST';
            payload = { userDTO: user, franchisorId: parentFranchisorId, legalEntityDTO: formData };
        }
        
        const token = localStorage.getItem('token');
        if (!token) {
            alert('You are not authenticated. Please log in again.');
            return;
        }

        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                alert(`Franchise ${isUpdate ? 'updated' : 'created'} successfully!`);
                if (onSave) onSave();
            } else {
                alert(`Error ${isUpdate ? 'updating' : 'creating'} franchise.`);
            }
        } catch (error) {
            console.error("Network error saving franchise: ", error);
        }
    };

    return createPortal(
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <form className="grid-container" onSubmit={handleSubmit}>
                <div className="grid-item full-width">
                    <h2>Franchise Details</h2>
                </div>

                <div className="grid-item">
                    <label htmlFor="input-name">Name:</label>
                    <input id="input-name" name="name" type="text" placeholder="Name" value={formData.name} onChange={handleChange} />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-email">Email:</label>
                    <input id="input-email" name="email" type="text" placeholder="Email" value={formData.email} onChange={handleChange} />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-phone-number">Phone number:</label>
                    <input id="input-phone-number" name="phoneNumber" type="text" placeholder="Phone number" value={formData.phoneNumber} onChange={handleChange} />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-establishment-date">Establishment date:</label>
                    <input id="input-establishment-date" name="establishmentDate" type="date" placeholder="Establishment date" value={formData.establishmentDate} onChange={handleChange} />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-tax-identification-number">Tax identification number:</label>
                    <input id="input-tax-identification-number" name="taxIdentificationNumber" type="text" placeholder="Tax identification number" value={formData.taxIdentificationNumber} onChange={handleChange} />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-trade-registry-number">Trade registry number:</label>
                    <input id="input-trade-registry-number" name="tradeRegistryNumber" type="text" placeholder="Trade registry number" value={formData.tradeRegistryNumber} onChange={handleChange} />
                </div>

                <div className="grid-item full-width form-section-title">
                    <h2>Address</h2>
                </div>

                <AddressForm address={formData.address} onChange={handleAddressChange} />

                <div className="grid-item full-width form-actions">
                    <button type="submit">Save</button>
                    <button type="button" onClick={onClose}>Close</button>
                </div>
            </form>
            </div>
        </div>,
        document.body
    );
}

export default FranchiseForm;