package com.example.tdd.ktmoney

class Dollar(
    override val amount: Int,
): Money(amount) {

    override fun times(multiplier: Int): Money {
        return Dollar(amount * multiplier)
    }
}