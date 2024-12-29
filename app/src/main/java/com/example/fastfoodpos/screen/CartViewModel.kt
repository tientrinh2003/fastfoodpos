package com.example.fastfoodpos.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.CartItem
import com.example.fastfoodpos.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {
    private val _cartState = MutableStateFlow<List<CartItem>>(emptyList())
    val cartState: StateFlow<List<CartItem>> = _cartState

    init {
        viewModelScope.launch {
            cartRepository.getAllCartItems().collect {
                _cartState.value = it
            }
        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingItem = _cartState.value.find { it.id == item.id }
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                cartRepository.updateCartItem(updatedItem)
            } else {
                cartRepository.insertCartItem(item.copy(quantity = 1))
            }
        }
    }

    fun increaseQuantity(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedItem = item.copy(quantity = item.quantity + 1)
            cartRepository.updateCartItem(updatedItem)
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