package com.example.fastfoodpos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val fastFoodRepository: FastFoodRepository,
) : ViewModel() {

    fun addFoodItem(name: String, price: Double, imageUri: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val foodItem = FoodItem(
                    name = name,
                    price = price,
                    imageResource = imageUri, // Store the URI as a String
                    quantity = 0
                )
                fastFoodRepository.insertFoodItem(foodItem)
            }
        }
    }
}