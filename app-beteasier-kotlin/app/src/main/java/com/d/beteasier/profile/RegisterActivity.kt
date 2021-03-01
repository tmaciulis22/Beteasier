package com.d.beteasier.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.d.beteasier.MainActivity
import com.d.beteasier.R
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initViews()
    }
    
    private fun initViews() {
        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val repeatPassword = passwordRepeatInput.text.toString()

            when {
                email.isEmpty() -> {
                    emailInput.error = "Please enter your email"
                    emailInput.requestFocus()
                }
                password.isEmpty() -> {
                    passwordInput.error = "Please enter your password"
                    passwordInput.requestFocus()
                }
                repeatPassword.isEmpty() -> {
                    passwordRepeatInput.error = "Please repeat your password"
                    passwordRepeatInput.requestFocus()
                }
                password != repeatPassword ->
                    Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()
                !viewModel.isPasswordValid(password) -> {
                    passwordInput.error = "Password has to be: minimum eight characters, at least one uppercase letter, one lowercase letter and one number"
                    passwordInput.requestFocus()
                }
                else -> {
                    if (viewModel.register(email, password)) {
                        startActivity(Intent(this, MainActivity::class.java))
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else
                        Toast.makeText(this, "User exists with this email", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}