function AddressForm({ address, onChange }) {
    return (
        <>
                <div className="grid-item">
                    <label htmlFor="input-country">Country:</label>
                    <input
                        id="input-country"
                        name="country"
                        type="text"
                        value={address?.country || ''}
                        onChange={onChange}
                        placeholder="Country"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-county">County:</label>
                    <input
                        id="input-county"
                        name="county"
                        type="text"
                        value={address?.county || ''}
                        onChange={onChange}
                        placeholder="County"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-city">City:</label>
                    <input
                        id="input-city"
                        name="city"
                        type="text"
                        value={address?.city || ''}
                        onChange={onChange}
                        placeholder="City"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-street">Street:</label>
                    <input
                        id="input-street"
                        name="street"
                        type="text"
                        value={address?.street || ''}
                        onChange={onChange}
                        placeholder="Street"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-street-number">Street number:</label>
                    <input
                        id="input-street-number"
                        name="streetNumber"
                        type="text"
                        value={address?.streetNumber || ''}
                        onChange={onChange}
                        placeholder="Street number"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-block">Block (optional):</label>
                    <input
                        id="input-block"
                        name="block"
                        type="text"
                        value={address?.block || ''}
                        onChange={onChange}
                        placeholder="Block"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-staircase">Staircase (optional):</label>
                    <input
                        id="input-staircase"
                        name="staircase"
                        type="text"
                        value={address?.staircase || ''}
                        onChange={onChange}
                        placeholder="Staircase"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-floor">Floor (optional):</label>
                    <input
                        id="input-floor"
                        name="floor"
                        type="text"
                        value={address?.floor || ''}
                        onChange={onChange}
                        placeholder="Floor"
                    />
                </div>
                <div className="grid-item">
                    <label htmlFor="input-apartment-number">Apartment Number (optional):</label>
                    <input
                        id="input-apartment-number"
                        name="apartmentNumber"
                        type="text"
                        value={address?.apartmentNumber || ''}
                        onChange={onChange}
                        placeholder="Apartment number"
                    />
                </div>
        </>
    );
}

export default AddressForm;