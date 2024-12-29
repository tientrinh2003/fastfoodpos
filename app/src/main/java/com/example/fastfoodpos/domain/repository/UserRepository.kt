package com.example.fastfoodpos.domain.repository

import com.example.fastfoodpos.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun getUserByAccount(account: String): User?
    suspend fun getUserById(id: Int): User?
    suspend fun updateUser(id: Int, name: String, account: String, password: String, role: String)
    suspend fun login(account: String, password: String): User?
}