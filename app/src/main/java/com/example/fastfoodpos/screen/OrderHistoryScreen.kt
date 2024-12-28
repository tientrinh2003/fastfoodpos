package com.example.fastfoodpos.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val orders = viewModel.orders.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Use displayLarge instead of h4
        Text(text = "Order History", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        orders.value.forEach { order ->
            OrderHistoryCard(order = order)
        }
    }
}

@Composable
fun OrderHistoryCard(order: Order) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Use titleMedium instead of h6 for order ID
            Text(text = "Order #${order.id}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            // Use bodyLarge instead of body1 for order total
            Text(text = "\$${"%.2f".format(order.totalPrice)}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
