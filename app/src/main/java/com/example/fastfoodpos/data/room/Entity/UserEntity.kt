package com.example.fastfoodpos.data.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val account : String,
    val password: String,
    val role: String
)
