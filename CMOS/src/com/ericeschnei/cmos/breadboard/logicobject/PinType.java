package com.ericeschnei.cmos.breadboard.logicobject;

public enum PinType {
    INPUT(0), OUTPUT(1), SWITCH(2);

    private final int pin;

    PinType(int pin) {
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }
}