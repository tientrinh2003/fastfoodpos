package com.example.fastfoodpos.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastfoodpos.domain.model.Customer
import kotlinx.coroutines.launch

class CustomerViewModel : ViewModel() {
    private val _customer = MutableLiveData<Customer>()
    val customer: LiveData<Customer> get() = _customer

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchCustomerDetails()
    }

    private fun fetchCustomerDetails() {
        _isLoading.value = true
        viewModelScope.launch {
            // Simulating a delay (e.g., fetching from database or API)
            _customer.value = Customer(
                id = 1,
                name = "John Doe",
                email = "johndoe@example.com",
                orderHistory = listOf("Burger", "Fries", "Coke")
            )
            _isLoading.value = false
        }
    }
}
