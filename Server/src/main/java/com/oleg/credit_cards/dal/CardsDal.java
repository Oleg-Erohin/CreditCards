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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CardsDal {
    public List<Card> getCardsByFilters(boolean showIsBlocked, boolean showIsUnblocked, String cardsNumbersToShow, int[] banksCodesToShow) throws ApplicationException {
        try {
            String jsonString = CommonFunctions.getJsonStringByFilePath(Consts.cardsJsonFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Card> cards = objectMapper.readValue(jsonString, new TypeReference<List<Card>>() {
            });

            Set<Integer> selectedBanksNumbersSet = new HashSet<Integer>();
            if (banksCodesToShow != null) {
                for (int bankCode : banksCodesToShow) {
                    selectedBanksNumbersSet.add(bankCode);
                }
            }

            List<Card> filteredCards = new ArrayList<>();
            for (Card card : cards) {
                if ((showIsBlocked && card.isBlocked()) || (showIsUnblocked && !card.isBlocked())) {
                    if (cardsNumbersToShow == null || cardNumberContainsSequence(cardsNumbersToShow, card.getCardNumber())) {
                        if (selectedBanksNumbersSet.isEmpty() || selectedBanksNumbersSet.contains(card.getBankCode())) {
                            filteredCards.add(card);
                        }
                    }
                }
            }
            return filteredCards;
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Could not get cards", e);
        }
    }

    public List<Card> getAllCards() throws ApplicationException {
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
        List<Card> allCards = getAllCards();
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
            List<Card> allCards = getAllCards();
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

    private boolean cardNumberContainsSequence(String cardNumbersToShow, long cardNumber) {
        String cardNumberStr = String.valueOf(cardNumber);
        return cardNumberStr.contains(cardNumbersToShow);
    }

}
