package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.Order
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(private val fastFoodRepository: FastFoodRepository) :
    ViewModel() {
    private val _orderHistory = MutableStateFlow<List<Order>>(emptyList())
    val orderHistory: StateFlow<List<Order>> = _orderHistory

    init {
        viewModelScope.launch {
            fastFoodRepository.fetchOrdersFromLocal().collect {
                _orderHistory.value = it
            }
        }
    }
}