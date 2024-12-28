package com.example.fastfoodpos.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastfoodpos.domain.model.Order

@Composable
fun OrderHistoryScreen(viewModel: OrderHistoryViewModel = hiltViewModel()) {
    val orderHistory = viewModel.orderHistory.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Order History", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        orderHistory.value.forEach { order ->
            OrderHistoryCard(order = order)
        }
    }
}

@Composable
fun OrderHistoryCard(order: Order) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order ID: ${order.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Date: ${order.orderDate}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Total: \$${order.totalPrice}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Items:", style = MaterialTheme.typography.bodyMedium)
            order.items.forEach { item ->
                Text(text = "- $item", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}