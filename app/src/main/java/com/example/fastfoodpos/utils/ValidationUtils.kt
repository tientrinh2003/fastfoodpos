package com.example.fastfoodpos.utils

fun validateEmail(email: String): String? {
    if (email.isBlank()) {
        return "Email cannot be empty"
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return "Invalid email format"
    }
    return null
}

fun validatePassword(password: String): String? {
    if (password.isBlank()) {
        return "Password cannot be empty"
    }
    if (password.length < 6) {
        return "Password must be at least 6 characters"
    }
    return null
}

fun validateName(name: String): String? {
    if (name.isBlank()) {
        return "Name cannot be empty"
    }
    return null
}
