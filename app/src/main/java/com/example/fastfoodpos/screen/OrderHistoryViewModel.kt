package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.Order
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderHistoryViewModel @Inject constructor(private val repository: FastFoodRepository) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            _orders.value = repository.getOrdersFromLocal() // Fetch orders using repository
        }
    }
}
