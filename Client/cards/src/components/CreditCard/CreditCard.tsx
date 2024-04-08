import Modal from 'react-modal';
import { ICard } from '../../models/ICard';
import { useEffect, useState } from 'react';
import axios from 'axios';
import img1 from '../../Images/img1.jpg';
import img2 from '../../Images/img2.jpg';
import img3 from '../../Images/img3.jpg';
import img4 from '../../Images/img4.jpg';

function CreditCard(props: ICard) {
    Modal.setAppElement('#root');

    const [modalIsOpen, setIsOpen] = useState(false);
    const [formData, setFormData] = useState({
        cardNumber: props.cardNumber,
        requestedCreditLimit: 0,
        occupation: "OTHER",
        averageMonthlyIncome: 0
    });
    const [isRequestValid, setIsRequestValid] = useState(false);

    function inputChanged(event: any) {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    useEffect(() => {
        validateInputs()
    }, [formData]);

    function validateInputs() {
        const isValid: boolean =
            formData.requestedCreditLimit >= 0 &&
            formData.averageMonthlyIncome >= 0 &&
            formData.requestedCreditLimit <= 100000 &&
            formData.averageMonthlyIncome <= 2147483647;
        setIsRequestValid(isValid);
    }

    async function onRequestClicked() {
        try {
            await axios.post("http://localhost:8080/cards/increaseCreditLimit", formData)
                .then(async () => {
                    alert("Credit limit updated successfully");
                    closeModal();
                    window.location.reload();
                })
                .catch(error => {
                    alert(error.response.data.errorMessage);
                })
        } catch (error: any) {
            alert(error.response.data.errorMessage);
        }
    }

    function openModal() {
        setIsOpen(true);
    };

    function closeModal() {
        setIsOpen(false);
    };

    let imagePathInFolder;
    switch (props.imagePath) {
        case 'img1':
            imagePathInFolder = img1;
            break;
        case 'img2':
            imagePathInFolder = img2;
            break;
        case 'img3':
            imagePathInFolder = img3;
            break;
        case 'img4':
            imagePathInFolder = img4;
            break;
        default:
            imagePathInFolder = '';
    }

    function formatCreditCardNumber(cardNumber: number): string {
        const cardNumberString = cardNumber.toString();
        return cardNumberString.match(/\d{4}/g)?.join(' ') || '';
    }

    return (
        <div className="card">
            <img src={imagePathInFolder} className="card-img-top" alt="Card" style={{ height: '200px', objectFit: 'cover' }} />
            <div className="card-body">
                <h5 className="card-title">{props.bankName}</h5>
                <p className="card-text">{formatCreditCardNumber(props.cardNumber)}</p>
                <button className="btn btn-secondary" onClick={openModal}>Increase Credit Limit</button>
            </div>

            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} className="modal-dialog-centered" id='Modal'>
                <div className="modal-content d-flex align-items-center" style={{ minHeight: "100vh" }}>
                    <div className="mx-auto mt-5" style={{ border: '0.4vmin solid darkgrey', backgroundColor: 'lightgrey', borderRadius: '1vmin', padding: "20px" }}>
                        <div className="modal-header">
                            <h5 className="modal-title fw-bold">Increase Credit Limit</h5>
                        </div>
                        <div className="modal-body text-center">                                    <div className="mb-3">
                            <p>Current Credit Limit: {props.creditLimit}</p>
                            <label htmlFor="requestedCreditLimit" className="form-label">Requested Credit Limit:</label>
                            <input
                                type="number"
                                className="form-control"
                                id="requestedCreditLimit"
                                name="requestedCreditLimit"
                                onChange={inputChanged}
                                min="0"
                                max="100000"
                            />
                        </div>
                            <div className="mb-3">
                                <label htmlFor="occupation" className="form-label">Current occupation type:</label>
                                <select
                                    className="form-select"
                                    id="occupation"
                                    name="occupation"
                                    onChange={inputChanged}
                                >
                                    <option value="OTHER">Other</option>
                                    <option value="EMPLOYEE">Employee</option>
                                    <option value="SELFEMPLOYED">Self-employed</option>
                                </select>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="averageMonthlyIncome" className="form-label">Average monthly income:</label>
                                <input
                                    type="number"
                                    className="form-control"
                                    id="averageMonthlyIncome"
                                    name="averageMonthlyIncome"
                                    onChange={inputChanged}
                                    min="0"
                                    max="2147483647"
                                />
                            </div>
                        </div>
                        <div className="modal-footer justify-content-center flex-wrap">
                            <button type="button" className="btn btn-secondary my-2 mx-1" onClick={closeModal}>Close</button>
                            <button type="button" className="btn btn-secondary my-2 mx-1" onClick={onRequestClicked} disabled={!isRequestValid}>Increase Credit Limit</button>
                        </div>
                    </div>
                </div>
            </Modal>
        </div>
    );
}

export default CreditCard;