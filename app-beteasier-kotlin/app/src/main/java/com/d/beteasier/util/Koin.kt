package com.d.beteasier.util

import com.d.beteasier.bet.BetsViewModel
import com.d.beteasier.match.MatchesViewModel
import com.d.beteasier.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Koin {

    val appModule = module {
        single { MatchesViewModel() }
        viewModel { ProfileViewModel() }
        viewModel { BetsViewModel() }
    }
}