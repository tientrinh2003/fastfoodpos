package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import com.example.fastfoodpos.domain.model.User
import com.example.fastfoodpos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun login(account: String, password: String): User? {
        return userRepository.login(account, password)
    }
}