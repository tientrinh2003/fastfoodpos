package com.example.orderapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fastfoodpos.domain.model.CartItem


@Composable
fun CheckoutScreen(cartItems: List<CartItem>, onPaymentSuccess: () -> Unit) {
    var paymentMethod by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Review Your Order", style = MaterialTheme.typography.titleLarge)

        // Display Cart Items
        cartItems.forEach {
            Text("${it.name} x${it.quantity} - $${it.price * it.quantity}", style = MaterialTheme.typography.bodyMedium)
        }

        // Total Price
        Text("Total: $${cartItems.sumOf { it.price * it.quantity }}", style = MaterialTheme.typography.titleMedium)

        // Payment Method Selection
        Text("Select Payment Method:", style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { paymentMethod = "Cash"; onPaymentSuccess() }) {
                Text("Cash")
            }
            Button(onClick = { paymentMethod = "Internet Banking" }) {
                Text("Internet Banking")
            }
        }

        // QR Code Display (if Internet Banking is selected)
        if (paymentMethod == "Internet Banking") {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Scan the QR code to pay", style = MaterialTheme.typography.bodyMedium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text("QR Code Placeholder")
            }
        }

        // Finish Button
        Button(
            onClick = onPaymentSuccess,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish")
        }
    }
}
