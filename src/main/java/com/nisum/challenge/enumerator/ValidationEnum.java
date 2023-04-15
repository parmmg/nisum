package com.nisum.challenge.enumerator;

public enum ValidationEnum {
    EMAIL_VALIDATION("EMAIL_VALIDATION"),
    PASSWORD_VALIDATION("PASSWORD_VALIDATION"),

    TEST_VALIDATION("TEST_VALIDATION");
    private final String value;

    ValidationEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
