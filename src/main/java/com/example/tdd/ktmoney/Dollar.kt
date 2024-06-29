package com.example.tdd.ktmoney

class Dollar(
    var amount: Int,
) {

    fun times(multiplier: Int): Dollar {
        return Dollar(amount * multiplier)
    }
}