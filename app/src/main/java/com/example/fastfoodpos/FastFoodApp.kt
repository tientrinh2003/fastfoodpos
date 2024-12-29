package com.example.fastfoodpos

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fastfoodpos.screen.AddItemScreen
import com.example.fastfoodpos.screen.CartScreen
import com.example.fastfoodpos.screen.CartViewModel
import com.example.fastfoodpos.screen.OrderScreen
import com.example.fastfoodpos.screen.OrderViewModel
import com.example.fastfoodpos.screen.SuccessScreen
import com.example.fastfoodpos.ui.AdminMenuScreen
import com.example.fastfoodpos.ui.EditItemScreen
import com.example.fastfoodpos.ui.UserType
import com.example.fastfoodpos.ui.ViewMenuScreen
import com.example.orderapp.ui.screen.CheckoutScreen

@Composable
fun FastFoodApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = { userType ->
                if (userType == UserType.ADMIN) {
                    navController.navigate("adminMenu")
                } else {
                    navController.navigate("menu")
                }
            })
        }
        composable("menu") {
            MenuScreen(onCartClicked = {
                navController.navigate("cart")
            })
        }
        composable("adminMenu") {
            AdminMenuScreen(navController = navController, onLogout = {
                navController.navigate("login")
            })
        }
        composable("addItemScreen") {
            AddItemScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("viewMenuScreen") {
            ViewMenuScreen(navController = navController)
        }
        composable("editItemScreen/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            EditItemScreen(navController = navController, itemId = itemId)
        }
        composable("cart") { CartScreen { navController.navigate("checkout") } }
        composable("checkout") {
            val cartViewModel: CartViewModel = hiltViewModel()
            val orderViewModel: OrderViewModel = hiltViewModel()
            val cartItems by cartViewModel.cartState.collectAsState(initial = emptyList())
            orderViewModel.setCartItems(cartItems)
            CheckoutScreen(cartItems = cartItems) { navController.navigate("success") }
        }
        composable("success") { SuccessScreen { navController.navigate("menu") } }
        composable("order") {
            val orderViewModel: OrderViewModel = hiltViewModel()
            OrderScreen(orderViewModel) {
                navController.navigate("success")
            }
        }
    }
}