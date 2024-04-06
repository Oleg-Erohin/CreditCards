package com.oleg.credit_cards.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Card {
    private long cardNumber;
    private Date dateOfIssue;
    private String imagePath;
    private boolean isBlocked;
    private boolean isDigital;
    private int creditLimit;
    private int bankCode;

    public Card() {
    }

    public Card(long cardNumber, Date dateOfIssue, String imagePath, boolean isBlocked, boolean isDigital, int creditLimit, int bankCode) {
        this.cardNumber = cardNumber;
        this.dateOfIssue = dateOfIssue;
        this.imagePath = imagePath;
        this.isBlocked = isBlocked;
        this.isDigital = isDigital;
        this.creditLimit = creditLimit;
        this.bankCode = bankCode;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    @JsonProperty("isBlocked")
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isDigital() {
        return isDigital;
    }

    @JsonProperty("isDigital")

    public void setDigital(boolean digital) {
        isDigital = digital;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber=" + cardNumber +
                ", dateOfIssue=" + dateOfIssue +
                ", imagePath='" + imagePath + '\'' +
                ", isBlocked=" + isBlocked +
                ", isDigital=" + isDigital +
                ", creditLimit=" + creditLimit +
                ", bankCode=" + bankCode +
                '}';
    }
}
