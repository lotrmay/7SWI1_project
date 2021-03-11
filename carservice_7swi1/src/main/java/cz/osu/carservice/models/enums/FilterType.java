package cz.osu.carservice.models.enums;

import cz.osu.carservice.models.utils.TextUtils;

public enum FilterType {
    ID("Číslo objednávky"),
    CUSTOMER_NAME("Jméno zákazníka"),
    CUSTOMER_SURNAME("Příjmení zákazníka"),
    CAR_TYPE("Typ auta"),
    CAR_REGISTRATION_PLATE("SPZ auta"),
    CAR_YEAR_PRODUCTION("Rok výroby");

    private final String type;
    public String getFilterType() { return type; }

    FilterType(String type) {
        if (TextUtils.isTextEmpty(type)) throw new IllegalArgumentException("Parametr type nesmí být null!");
        this.type = type;
    }

}
