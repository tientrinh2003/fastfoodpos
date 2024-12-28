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
import com.example.fastfoodpos.domain.model.FoodItem

@Composable
fun OrderScreen(viewModel: OrderViewModel = hiltViewModel()) {
    val orderItems = viewModel.orderItems.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Use displayLarge instead of h4 for the title
        Text(text = "Place your Order", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        orderItems.value.forEach { item ->
            OrderItemCard(item = item)
        }

        Button(
            onClick = { viewModel.submitOrder() },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text(text = "Place Order")
        }
    }
}

@Composable
fun OrderItemCard(item: FoodItem) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Use titleMedium instead of h6 for the food item name
            Text(text = item.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            // Use bodyLarge instead of body1 for the price
            Text(text = "\$${item.price}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
