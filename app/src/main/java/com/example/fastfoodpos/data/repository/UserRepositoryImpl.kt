package com.example.fastfoodpos.data.repository

import com.example.fastfoodpos.data.room.DAO.UserDao
import com.example.fastfoodpos.data.room.Entity.UserEntity
import com.example.fastfoodpos.domain.model.User
import com.example.fastfoodpos.domain.repository.UserRepository
import com.example.fastfoodpos.ui.UserType
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toUserEntity())
    }

    override suspend fun getUserByAccount(account: String): User? {
        return userDao.getUserByAccount(account)?.toUser()
    }

    override suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)?.toUser()
    }

    override suspend fun updateUser(id: Int, name: String, account: String, password: String, role: String) {
        userDao.updateUser(id, name, account, password, role)
    }

    override suspend fun login(account: String, password: String, userType: UserType): User? {
        return userDao.login(account, password, userType)?.toUser()
    }
    private fun User.toUserEntity(): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            account = account,
            password = password,
            role = role
        )
    }
    private fun UserEntity.toUser(): User {
        return User(
            id = id,
            name = name,
            account = account,
            password = password,
            role = role
        )
    }
}