package com.phakocy.jwtsendotp.entity;

import lombok.Getter;

@Getter
public enum Role {
    USER("User"),
    ADMIN("Admin");
    private final String displayValue;

    Role(String displayValue) {
        this.displayValue = displayValue;
    }
}
