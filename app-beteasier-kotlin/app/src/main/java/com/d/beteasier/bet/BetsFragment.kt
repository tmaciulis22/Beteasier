package com.d.beteasier.bet

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.d.beteasier.R
import com.d.beteasier.base.BaseFragment
import com.d.beteasier.match.MatchInfoActivity
import com.d.beteasier.match.MatchesViewModel
import kotlinx.android.synthetic.main.fragment_bets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BetsFragment : BaseFragment() {

    override val fragmentLayoutRes: Int
        get() = R.layout.fragment_bets

    private val betsViewModel: BetsViewModel by viewModel()
    private val matchesViewModel: MatchesViewModel by viewModel() //TODO REFACTOR REMOVE DEPENDENCY ON THIS VM

    private val adapter: BetsAdapter by lazy {
        BetsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        adapter.initItems(betsViewModel.getBets())
    }

    private fun initAdapter() = adapter.apply {
        recyclerView.adapter = this
        setOnViewClickListener<String> { _, matchId ->
            matchesViewModel.setModelFromMatchId(matchId)
            startActivity(
                Intent(
                    context,
                    MatchInfoActivity::class.java
                ).putExtra("ALLOW_BETS", false)
            )
        }
        initItems(betsViewModel.getBets())
    }
}