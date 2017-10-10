package com.example.vladimirsostaric.walletlog.model;

import java.math.BigDecimal;
import java.util.Date;

public class Expense {

    private BigDecimal amount;

    private ExpenseType type;

    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
