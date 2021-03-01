package com.d.beteasier.api

data class Bet(
    val userId: String,
    val matchId: String,
    val team: String,
    val amount: Double,
    val result: Result = Result.TBD
)

enum class Result(val text: String) {
    TBD("TBD:"),
    Won("Won:"),
    Lost("Lost:");
}