package com.example.tdd.ktmoney

interface Expression {
    fun reduce(to: String): Money
}