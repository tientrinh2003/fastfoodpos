package com.example.fastfoodpos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fastfoodpos.screen.CheckoutScreen
import com.example.fastfoodpos.screen.CustomerScreen
import com.example.fastfoodpos.screen.MenuScreen
import com.example.fastfoodpos.screen.OrderScreen

class FastFoodApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastFoodPOSApp()
        }
    }
}

@Composable
fun FastFoodPOSApp() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") { MenuScreen() }
            composable("order") { OrderScreen() }
            composable("checkout") { CheckoutScreen(totalAmount = 100.0) }
            composable("customer") { CustomerScreen() }
        }
    }
}
