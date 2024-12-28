package com.example.fastfoodpos.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastfoodpos.domain.model.CartItem


@Composable
fun CartScreen(viewModel: CartViewModel = hiltViewModel(), onCheckout: () -> Unit) {
    val cartItems by viewModel.cartState.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(cartItems) { item ->
                CartItemRow(item)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Checkout")
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("${cartItem.name} x${cartItem.quantity}")
        Text("Price: $${cartItem.price * cartItem.quantity}")
    }
}
