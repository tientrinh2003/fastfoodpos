package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.CartItem
import com.example.fastfoodpos.domain.model.Order
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val fastFoodRepository: FastFoodRepository) :
    ViewModel() {
    private val _orderSummaryItems = MutableStateFlow<List<CartItem>>(emptyList())
    val orderItems: StateFlow<List<CartItem>> = _orderSummaryItems
    private var cartItems: List<CartItem> = emptyList()
    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())


    fun setCartItems(items: List<CartItem>) {
        cartItems = items
        _orderSummaryItems.value = items.map {
            CartItem(
                id = it.id,
                name = it.name,
                price = it.price,
                imageResource = it.imageResource,
                quantity = it.quantity
            )
        }
    }

    fun submitOrder() {
        if (cartItems.isEmpty() || cartItems.any { it.quantity <= 0 }) {
            // Handle empty cart case (e.g., show a message)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val order = createOrder()
                fastFoodRepository.saveOrderToLocal(order)
                fastFoodRepository.clearCart()

            } catch (e: Exception) {
                // Handle database error (e.g., log the error)
                e.printStackTrace()
            }
        }
    }

    private suspend fun createOrder(): Order {
        val currentDate = sdf.format(Date())
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        val formattedPrice = "%.2f".format(totalPrice).toDouble()

        for (cartItem in cartItems) {
            val foodItem = fastFoodRepository.getFoodItemByName(cartItem.name)
            if (foodItem != null) {
                updateFoodItemQuantity(foodItem.id, foodItem.quantity - cartItem.quantity)
            } else {
                // Handle the case where the food item is not found
                println("Food item with name ${cartItem.name} not found")
            }
        }

        return Order(
            orderDate = currentDate,
            totalPrice = formattedPrice,
            items = cartItems
        )
    }
    private fun updateFoodItemQuantity(itemId: Int, newQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fastFoodRepository.updateFoodItemQuantity(itemId, newQuantity)
        }
    }
}