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

    public List<Card> getCards() throws ApplicationException {
        return this.cardsDal.getCards();
    }

    public void increaseCreditLimit(long cardNumber, Date dateOfIssue, int requestedCreditLimit, String occupation, int averageMonthlyIncome) throws ApplicationException {
        validateRequestedCreditLimit(requestedCreditLimit);
        validateLimitPerOccupation(requestedCreditLimit, occupation, averageMonthlyIncome);
        validateIsCardBlocked(cardNumber);
        ValidateIncome(averageMonthlyIncome);
        ValidateDateOfIssue(dateOfIssue);

        this.cardsDal.increaseCreditLimit(cardNumber, requestedCreditLimit);
    }

    private void validateRequestedCreditLimit(int requestedCreditLimit) throws ApplicationException {
        if(requestedCreditLimit>100000){
            throw new ApplicationException(ErrorType.REQUEST_DECLINED);
        }
    }

    private void validateLimitPerOccupation(int requestedCreditLimit, String occupation, int averageMonthlyIncome) throws ApplicationException {
        if (occupation == Occupation.EMPLOYEE.toString()){
            if(requestedCreditLimit > (averageMonthlyIncome/2)) {
                throw new ApplicationException(ErrorType.REQUESTED_LIMIT_TOO_HIGH);
            }
        } else if (occupation == Occupation.SELFEMPLOYED.toString()){
            if(requestedCreditLimit > (averageMonthlyIncome/3)) {
                throw new ApplicationException(ErrorType.REQUESTED_LIMIT_TOO_HIGH);
            }
        } else {
            throw new ApplicationException(ErrorType.REQUEST_DECLINED);
        }
    }

    private void validateIsCardBlocked(long cardNumber) throws ApplicationException {
        Card card = cardsDal.getCardById(cardNumber);
        boolean isCardBlocked = card.isBlocked();
        if(isCardBlocked){
            throw new ApplicationException(ErrorType.CARD_BLOCKED);
        }
    }

    private void ValidateIncome(int averageMonthlyIncome) throws ApplicationException {
        if(averageMonthlyIncome<12000){
            throw new ApplicationException(ErrorType.INCOME_TOO_LOW);
        }
    }

    private void ValidateDateOfIssue(Date dateOfIssue) throws ApplicationException {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);
        Date threeMonthsAgoAsDate = Date.from(threeMonthsAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (dateOfIssue.after(threeMonthsAgoAsDate)) {
            throw new ApplicationException(ErrorType.CARD_TOO_NEW);
        }
    }
}
