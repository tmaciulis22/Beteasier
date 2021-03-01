package com.d.beteasier.bet

import android.view.ViewGroup
import com.d.beteasier.R
import com.d.beteasier.api.BackEndMock
import com.d.beteasier.api.Bet
import com.d.beteasier.api.Result
import com.d.beteasier.base.BaseAdapter
import com.d.beteasier.base.BaseHolder
import kotlinx.android.synthetic.main.holder_bet.*

class BetViewHolder(parent: ViewGroup) : BaseHolder<BaseAdapter.SingleTypeItem<Bet>>(
    parent,
    R.layout.holder_bet
) {

    override fun onBind(listItem: BaseAdapter.SingleTypeItem<Bet>) {
        val bet = listItem.item
        val match = BackEndMock.getMatchById(bet.matchId) //TODO REFACTOR do not fetch from BE here
        imageView.setImageResource(match.category.imageRes)
        firstTeamView.text = match.firstTeam
        secondTeamView.text = match.secondTeam
        dateTimeView.text = match.dateTime
        resultView.text = bet.result.text
        amountView.text = when(bet.result) {
            Result.Won -> {
                val rate = if (bet.team == match.firstTeam)
                    match.firstRate
                else
                    match.secondRate
                String.format("%.2f EUR", bet.amount * rate)
            }
            else -> "${bet.amount.toString()} EUR"
        }
        openButton.setOnClickListener {
            onViewClick(it, bet.matchId)
        }
    }
}