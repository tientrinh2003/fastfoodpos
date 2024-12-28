package com.example.fastfoodpos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FastFoodPOSTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = Typography, // Use the custom Typography
        content = content
    )
}
