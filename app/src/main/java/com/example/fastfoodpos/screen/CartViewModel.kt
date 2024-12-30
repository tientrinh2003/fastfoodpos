package com.example.fastfoodpos.screen

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.CartItem
import com.example.fastfoodpos.domain.repository.CartRepository
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val fastFoodRepository: FastFoodRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _cartState = MutableStateFlow<List<CartItem>>(emptyList())
    val cartState: StateFlow<List<CartItem>> = _cartState

    init {
        viewModelScope.launch {
            cartRepository.getAllCartItems().collect {
                _cartState.value = it
            }
        }
    }

    suspend fun addToCart(item: CartItem) {
        val availableQuantity = withContext(Dispatchers.IO) {
            fastFoodRepository.getFoodItemByName(item.name)?.quantity ?: 0
        }
        if (availableQuantity > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                val existingItem = _cartState.value.find { it.id == item.id }
                if (existingItem != null) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                    cartRepository.updateCartItem(updatedItem)
                } else {
                    cartRepository.insertCartItem(item.copy(quantity = 1))
                }
            }
        } else {
            // Show a Toast message to the user
            Toast.makeText(getApplication(), "Food item with name ${item.name} is not available in stock", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun increaseQuantity(item: CartItem) {
        val availableQuantity = withContext(Dispatchers.IO) {
            fastFoodRepository.getFoodItemByName(item.name)?.quantity ?: 0
        }
        if (availableQuantity > item.quantity) {
            viewModelScope.launch(Dispatchers.IO) {
                val updatedItem = item.copy(quantity = item.quantity + 1)
                cartRepository.updateCartItem(updatedItem)
            }
        } else {
            // Show a Toast message to the user
            Toast.makeText(getApplication(), "Not enough stock for ${item.name}", Toast.LENGTH_SHORT).show()
        }
    }

    fun decreaseQuantity(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (item.quantity == 1) {
                cartRepository.deleteCartItem(item)
            } else {
                val updatedItem = item.copy(quantity = item.quantity - 1)
                cartRepository.updateCartItem(updatedItem)
            }
        }
    }

    fun deleteFromCart(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCartItem(item)
        }
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.clearCart()
        }
    }
}