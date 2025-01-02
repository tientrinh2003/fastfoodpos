package com.example.fastfoodpos.data.room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fastfoodpos.data.room.Entity.UserEntity
import com.example.fastfoodpos.ui.UserType

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE account = :account")
    suspend fun getUserByAccount(account: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("UPDATE users SET name = :name, account = :account, password = :password, role = :role WHERE id = :id")
    suspend fun updateUser(id: Int, name: String, account: String, password: String, role: String)

    @Query("SELECT * FROM users WHERE account = :account AND password = :password AND role = :userType")
    suspend fun login(account: String, password: String, userType: UserType): UserEntity?
}