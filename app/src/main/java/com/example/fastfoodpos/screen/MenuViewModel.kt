package com.example.fastfoodpos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val fastFoodRepository: FastFoodRepository
) : ViewModel() {

    private val _foodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodItems: StateFlow<List<FoodItem>> = _foodItems

    init {
        fetchFoodItems()
    }

    fun fetchFoodItems() {
        viewModelScope.launch {
            println("MenuViewModel: fetchFoodItems() called")
            try {
                val items = fastFoodRepository.fetchFoodItems()
                _foodItems.value = items
                println("MenuViewModel: Food items fetched successfully: $items")
            } catch (e: Exception) {
                println("MenuViewModel: Error fetching food items: ${e.message}")
            }
        }
    }
}