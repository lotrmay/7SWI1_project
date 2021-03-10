package cz.osu.carservice.models.enums;

public enum FilterType {
    ID("Číslo objednávky"),
    CUSTOMER_NAME("Jméno zákazníka"),
    CUSTOMER_SURNAME("Příjmení zákazníka"),
    CAR_TYPE("Typ auta"),
    CAR_REGISTRATION_PLATE("SPZ auta"),
    CAR_YEAR_PRODUCTION("Rok výroby"),
    STATE("Stát"),
    CITY("Město");

    private String type;
    public String getFilterType() { return type; }

    FilterType(String s) {
        this.type = s;
    }

}
