package com.d.beteasier.api

data class User(
    val id: String,
    val email: String,
    val password: String,
    var name: String? = null,
    var surname: String? = null,
    var country: String? = null,
    var balance: Double? = null,
    var monthlyLimit: Double? = null,
    var betLimit: Double? = null,
    val bets: MutableList<Bet> = mutableListOf()
)