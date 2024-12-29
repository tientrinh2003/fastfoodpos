package com.example.fastfoodpos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.R
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val fastFoodRepository: FastFoodRepository
) : ViewModel() {

    private val _foodItems = MutableStateFlow<MenuUiState>(MenuUiState.Loading)
    val foodItems: StateFlow<MenuUiState> = _foodItems

    init {
        fetchFoodItems()
        insertInitialData()
    }

    private fun fetchFoodItems() {
        viewModelScope.launch {
            println("MenuViewModel: fetchFoodItems() called")
            fastFoodRepository.fetchFoodItems()
                .catch { e ->
                    _foodItems.value = MenuUiState.Error(e.message ?: "Unknown error")
                }
                .collect { items ->
                    _foodItems.value = MenuUiState.Success(items)
                    println("MenuViewModel: Food items fetched successfully: $items")
                }
        }
    }

    fun addFoodItem(foodItem: FoodItem) {
        viewModelScope.launch {
            fastFoodRepository.insertFoodItem(foodItem)
        }
    }
    fun updateFoodItem(foodItem: FoodItem) {
        viewModelScope.launch {
            fastFoodRepository.updateFoodItem(foodItem)
        }
    }

    fun getFoodItemById(id: Int): FoodItem? {
        return foodItems.value.let { state ->
            when (state) {
                is MenuUiState.Success -> state.foodItems.find { it.id == id }
                else -> null
            }
            }
    }

    private fun insertInitialData() {
        viewModelScope.launch {
            val initialFoodItems = listOf(
                FoodItem(
                    id = 1,
                    name = "Burger",
                    price = 5.99,
                    imageResource = R.drawable.hamcheese
                ),
                FoodItem(
                    id = 2,
                    name = "Pizza",
                    price = 8.99,
                    imageResource = R.drawable.hamcheese
                ),
                FoodItem(
                    id = 3,
                    name = "Fries",
                    price = 3.99,
                    imageResource = R.drawable.hamcheese
                ),
                FoodItem(
                    id = 4,
                    name = "Coke",
                    price = 2.99,
                    imageResource = R.drawable.hamcheese
                ),
                FoodItem(
                    id = 5,
                    name = "Salad",
                    price = 6.99,
                    imageResource = R.drawable.hamcheese
                ),
                FoodItem(
                    id = 6,
                    name = "Ice Cream",
                    price = 4.99,
                    imageResource = R.drawable.hamcheese
                )
            )
            try {
                fastFoodRepository.insertFoodItems(initialFoodItems)
                println("MenuViewModel: Initial data inserted successfully")
            } catch (e: Exception) {
                println("MenuViewModel: Error inserting initial data: ${e.message}")
            }
        }
    }
}

sealed class MenuUiState {
    object Loading : MenuUiState()
    data class Success(val foodItems: List<FoodItem>) : MenuUiState()
    data class Error(val message: String) : MenuUiState()
}