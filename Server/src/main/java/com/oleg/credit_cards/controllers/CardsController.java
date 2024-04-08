package com.oleg.credit_cards.controllers;

import com.oleg.credit_cards.dto.Card;
import com.oleg.credit_cards.dto.IncreaseRequestParams;
import com.oleg.credit_cards.exceptions.ApplicationException;
import com.oleg.credit_cards.logic.CardsLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {
    private CardsLogic cardsLogic;

    @Autowired
    public CardsController(CardsLogic cardsLogic) {
        this.cardsLogic = cardsLogic;
    }

    @GetMapping("/byFilters")
    public List<Card> getCardsByFilters(@RequestParam("showIsBlocked") boolean showIsBlocked,
                                        @RequestParam("showIsUnblocked") boolean showIsUnblocked,
                                        @RequestParam("cardsNumbersToShow") String cardsNumbersToShow,
                                        @RequestParam("banksCodesToShow") int[] banksCodesToShow) throws Exception {
        return this.cardsLogic.getCardsByFilters(showIsBlocked, showIsUnblocked, cardsNumbersToShow, banksCodesToShow);
    }

    @GetMapping("/")
    public List<Card> getAllCards() throws Exception {
        return this.cardsLogic.getAllCards();
    }

    @PostMapping("/increaseCreditLimit")
    public void increaseCreditLimit(@RequestBody IncreaseRequestParams increaseRequestParams) throws ApplicationException {

        long cardNumber = increaseRequestParams.getCardNumber();
        int requestedCreditLimit = increaseRequestParams.getRequestedCreditLimit();
        String occupation = increaseRequestParams.getOccupation();
        int averageMonthlyIncome = increaseRequestParams.getAverageMonthlyIncome();

        this.cardsLogic.increaseCreditLimit(cardNumber, requestedCreditLimit, occupation, averageMonthlyIncome);
    }

}
