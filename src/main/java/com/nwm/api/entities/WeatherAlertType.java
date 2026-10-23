package com.nwm.api.entities;

public enum WeatherAlertType {
    HIGH_WINDS("High Winds"),
    FLOOD_WARNING("Flood Warning"),
    EXPECTED_STORM("Expected Storm");

    private final String displayName;

    WeatherAlertType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
