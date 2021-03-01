package com.d.beteasier.match

import android.view.ViewGroup
import com.d.beteasier.api.Category
import com.d.beteasier.api.Match
import com.d.beteasier.base.BaseAdapter
import com.d.beteasier.base.BaseHolder

class MatchesAdapter : BaseAdapter<BaseAdapter.SingleTypeItem<Match>>() {

    override fun <VH : BaseHolder<SingleTypeItem<Match>>> getViewHolder(
        parent: ViewGroup,
        viewType: Enum<*>
    ): VH = MatchViewHolder(parent) as VH

    fun initItems(matches: List<Match>){
        items = matches.map { SingleTypeItem(it) }.toMutableList()
    }

    fun filterItems(matches: List<Match>, category: Category) {
        items = matches.filter { match -> match.category == category }.map { SingleTypeItem(it) }.toMutableList()
    }
}