package com.d.beteasier.bet

import androidx.lifecycle.ViewModel
import com.d.beteasier.api.BackEndMock

class BetsViewModel : ViewModel() {

    fun getBets() = BackEndMock.getBets()
}