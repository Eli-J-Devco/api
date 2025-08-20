package com.nwm.api.entities;

public enum LogOperationEnum {
	DEFAULT(0),
	INSERT(1),
	UPDATE(2),
	DELETE(3);

	private final int value;

	LogOperationEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
        return this.value;
    }
	
	public static LogOperationEnum fromValue(int value) {
        for (LogOperationEnum granularity : LogOperationEnum.values()) {
            if (granularity.getValue() == value) {
                return granularity;
            }
        }
        
        return LogOperationEnum.DEFAULT;
    }
}
