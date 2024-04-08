import CreditCard from "../CreditCard/CreditCard";
import { useEffect, useState } from "react";
import { ICard } from "../../models/ICard";
import axios from "axios";
import { IFilterData } from "../../models/IFilterData";
import FiltersMenu from "../FilterMenu/FiltersMenu";
import { IBank } from "../../models/IBank";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from 'react-bootstrap';

function CardsContainer() {

    const [filtersData, setFiltersData] = useState<IFilterData>({
        showIsBlocked: true,
        showIsUnblocked: true,
        cardsNumbersToShow: "",
        banksCodesToShow: []
    })

    const [cards, setCards] = useState<ICard[]>([]);
    const [banks, setBanks] = useState<IBank[]>([]);

    async function fetchData() {
        return new Promise<void>((resolve, reject) => {
            Promise.all([getCards(), getBanks()])
                .then(([cardsData, banksData]) => {
                    if (cardsData && banksData) {
                        updateBankNames(cardsData, banksData);
                        resolve();
                    } else {
                        reject("Failed to fetch cards or banks");
                    }
                })
                .catch(error => {
                    alert(error.response.data.errorMessage);
                });
        });
    }

    useEffect(() => {
        fetchData();
    }, [filtersData.showIsBlocked, filtersData.showIsUnblocked, filtersData.cardsNumbersToShow, filtersData.banksCodesToShow]);

    async function getCards() {
        try {
            const response = await axios.get(`http://localhost:8080/cards/byFilters`, {
                params: {
                    showIsBlocked: filtersData.showIsBlocked,
                    showIsUnblocked: filtersData.showIsUnblocked,
                    cardsNumbersToShow: filtersData.cardsNumbersToShow,
                    banksCodesToShow: filtersData.banksCodesToShow.join(',')
                },
                headers: { "Authorization": localStorage.getItem("authToken") }
            });
            const responseCards: ICard[] = response.data;

            setCards(responseCards);
            return responseCards;

        } catch (error: any) {
            alert(error.response.data.errorMessage);
        }
    }

    async function getBanks() {
        try {
            let response = await axios.get(`http://localhost:8080/banks`,
                { headers: { "Authorization": localStorage.getItem("authToken") } });
            let responseBanks: IBank[] = response.data;

            setBanks(responseBanks);
            return responseBanks;

        } catch (error: any) {
            alert(error.response.data.errorMessage);
        }
    }

    function updateBankNames(cardsData: ICard[], banksData: IBank[]) {
        const updatedCards = cardsData.map((card) => {
            const bank = banksData.find((bank) => bank.code === card.bankCode);
            if (bank) {
                return {
                    ...card,
                    bankName: bank.name
                };
            }
            return card;
        });
        setCards(updatedCards);
    }

    return (
        <Container className="mt-4">
            <FiltersMenu setFiltersData={setFiltersData} banks={banks} />
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 pb-3" style={{ marginTop: '10px', marginBottom: '10px', border: '0.4vmin solid darkgrey', borderRadius: '1vmin', backgroundColor: 'lightgrey' }}>
                {cards.length > 0 ? (
                    cards.map((card, index) => (
                        <div key={index} className="col">
                            <CreditCard
                                cardNumber={card.cardNumber}
                                imagePath={card.imagePath}
                                bankName={card.bankName}
                                bankCode={card.bankCode}
                                isBlocked={card.isBlocked}
                                creditLimit={card.creditLimit}
                            />
                        </div>
                    ))
                ) : (
                    <p className="col">No credit cards to present</p>
                )}
            </div>
        </Container>)
}

export default CardsContainer;