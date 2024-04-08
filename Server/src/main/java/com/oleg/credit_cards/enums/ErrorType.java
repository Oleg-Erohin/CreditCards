package com.oleg.credit_cards.enums;

public enum ErrorType {
    GENERAL_ERROR(801, "An error has occurred while running", true),
    REQUESTED_LIMIT_TOO_HIGH(802, "The requested credit limit is higher than the allowed limit", false),
    REQUEST_DECLINED(803, "Your request has been declined. Please read the conditions again", false),
    CARD_BLOCKED(804, "The card is blocked", false),
    INCOME_TOO_LOW(805, "Operation cannot be performed due to low income limits", false),
    CARD_TOO_NEW(806, "Card cannot be updated as the issue date is too near", false),
    CURRENT_CREDIT_LIMIT_HIGHER(807, "Your current credit limit is higher than the requested credit limit", false);

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
