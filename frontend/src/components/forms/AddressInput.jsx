function AddressInput({address, onChange}) {
    return (
        <div className="grid-container">
            <div className="grid-item">
                <input
                    name="country"
                    type="text"
                    value={address.country || ''}
                    onChange={onChange}
                    placeholder="Country"/>
            </div>
            <div className="grid-item">
                <input
                    name="county"
                    type="text"
                    value={address.county || ''}
                    onChange={onChange}
                    placeholder="County"/>
            </div>
            <div className="grid-item">
                <input
                    name="city"
                    type="text"
                    value={address.city || ''}
                    onChange={onChange}
                    placeholder="City"/>
            </div>
            <div className="grid-item">
                <input
                    name="street"
                    type="text"
                    value={address.street || ''}
                    onChange={onChange}
                    placeholder="Street"/>
            </div>
            <div className="grid-item">
                <input
                    name="streetNumber"
                    type="text"
                    value={address.streetNumber || ''}
                    onChange={onChange}
                    placeholder="Street Number"/>
            </div>
            <div className="grid-item">
                <input
                    name="block"
                    type="text"
                    value={address.block || ''}
                    onChange={onChange}
                    placeholder="Block"/>
            </div>
            <div className="grid-item">
                <input
                    name="stairCase"
                    type="text"
                    value={address.stairCase || ''}
                    onChange={onChange}
                    placeholder="Staircase"/>
            </div>
            <div className="grid-item">
                <input
                    name="floor"
                    type="text"
                    value={address.floor || ''}
                    onChange={onChange}
                    placeholder="Floor"/>
            </div>
            <div className="grid-item">
                <input
                    name="apartmentNumber"
                    type="text"
                    value={address.apartmentNumber || ''}
                    onChange={onChange}
                    placeholder="Apartment Number"/>
            </div>
        </div>
    );
}

export default AddressInput;