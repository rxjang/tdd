package com.example.tdd.ktmoney

class Bank {

    fun reduce(source: Expression, to: String): Money {
        return source.reduce(to)
    }
}