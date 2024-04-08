package com.oleg.credit_cards.dto;

public class IncreaseRequestParams {
    private long cardNumber;
    private int requestedCreditLimit;
    private String occupation;
    private int averageMonthlyIncome;

    public IncreaseRequestParams() {
    }

    public IncreaseRequestParams(long cardNumber, int requestedCreditLimit, String occupation, int averageMonthlyIncome) {
        this.cardNumber = cardNumber;
        this.requestedCreditLimit = requestedCreditLimit;
        this.occupation = occupation;
        this.averageMonthlyIncome = averageMonthlyIncome;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getRequestedCreditLimit() {
        return requestedCreditLimit;
    }

    public void setRequestedCreditLimit(int requestedCreditLimit) {
        this.requestedCreditLimit = requestedCreditLimit;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAverageMonthlyIncome() {
        return averageMonthlyIncome;
    }

    public void setAverageMonthlyIncome(int averageMonthlyIncome) {
        this.averageMonthlyIncome = averageMonthlyIncome;
    }

    @Override
    public String toString() {
        return "IncreaseRequestParams{" +
                "cardNumber=" + cardNumber +
                ", requestedCreditLimit=" + requestedCreditLimit +
                ", occupation='" + occupation + '\'' +
                ", averageMonthlyIncome=" + averageMonthlyIncome +
                '}';
    }
}
