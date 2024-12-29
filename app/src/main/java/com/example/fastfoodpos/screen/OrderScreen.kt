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
import com.example.fastfoodpos.domain.model.CartItem

@Composable
fun OrderScreen(
    viewModel: OrderViewModel,
    onOrderSuccess: () -> Unit
) {
    val orderItems = viewModel.orderItems.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Place your Order", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        orderItems.value.forEach { item ->
            OrderItemCard(item = item)
        }

        Button(
            onClick = {
                viewModel.submitOrder()
                onOrderSuccess()
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text(text = "Confirm Order")
        }
    }
}

@Composable
fun OrderItemCard(item: CartItem) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text(text = "\$${item.price}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}