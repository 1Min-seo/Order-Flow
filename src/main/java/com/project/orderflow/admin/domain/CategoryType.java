package com.project.orderflow.admin.domain;

public enum CategoryType {
    BEST("BEST"),
    SIGNATURE("SIGNATURE"),
    MAIN("MAIN"),
    SIDE("SIDE"),
    DRINK("DRINK"),;


    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
