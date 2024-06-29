package com.example.tdd.ktmoney

class Dollar(
    var amount: Int,
) {

    fun times(multiplier: Int) {
        this.amount *= multiplier
    }
}