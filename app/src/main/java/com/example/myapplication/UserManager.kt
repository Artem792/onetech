package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class UserManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERS = "users"
        private const val KEY_CURRENT_USER = "current_user"
        private const val SEPARATOR = "|||"
    }

    // Регистрация нового пользователя
    fun registerUser(login: String, email: String, password: String): Boolean {
        val users = getUsers()

        // Проверяем, существует ли пользователь с таким логином или email
        if (users.any { it.login == login || it.email == email }) {
            return false // Пользователь уже существует
        }

        val newUser = User(login, email, password)
        users.add(newUser)
        saveUsers(users)
        return true
    }

    // Авторизация пользователя
    fun loginUser(login: String, password: String): Boolean {
        val users = getUsers()
        val user = users.find { it.login == login && it.password == password }

        if (user != null) {
            // Сохраняем текущего пользователя
            saveCurrentUser(user)
            return true
        }
        return false
    }

    // Получение списка всех пользователей
    private fun getUsers(): MutableList<User> {
        val usersString = sharedPreferences.getString(KEY_USERS, "") ?: ""
        if (usersString.isEmpty()) return mutableListOf()

        return usersString.split(";").mapNotNull { userString ->
            if (userString.isNotEmpty()) {
                val parts = userString.split(SEPARATOR)
                if (parts.size == 3) {
                    User(parts[0], parts[1], parts[2])
                } else {
                    null
                }
            } else {
                null
            }
        }.toMutableList()
    }

    // Сохранение списка пользователей
    private fun saveUsers(users: List<User>) {
        val usersString = users.joinToString(";") {
            "${it.login}$SEPARATOR${it.email}$SEPARATOR${it.password}"
        }
        sharedPreferences.edit().putString(KEY_USERS, usersString).apply()
    }

    // Сохранение текущего пользователя
    private fun saveCurrentUser(user: User) {
        val userString = "${user.login}$SEPARATOR${user.email}$SEPARATOR${user.password}"
        sharedPreferences.edit().putString(KEY_CURRENT_USER, userString).apply()
    }

    // Получение текущего пользователя
    fun getCurrentUser(): User? {
        val userString = sharedPreferences.getString(KEY_CURRENT_USER, null) ?: return null
        val parts = userString.split(SEPARATOR)
        return if (parts.size == 3) {
            User(parts[0], parts[1], parts[2])
        } else {
            null
        }
    }

    // Выход из системы
    fun logout() {
        sharedPreferences.edit().remove(KEY_CURRENT_USER).apply()
    }

    // Проверка, авторизован ли пользователь
    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }
}