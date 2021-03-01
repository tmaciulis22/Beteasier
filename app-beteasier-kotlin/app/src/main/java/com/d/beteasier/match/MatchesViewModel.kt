package com.d.beteasier.match

import androidx.lifecycle.ViewModel
import com.d.beteasier.api.BackEndMock
import com.d.beteasier.api.Bet
import com.d.beteasier.api.Match
import com.d.beteasier.api.User

class MatchesViewModel : ViewModel() {

    private var matchList: List<Match> = listOf()
    var user: User? = null
    lateinit var model: Match

    fun getMatches(): List<Match> {
        if (matchList.isEmpty())
            matchList = BackEndMock.getMatches()
        return matchList
    }

    fun getBetByMatchId(id: String?) = BackEndMock.getBetByMatchId(id ?: "")

    fun getCurrentUser(): User? {
        if (user == null)
            user = BackEndMock.getCurrentUser()
        return user
    }

    fun addBet(userId: String, matchId: String, team: String, amount: Double) = BackEndMock.addBet(
        Bet(
            userId,
            matchId,
            team,
            amount
        )
    )

    fun setModelFromMatchId(id: String) {
        model = BackEndMock.getMatchById(id)
    }
}