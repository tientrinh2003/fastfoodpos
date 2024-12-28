package com.example.fastfoodpos

import LoginScreen
import MenuScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fastfoodpos.screen.CartScreen
import com.example.fastfoodpos.screen.CartViewModel
import com.example.fastfoodpos.screen.SuccessScreen
import com.example.orderapp.ui.screen.CheckoutScreen

@Composable
fun FastFoodApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen { navController.navigate("menu") } }
        composable("menu") { MenuScreen() }
        composable("cart") { CartScreen { navController.navigate("checkout") } }
        composable("checkout") {
            val viewModel: CartViewModel = hiltViewModel()
            val cartItems by viewModel.cartState.observeAsState(emptyList())
            CheckoutScreen(cartItems = cartItems) { navController.navigate("success") }
        }
        composable("success") { SuccessScreen { navController.navigate("menu") } }
    }
}