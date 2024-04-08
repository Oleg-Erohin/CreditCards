import { useEffect, useState } from "react";
import { IFilterData } from "../../models/IFilterData";
import { IFilterPropsType } from "../../models/IFilterPropsType";

function FiltersMenu({ setFiltersData, banks }: IFilterPropsType) {
    const [showIsBlocked, setShowIsBlocked] = useState<boolean>(true);
    const [showIsUnblocked, setShowIsUnblocked] = useState<boolean>(true);
    const [cardsNumbersToShow, setCardsNumbersToShow] = useState<string>("");
    const [selectedBanks, setSelectedBanks] = useState<number[]>([]);
    const defaultFiltersData: IFilterData = {
        showIsBlocked: true,
        showIsUnblocked: true,
        cardsNumbersToShow: "",
        banksCodesToShow: []
    }

    useEffect(() => {
        updateFilters();
    }, [showIsBlocked, showIsUnblocked, cardsNumbersToShow, selectedBanks])

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target;
        if (name === "showIsBlocked") {
            setShowIsBlocked(checked);
        } else if (name === "showIsUnblocked") {
            setShowIsUnblocked(checked);
        }
    };

    const handleNumberInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target;
        setCardsNumbersToShow(value);
    };

    const handleBankCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { value, checked } = event.target;
        if (checked) {
            setSelectedBanks(prevState => [...prevState, Number(value)]);
        } else {
            setSelectedBanks(prevState => prevState.filter(bankCode => bankCode !== Number(value)));
        }
    };

    const updateFilters = () => {
        const filterData: IFilterData = {
            showIsBlocked: showIsBlocked,
            showIsUnblocked: showIsUnblocked,
            cardsNumbersToShow: cardsNumbersToShow,
            banksCodesToShow: selectedBanks
        };
        setFiltersData(filterData);
    }

    const resetFilters = () => {
        setShowIsBlocked(defaultFiltersData.showIsBlocked);
        setShowIsUnblocked(defaultFiltersData.showIsUnblocked);
        setCardsNumbersToShow(defaultFiltersData.cardsNumbersToShow);
        setSelectedBanks(defaultFiltersData.banksCodesToShow)
    };

    return (
        <div className="container mt-4">
            <div className="row">
                <div className="col-md-4 d-flex flex-column justify-content-center" style={{ borderRadius: '1vmin', border: '0.4vmin solid', borderColor: 'darkgrey', backgroundColor: 'lightgrey' }}>
                    <div className="form-check">
                        <input className="form-check-input bg-secondary border-secondary" type="checkbox" name="showIsBlocked" checked={showIsBlocked} onChange={handleCheckboxChange} />
                        <label className="form-check-label">Show Blocked Cards</label>
                    </div>
                    <div className="form-check mt-2">
                        <input className="form-check-input bg-secondary border-secondary" type="checkbox" name="showIsUnblocked" checked={showIsUnblocked} onChange={handleCheckboxChange} />
                        <label className="form-check-label">Show Unblocked Cards</label>
                    </div>
                </div>
                <div className="col-md-4 d-flex justify-content-center" style={{ borderRadius: '1vmin', border: '0.4vmin solid', borderColor: 'darkgrey', backgroundColor: 'lightgrey' }}>
                    <div className="form-group">
                        <label className="fw-bold">Card Number:</label>
                        <input type="number" className="form-control" value={cardsNumbersToShow} onChange={handleNumberInputChange} />
                    </div>
                </div>
                <div className="col-md-4 d-flex flex-column justify-content-center" style={{ borderRadius: '1vmin', border: '0.4vmin solid', borderColor: 'darkgrey', backgroundColor: 'lightgrey' }}>
                    <div className="form-group">
                        <label className="fw-bold">Banks:</label>
                        {banks.map((bank) => (
                            <div key={bank.code} className="form-check">
                                <input
                                    className="form-check-input bg-secondary border-secondary"
                                    type="checkbox"
                                    value={bank.code}
                                    checked={selectedBanks.includes(bank.code)}
                                    onChange={handleBankCheckboxChange}
                                />
                                <label className="form-check-label">{bank.name}</label>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
            <div className="row mt-4">
                <div className="col-md-12 d-flex justify-content-center">
                    <button className="btn btn-secondary" onClick={resetFilters}>Reset Filters</button>
                </div>
            </div>
        </div>
    )
}

export default FiltersMenu;
