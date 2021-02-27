package com.nikhilcodes.expzen.shared.enums;


public enum ExpenseType {
    TECH("TECH"),
    EVERYDAY("EVERYDAY"),
    FOOD("FOOD"),
    UTILITIES("UTILITIES");

    public final String label;

    ExpenseType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
