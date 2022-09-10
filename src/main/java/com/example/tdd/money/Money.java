package com.example.tdd.money;

class Money implements Expression {

    protected int amount;

    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount
                && currency().equals(money.currency());
    }

    static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    Money times(int mutiplier) {
        return new Money(amount * mutiplier, currency);
    }

    public Money reduce(String to) {
        return this;
    }

    Expression plus(Money addend) {
        return new Sum(this, addend);
    }

    protected String currency() {
        return currency;
    }

    @Override
    public String toString() {
        return amount + " " + currency ;
    }
}
