package com.example.tdd.ktmoney

class Dollar(
    override val amount: Int,
): Money(amount) {

    fun times(multiplier: Int): Dollar {
        return Dollar(amount * multiplier)
    }
}