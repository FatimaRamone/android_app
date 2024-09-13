package com.example.explore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao = UserDatabase.getDatabase(application).userDao()

    fun insertUser(user: User) = viewModelScope.launch {
        userDao.insertUser(user)
    }

    fun getUserByEmail(email: String): Flow<User?> = userDao.findUserByEmail(email)
    fun getUserByUsername(username: String): Flow<User?> = userDao.findUserByUsername(username)

    // Obtener todos los usuarios
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
}
