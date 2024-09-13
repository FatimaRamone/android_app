package com.example.explore

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {

    private val userDao: UserDao = UserDatabase.getDatabase(context).userDao()

    // Insertar un nuevo usuario en la base de datos
    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    // Obtener todos los usuarios desde la base de datos como LiveData
    fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers().asLiveData()
    }
}
