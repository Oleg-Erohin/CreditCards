package com.oleg.credit_cards.dal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleg.credit_cards.consts.Consts;
import com.oleg.credit_cards.dto.Card;
import com.oleg.credit_cards.enums.ErrorType;
import com.oleg.credit_cards.exceptions.ApplicationException;
import com.oleg.credit_cards.utils.CommonFunctions;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.util.List;

@Repository
public class CardsDal {
    public List<Card> getCards() throws ApplicationException {
        try {
            String jsonString = CommonFunctions.getJsonStringByFilePath(Consts.cardsJsonFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Card> cards = objectMapper.readValue(jsonString, new TypeReference<List<Card>>() {
            });
            return cards;
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Could not get cards", e);
        }
    }

    public Card getCardById(long cardNumber) throws ApplicationException {
        List<Card> allCards = getCards();
        for (int i = 0; i < allCards.size(); i++) {
            Card card = allCards.get(i);
            if (card.getCardNumber() == cardNumber) {
                return card;
            }
        }
        return null;
    }

    public void increaseCreditLimit(long cardNumber, int newCreditLimit) throws ApplicationException {
        try {
            List<Card> allCards = getCards();
            for (int i = 0; i < allCards.size(); i++) {
                Card card = allCards.get(i);
                if (card.getCardNumber() == cardNumber) {
                    card.setCreditLimit(newCreditLimit);
                    allCards.set(i, card);
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(allCards);
            try (FileWriter fileWriter = new FileWriter(Consts.cardsJsonFilePath)) {
                fileWriter.write(jsonString);
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Could not update card", e);
        }
    }
}
