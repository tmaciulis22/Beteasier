package com.d.beteasier.match

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.d.beteasier.base.BaseFragment
import com.d.beteasier.R
import com.d.beteasier.api.Category
import com.d.beteasier.api.Match
import kotlinx.android.synthetic.main.fragment_matches.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchesFragment : BaseFragment(), AdapterView.OnItemSelectedListener {

    override val fragmentLayoutRes: Int
        get() = R.layout.fragment_matches

    private val viewModel: MatchesViewModel by viewModel()

    private val adapter: MatchesAdapter by lazy {
        MatchesAdapter()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) =
        adapter.initItems(viewModel.getMatches())

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
        when (position) {
            0 -> adapter.initItems(viewModel.getMatches())
            else -> adapter.filterItems(viewModel.getMatches(), Category.values()[position-1])
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSpinner()
    }

    override fun onResume() {
        super.onResume()
        adapter.initItems(viewModel.getMatches())
    }

    private fun initAdapter() =
        adapter.apply {
            recyclerView.adapter = this
            setOnViewClickListener<Match> { _, match ->
                viewModel.model = match
                startActivity(Intent(context, MatchInfoActivity::class.java))
            }
            initItems(viewModel.getMatches())
        }

    private fun initSpinner() =
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
            categorySpinner.onItemSelectedListener = this@MatchesFragment
        }
}