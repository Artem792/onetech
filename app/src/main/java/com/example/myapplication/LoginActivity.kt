package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView
    private lateinit var loginInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText

    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userManager = UserManager(this)
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)
        loginInput = findViewById(R.id.loginInput)
        passwordInput = findViewById(R.id.passwordInput)
    }

    private fun setupClickListeners() {
        loginButton.setOnClickListener {
            val login = loginInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // Валидация полей
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Анимация нажатия кнопки
            loginButton.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(150)
                .withEndAction {
                    // Попытка авторизации
                    val success = userManager.loginUser(login, password)

                    loginButton.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .start()

                    if (success) {
                        Toast.makeText(this, "Успешный вход!", Toast.LENGTH_SHORT).show()
                        goToMainScreen()
                    } else {
                        Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                    }
                }
                .start()
        }

        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToMainScreen() {
        val intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
        finish()
    }
}