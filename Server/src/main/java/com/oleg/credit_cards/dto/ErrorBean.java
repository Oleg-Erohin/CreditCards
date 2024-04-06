package com.oleg.credit_cards.dto;

import com.oleg.credit_cards.enums.ErrorType;

public class ErrorBean {

    private int errorNum;
    private String errorMessage;

    public ErrorBean() {
    }

    public ErrorBean(int errorNum, String errorMessage) {
        this.errorNum = errorNum;
        this.errorMessage = errorMessage;
    }

    public ErrorBean(ErrorType errorType) {
        this.errorNum = errorType.getErrorNum();
        this.errorMessage = errorType.getErrorMessage();
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
