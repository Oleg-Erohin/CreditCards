package com.oleg.credit_cards.controllers;

import com.oleg.credit_cards.dto.Card;
import com.oleg.credit_cards.dto.SuccessfulLoginDetails;
import com.oleg.credit_cards.logic.CardsLogic;
import com.oleg.credit_cards.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {
    private CardsLogic cardsLogic;

    @Autowired
    public CardsController(CardsLogic cardsLogic) {
        this.cardsLogic = cardsLogic;
    }

    @GetMapping
    public List<Card> getCards(@RequestHeader("Authorization") String token) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        return this.cardsLogic.getCards();
    }

    public void increaseCreditLimit(@RequestHeader("Authorization") String token,
                                    @RequestParam("cardNumber") long cardNumber,
                                    @RequestParam("DateOfIssue") Date dateOfIssue,
                                    @RequestParam("requestedCreditLimit") int requestedCreditLimit,
                                    @RequestParam("occupation") String occupation,
                                    @RequestParam("averageMonthlyIncome") int averageMonthlyIncome) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.cardsLogic.increaseCreditLimit(cardNumber, dateOfIssue, requestedCreditLimit, occupation, averageMonthlyIncome);
    }
}
