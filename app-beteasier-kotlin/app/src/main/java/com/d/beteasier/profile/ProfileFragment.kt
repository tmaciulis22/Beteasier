package com.d.beteasier.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.d.beteasier.R
import com.d.beteasier.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    override val fragmentLayoutRes: Int
        get() = R.layout.fragment_profile

    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()
        initViews()
    }

    private fun loadUserData() = viewModel.getCurrentUser()?.apply {
        emailView.setText(email)
        balanceView.setText(balance?.let { "${it} EUR" } ?: "0.00 EUR")
        nameEditView.setText(name)
        surnameEditView.setText(surname)
        countryEditView.setText(country)
        monthlyLimitEditView.setText(monthlyLimit?.toString() ?: "")
        betLimitEditView.setText(betLimit?.toString() ?: "")
    }

    private fun initViews() {
        buttonUpdateInfo.setOnClickListener {
            val name = nameEditView.text.toString()
            val surname = surnameEditView.text.toString()
            val country = countryEditView.text.toString()
            val monthlyLimit = monthlyLimitEditView.text.toString()
            val betLimit = betLimitEditView.text.toString()

            viewModel.updateProfile(name, surname, country, monthlyLimit, betLimit)

            loadUserData()
            Toast.makeText(context, "Profile has been updated", Toast.LENGTH_SHORT).show()
        }
        addFundsButton.setOnClickListener {
            viewModel.addFunds(100.0)
            Toast.makeText(context, "Added 100 EUR from your PayPal account", Toast.LENGTH_SHORT)
                .show()
            balanceView.setText("${viewModel.getCurrentUser()?.balance.toString()} EUR")
        }
        deleteAccountButton.setOnClickListener {
            viewModel.deleteCurrentUser()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}