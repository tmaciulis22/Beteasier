package com.d.beteasier.bet

import android.view.ViewGroup
import com.d.beteasier.api.Bet
import com.d.beteasier.base.BaseAdapter
import com.d.beteasier.base.BaseHolder

class BetsAdapter : BaseAdapter<BaseAdapter.SingleTypeItem<Bet>>() {

    override fun <VH : BaseHolder<SingleTypeItem<Bet>>> getViewHolder(
        parent: ViewGroup,
        viewType: Enum<*>
    ): VH = BetViewHolder(parent) as VH

    fun initItems(bets: List<Bet>){
        items = bets.map { SingleTypeItem(it) }.toMutableList()
    }
}