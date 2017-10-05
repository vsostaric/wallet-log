package com.example.vladimirsostaric.walletlog.model;

import java.math.BigDecimal;

public class Expense {

    private BigDecimal amount;

    private ExpenseType type;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }
}
