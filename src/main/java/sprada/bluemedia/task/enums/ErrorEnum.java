package sprada.bluemedia.task.enums;

public enum ErrorEnum {
    EMPTY_REQUEST("Provided request should not be empty"),
    EMPTY_INVESTMENT_TYPE("Provided investment type should not be empty"),
    EMPTY_AMOUNT("Provided amount should not be empty"),
    NEGATIVE_AMOUNT("Provided amount should be greater than 0 "),
    EMPTY_FUNDS("Provided list of funds should not be empty"),
    EMPTY_ID("Provided id for fund should not be empty"),
    EMPTY_NAME("Provided name for fund should not be empty"),
    EMPTY_FUND_KIND("Provided kind of fund should not be empty");

    private final String value;

    public String getValue() {
        return value;
    }

    ErrorEnum(String s) {
        this.value = s;
    }
}
