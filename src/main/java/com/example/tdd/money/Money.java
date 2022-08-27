package com.example.tdd.money;

abstract class Money {

    protected int amount;

    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount
                && getClass().equals(money.getClass());
    }

    static Dollar dollar(int amount) {
        return new Dollar(amount, "USD");
    }

    static Franc franc(int amount) {
        return new Franc(amount, "CHF");
    }

    abstract Money times(int mutiplier);

    protected String currency() {
        return currency;
    }
}
