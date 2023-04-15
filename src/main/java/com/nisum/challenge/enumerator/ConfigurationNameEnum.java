package com.nisum.challenge.enumerator;

public enum ConfigurationNameEnum {
    EMAIL_VALIDATION("EMAIL_VALIDATION"),
    PASSWORD_VALIDATION("PASSWORD_VALIDATION");

    private final String value;

    ConfigurationNameEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
