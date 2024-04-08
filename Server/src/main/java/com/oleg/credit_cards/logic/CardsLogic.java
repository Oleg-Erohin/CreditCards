package com.oleg.credit_cards.logic;

import com.oleg.credit_cards.dal.CardsDal;
import com.oleg.credit_cards.dto.Card;
import com.oleg.credit_cards.enums.ErrorType;
import com.oleg.credit_cards.enums.Occupation;
import com.oleg.credit_cards.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CardsLogic {
    private CardsDal cardsDal;

    @Autowired
    public CardsLogic(CardsDal cardsDal) {
        this.cardsDal = cardsDal;
    }

    public List<Card> getCardsByFilters(boolean showIsBlocked, boolean showIsUnblocked, String cardsNumbersToShow, int[] banksCodesToShow) throws ApplicationException {
        return this.cardsDal.getCardsByFilters(showIsBlocked, showIsUnblocked, cardsNumbersToShow, banksCodesToShow);
    }

    public List<Card> getAllCards() throws ApplicationException {
        return this.cardsDal.getAllCards();
    }

    public void increaseCreditLimit(long cardNumber, int requestedCreditLimit, String occupation, int averageMonthlyIncome) throws ApplicationException {
        validateRequestedCreditLimit(requestedCreditLimit, cardNumber);
        validateLimitPerOccupation(requestedCreditLimit, occupation, averageMonthlyIncome);
        validateIsCardBlocked(cardNumber);
        ValidateIncome(averageMonthlyIncome);
        ValidateDateOfIssue(cardNumber);

        this.cardsDal.increaseCreditLimit(cardNumber, requestedCreditLimit);
    }

    private void validateRequestedCreditLimit(int requestedCreditLimit, long cardNumber) throws ApplicationException {
        if (requestedCreditLimit > 100000) {
            throw new ApplicationException(ErrorType.REQUEST_DECLINED);
        }
        Card card = cardsDal.getCardById(cardNumber);
        if (card.getCreditLimit() > requestedCreditLimit) {
            throw new ApplicationException(ErrorType.CURRENT_CREDIT_LIMIT_HIGHER);
        }
    }

    private void validateLimitPerOccupation(int requestedCreditLimit, String occupation, int averageMonthlyIncome) throws ApplicationException {
        if (occupation.equals(Occupation.EMPLOYEE.toString())) {
            if (requestedCreditLimit > (averageMonthlyIncome / 2)) {
                throw new ApplicationException(ErrorType.REQUESTED_LIMIT_TOO_HIGH);
            }
        } else if (occupation.equals(Occupation.SELFEMPLOYED.toString())) {
            if (requestedCreditLimit > (averageMonthlyIncome / 3)) {
                throw new ApplicationException(ErrorType.REQUESTED_LIMIT_TOO_HIGH);
            }
        } else {
            throw new ApplicationException(ErrorType.REQUEST_DECLINED);
        }
    }

    private void validateIsCardBlocked(long cardNumber) throws ApplicationException {
        Card card = cardsDal.getCardById(cardNumber);
        boolean isCardBlocked = card.isBlocked();
        if (isCardBlocked) {
            throw new ApplicationException(ErrorType.CARD_BLOCKED);
        }
    }

    private void ValidateIncome(int averageMonthlyIncome) throws ApplicationException {
        if (averageMonthlyIncome < 12000) {
            throw new ApplicationException(ErrorType.INCOME_TOO_LOW);
        }
    }

    private void ValidateDateOfIssue(long cardNumber) throws ApplicationException {
        Card card = cardsDal.getCardById(cardNumber);
        Date dateOfIssue = card.getDateOfIssue();
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);
        Date threeMonthsAgoAsDate = Date.from(threeMonthsAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (dateOfIssue.after(threeMonthsAgoAsDate)) {
            throw new ApplicationException(ErrorType.CARD_TOO_NEW);
        }
    }
}
