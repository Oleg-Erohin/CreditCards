package com.oleg.credit_cards.dto;


public class SuccessfulLoginDetails {
    private String userName;

    public SuccessfulLoginDetails(String userName) {
        this.userName = userName;
    }

    public SuccessfulLoginDetails() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "SuccessfulLoginDetails{" +
                "userName=" + userName +
                '}';
    }
}
