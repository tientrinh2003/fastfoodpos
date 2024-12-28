package com.example.fastfoodpos.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.data.network.DTO.FoodItemDTO
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(private val repository: FastFoodRepository) : ViewModel() {

    private val _menuItems = MutableLiveData<List<FoodItemDTO>>()
    val menuItems: LiveData<List<FoodItemDTO>> get() = _menuItems

    init {
        fetchMenuItems()
    }

    private fun fetchMenuItems() {
        viewModelScope.launch {
            try {
                val foodItems = repository.fetchFoodItems()
                _menuItems.value = foodItems
            } catch (e: Exception) {
                // Handle the error gracefully (show error message, etc.)
            }
        }
    }
}
