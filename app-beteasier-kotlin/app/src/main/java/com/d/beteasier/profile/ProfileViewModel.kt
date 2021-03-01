package com.d.beteasier.profile

import androidx.lifecycle.ViewModel
import com.d.beteasier.api.BackEndMock
import com.d.beteasier.api.User

class ProfileViewModel : ViewModel() {

    private var model: User? = null

    fun isPasswordValid(password: String) =
        password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"))

    fun register(email: String, password: String) = BackEndMock.register(email, password)

    fun login(email: String, password: String) = BackEndMock.login(email, password)

    fun getCurrentUser(): User? {
        if (model == null)
            model = BackEndMock.getCurrentUser()
        return model
    }

    fun updateProfile(
        name: String,
        surname: String,
        country: String,
        monthlyLimit: String,
        betLimit: String
    ) {
        BackEndMock.updateUser(
            name,
            surname,
            country,
            monthlyLimit,
            betLimit
        )
        model = BackEndMock.getCurrentUser()
    }

    fun addFunds(amount: Double) = BackEndMock.addFunds(amount)

    fun deleteCurrentUser() = BackEndMock.deleteCurrentUser()
}