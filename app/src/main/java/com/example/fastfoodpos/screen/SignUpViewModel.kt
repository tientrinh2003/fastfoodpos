package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.User
import com.example.fastfoodpos.domain.repository.UserRepository
import com.example.fastfoodpos.ui.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    sealed class SignUpResult {
        data class Success(val user: User) : SignUpResult()
        data class Error(val message: String) : SignUpResult()
    }

    fun signUp(name: String, account: String, password: String, role: String): SignUpResult {
        var result: SignUpResult = SignUpResult.Error("Unknown error")
        viewModelScope.launch {
            try {
                val user = User(name = name, account = account, password = password, role = role)
                userRepository.insertUser(user)
                val inputRole : UserType = UserType.valueOf(role)
                val loggedInUser = userRepository.login(account, password, inputRole)
                if (loggedInUser != null) {
                    result = SignUpResult.Error("User existed")
                } else {
                    userRepository.insertUser(user)
                    result = SignUpResult.Success(user)
                }
            } catch (e: Exception) {
                result = SignUpResult.Error(e.message ?: "Sign up failed")
            }
        }
        return result
    }
}