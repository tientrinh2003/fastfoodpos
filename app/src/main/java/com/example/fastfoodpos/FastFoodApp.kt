package com.example.fastfoodpos

import SignUpScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fastfoodpos.screen.AddItemScreen
import com.example.fastfoodpos.screen.CartScreen
import com.example.fastfoodpos.screen.CartViewModel
import com.example.fastfoodpos.screen.EditItemScreen
import com.example.fastfoodpos.screen.LoginScreen
import com.example.fastfoodpos.screen.OrderHistoryScreen
import com.example.fastfoodpos.screen.OrderViewModel
import com.example.fastfoodpos.screen.SuccessScreen
import com.example.fastfoodpos.ui.AdminMenuScreen
import com.example.fastfoodpos.ui.UserType
import com.example.fastfoodpos.ui.ViewMenuScreen
import com.example.orderapp.ui.screen.CheckoutScreen

@Composable
fun FastFoodApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Login Screen
        composable("login") {
            LoginScreen(navController = navController, onLoginSuccess = { userType ->
                if (userType == UserType.ADMIN) {
                    navController.navigate("adminMenu")
                } else {
                    navController.navigate("menu")
                }
            })
        }


        // Menu Screen
        composable("menu") {
            MenuScreen(onCartClicked = {
                navController.navigate("cart")
            }, onLogout = {
                navController.navigate("login")
            })
        }

        // Admin Menu Screen
        composable("adminMenu") {
            AdminMenuScreen(
                navController = navController,
                onLogout = {
                    navController.navigate("login")
                }
            )
        }

        // Add Item Screen
        composable("addItemScreen") {
            AddItemScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // View Menu Screen
        composable("viewMenuScreen") {
            ViewMenuScreen(navController = navController)
        }

        // Edit Item Screen
        composable("editItemScreen/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            EditItemScreen(navController = navController, itemId = itemId)
        }

        // Cart Screen
        composable("cart") {
            CartScreen(
                navController = navController,
                onCheckoutClicked = {
                    navController.navigate("checkout")
                }
            )
        }

        // Checkout Screen
        composable("checkout") {
            val cartViewModel: CartViewModel = hiltViewModel()
            val orderViewModel: OrderViewModel = hiltViewModel()
            val cartItems by cartViewModel.cartState.collectAsState(initial = emptyList())

            // Update OrderViewModel with Cart Items
            LaunchedEffect(cartItems) {
                orderViewModel.setCartItems(cartItems)
            }

            CheckoutScreen(cartItems = cartItems, navController = navController) {
                navController.navigate("success")
            }
        }

        // Success Screen
        composable("success") {
            SuccessScreen {
                navController.navigate("menu") {
                    popUpTo("menu") { inclusive = true }
                }
            }
        }

        // Order Screen
        composable("historyOrder") {
            OrderHistoryScreen(navController = navController)
        }

        composable("signup") {
            SignUpScreen(
                navController = navController
            )
        }
    }
}