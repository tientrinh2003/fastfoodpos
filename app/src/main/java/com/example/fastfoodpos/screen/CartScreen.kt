package com.example.fastfoodpos.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastfoodpos.domain.model.CartItem

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onCheckoutClicked: () -> Unit
) {
    val cartItems = viewModel.cartState.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Shopping Cart", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        cartItems.value.forEach { item ->
            CartItemCard(item = item)
        }

        Button(
            onClick = onCheckoutClicked,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text(text = "Proceed to Checkout")
        }
    }
}

@Composable
fun CartItemCard(item: CartItem) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text(text = "Qty: ${item.quantity}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "\$${item.price * item.quantity}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}