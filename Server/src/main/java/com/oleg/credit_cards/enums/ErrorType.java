package com.oleg.credit_cards.enums;

public enum ErrorType {
    GENERAL_ERROR(101, "An error has occurred while running", true),
    REQUESTED_LIMIT_TOO_HIGH(102, "The requested credit limit is higher than the allowed limit", false),
    REQUEST_DECLINED(103, "Your request has been declined. Please read the conditions again", false),
    CARD_BLOCKED(104, "The card is blocked", false),
    INCOME_TOO_LOW(105, "Operation cannot be performed due to low income limits", false),
    CARD_TOO_NEW(106, "Card cannot be updated as the issue date is too near", false),
    USER_NOT_FOUND(107, "The user you were trying to login to was not found", false),
    LOGIN_FAILED(108, "Provided incorrect username or password", false);

    private int errorNum;
    private String errorMessage;
    private boolean isShowStackTrace;

    ErrorType(int errorNum, String errorMessage, boolean isShowStackTrace) {
        this.errorNum = errorNum;
        this.errorMessage = errorMessage;
        this.isShowStackTrace = isShowStackTrace;
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

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

    public void setShowStackTrace(boolean showStackTrace) {
        isShowStackTrace = showStackTrace;
    }
}
