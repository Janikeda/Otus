package ru.otus.homework.dto;

public class BalanceAmount {

    private final long amount;

    public BalanceAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }
}
