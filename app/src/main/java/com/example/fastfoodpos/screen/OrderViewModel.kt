package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderViewModel @Inject constructor(private val repository: FastFoodRepository) : ViewModel() {

    private val _orderItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val orderItems: StateFlow<List<FoodItem>> get() = _orderItems

    fun addOrderItem(item: FoodItem) {
        // Add item to the order
        _orderItems.value = _orderItems.value + item
    }

    fun submitOrder() {
        // Submit the order and save it to the database or send to the server
        viewModelScope.launch {
            // Handle order submission logic
        }
    }
}
