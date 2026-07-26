import UserNavBar from "../../components/navbars/UserNavBar.jsx";
import FranchiseTable from "../../components/franchise/FranchiseTable.jsx";
import FranchiseForm from "../../components/forms/FranchiseForm.jsx";
import AddUserForm from "../../components/forms/AddUserForm.jsx";
import "./FranchisesPage.css";
import {useState} from "react";

function FranchisesPage() {
    const [selectedFranchise, setSelectedFranchise] = useState(null);
    const [activeModal, setActiveModal] = useState(null); // 'FRANCHISE_FORM' | 'ADD_USER_FORM' | null
    const [isRootEntity, setIsRootEntity] = useState(false);
    const [refreshTrigger, setRefreshTrigger] = useState(0);

    const [isEditing, setIsEditing] = useState(false);

    const handleSelectFranchise = (franchise) => {
        if (selectedFranchise && selectedFranchise.id === franchise.id) {
            setSelectedFranchise(null);
            setActiveModal(null);
            setIsEditing(false);
        } else {
            setSelectedFranchise(franchise);
            setIsRootEntity(false);
            setIsEditing(true);
            setActiveModal('FRANCHISE_FORM');
        }
    };

    const handleAddRootClick = () => {
        setSelectedFranchise(null);
        setIsRootEntity(true);
        setIsEditing(false);
        setActiveModal('FRANCHISE_FORM');
    };

    const handleAddFranchiseClick = () => {
        setIsRootEntity(false);
        setIsEditing(false);
        setActiveModal('FRANCHISE_FORM');
    };

    const handleAddUserClick = () => {
        setActiveModal('ADD_USER_FORM');
    };

    const handleModalClose = () => {
        setActiveModal(null);
        if (activeModal === 'FRANCHISE_FORM' && !selectedFranchise) {
            setIsRootEntity(false);
        }
        setIsEditing(false);
    };

    const handleModalSave = () => {
        setRefreshTrigger(prev => prev + 1);
        handleModalClose();
    };

    return (
        <main>
            <UserNavBar/>
            <div id="div-content">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                    <h2>My Legal Entities</h2>
                    <div style={{ display: 'flex', gap: '10px' }}>
                        <button onClick={handleAddRootClick} style={{ padding: '10px 20px', cursor: 'pointer', backgroundColor: '#4CAF50', color: 'white', border: 'none', borderRadius: '4px' }}>
                            Add Root Legal Entity
                        </button>
                        <button 
                            onClick={handleAddFranchiseClick} 
                            disabled={!selectedFranchise}
                            style={{ padding: '10px 20px', cursor: selectedFranchise ? 'pointer' : 'not-allowed', backgroundColor: selectedFranchise ? '#2196F3' : '#ccc', color: 'white', border: 'none', borderRadius: '4px' }}>
                            Add Child Franchise
                        </button>
                        <button 
                            onClick={handleAddUserClick} 
                            disabled={!selectedFranchise}
                            style={{ padding: '10px 20px', cursor: selectedFranchise ? 'pointer' : 'not-allowed', backgroundColor: selectedFranchise ? '#FF9800' : '#ccc', color: 'white', border: 'none', borderRadius: '4px' }}>
                            Manage Users
                        </button>
                    </div>
                </div>

                <FranchiseTable 
                    selectedFranchise={selectedFranchise}
                    onSelectFranchise={handleSelectFranchise}
                    refreshTrigger={refreshTrigger}
                />

                {activeModal === 'FRANCHISE_FORM' && (
                    <FranchiseForm 
                        franchise={isEditing ? selectedFranchise : null} 
                        isRoot={isRootEntity}
                        parentFranchisorId={selectedFranchise ? selectedFranchise.id : null}
                        onClose={handleModalClose}
                        onSave={handleModalSave}
                    />
                )}

                {activeModal === 'ADD_USER_FORM' && (
                    <AddUserForm 
                        franchiseId={selectedFranchise?.id}
                        onClose={handleModalClose}
                    />
                )}
            </div>
        </main>
    )
}

export default FranchisesPage;