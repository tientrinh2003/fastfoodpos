package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.CartItem
import com.example.fastfoodpos.domain.model.FoodItem
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
    private val _orderItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val orderItems: StateFlow<List<FoodItem>> = _orderItems
    private var cartItems: List<CartItem> = emptyList()

    fun setCartItems(items: List<CartItem>) {
        cartItems = items
        _orderItems.value = items.map {
            FoodItem(
                id = it.id,
                name = it.name,
                price = it.price,
                imageResource = 0,
                quantity = it.quantity
            )
        }
    }

    fun submitOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val order = createOrder()
            fastFoodRepository.saveOrderToLocal(order)
        }
    }

    private fun createOrder(): Order {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        val itemsName = cartItems.map { "${it.name} x ${it.quantity}" }
        return Order(
            orderDate = currentDate,
            totalPrice = totalPrice,
            items = itemsName
        )
    }
}