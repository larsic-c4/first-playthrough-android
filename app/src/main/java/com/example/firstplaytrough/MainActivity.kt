package com.example.firstplaytrough

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.firstplaytrough.model.Account
import com.example.firstplaytrough.model.AccountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var accountRepository: AccountRepository
    private var defaultColor: Int = Color.BLACK
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_menu)

        accountRepository = AccountRepository(MyApp.database.accountDao())
        val label = findViewById<TextView>(R.id.loginLabel)
        defaultColor = label.currentTextColor

        setSignInButtonListener()
        setRegisterButtonListener()
    }

    protected fun setSignInButtonListener() {
        val btLogin = findViewById<Button>(R.id.btLogin)
        btLogin.setOnClickListener {
            val usernameEditText = findViewById<EditText>(R.id.signInUsername)
            val passwordEditText = findViewById<EditText>(R.id.signInPassword)

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            
            GlobalScope.launch {
                val isAuthenticated = accountRepository.authenticateAccount(username, password)

                if (isAuthenticated) {
                    navigateToMainMenu()
                } else {
                    // Show error message for invalid credentials
                    val label = findViewById<TextView>(R.id.loginLabel)
                    val textColor = ContextCompat.getColor(this@MainActivity, R.color.red)
                    label.setTextColor(textColor)
                    label.text = "Sign in unsuccessful!"
                }
            }
        }
    }

    protected fun setRegisterButtonListener() {
        val btRegister = findViewById<Button>(R.id.btRegister)
        btRegister.setOnClickListener {
            val usernameEditText = findViewById<EditText>(R.id.signInUsername)
            val passwordEditText = findViewById<EditText>(R.id.signInPassword)

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val label = findViewById<TextView>(R.id.loginLabel)

            if (username.isBlank() || password.isBlank()
                || username.length < 4 || username.length > 16
                || password.length < 4 || password.length > 16 ) {
                label.text = "Enter username and password to register\n4 to 16 characters long."
                return@setOnClickListener
            }

            GlobalScope.launch {
                val account = accountRepository.findAccountByUsername(username)
                if (account != null) {
                    val textColor = ContextCompat.getColor(this@MainActivity, R.color.red)
                    label.setTextColor(textColor)
                    label.text = "The username is taken!"
                } else {
                    val account = Account(username = username, passwordHash = accountRepository.calculateSHA256Hash(password))
                    val accountId = accountRepository.insertAccount(account)
                    account.id = accountId
                    label.setTextColor(defaultColor)
                    label.text = "Account is registered!"
                }
            }
        }

    }

    private fun navigateToMainMenu() {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}