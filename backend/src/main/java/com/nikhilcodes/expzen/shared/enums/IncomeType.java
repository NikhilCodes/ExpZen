package com.nikhilcodes.expzen.shared.enums;

public enum IncomeType {
    PAYCHECK("PAYCHECK"),
    REFUND("REFUND"),
    INTEREST("INTEREST"),
    BONUS("BONUS");

    public final String label;

    IncomeType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
