package com.d.beteasier.match

import android.view.ViewGroup
import com.d.beteasier.R
import com.d.beteasier.api.Match
import com.d.beteasier.base.BaseAdapter
import com.d.beteasier.base.BaseHolder
import kotlinx.android.synthetic.main.holder_match.*

class MatchViewHolder(parent: ViewGroup) : BaseHolder<BaseAdapter.SingleTypeItem<Match>>(
    parent,
    R.layout.holder_match
) {

    override fun onBind(listItem: BaseAdapter.SingleTypeItem<Match>) {
        listItem.item.apply {
            imageView.setImageResource(category.imageRes)
            firstTeamView.text = firstTeam
            secondTeamView.text = secondTeam
            dateTimeView.text = dateTime
        }
        openButton.setOnClickListener {
            onViewClick(it, listItem.item)
        }
    }
}