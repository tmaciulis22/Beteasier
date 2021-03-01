package com.d.beteasier.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.d.beteasier.MainActivity
import com.d.beteasier.R
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK)
            finish()
    }

    private fun initViews() {
        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            when {
                email.isEmpty() -> {
                    emailInput?.error = "Please enter your email"
                    emailInput?.requestFocus()
                }
                password.isEmpty() -> {
                    passwordInput?.error = "Please enter your password"
                    passwordInput?.requestFocus()
                }
                else -> {
                    if (viewModel.login(email, password)) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
        registerButton.setOnClickListener {
            startActivityForResult(Intent(this, RegisterActivity::class.java), 0)
        }
    }
}