package com.example.orderapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
fun CheckoutScreen(
    cartItems: List<CartItem>,
    onPaymentSuccess: () -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("Cash") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Checkout", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        cartItems.forEach { item ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Text(text = "${item.name} x ${item.quantity}", modifier = Modifier.weight(1f))
                Text(text = "\$${item.price * item.quantity}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        Text(text = "Total: \$${totalPrice}", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Select Payment Method", style = MaterialTheme.typography.titleMedium)

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selectedPaymentMethod == "Cash", onClick = { selectedPaymentMethod = "Cash" })
            Text(text = "Cash")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selectedPaymentMethod == "Internet Banking", onClick = { selectedPaymentMethod = "Internet Banking" })
            Text(text = "Internet Banking")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onPaymentSuccess,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text(text = "Finish")
        }
    }
}