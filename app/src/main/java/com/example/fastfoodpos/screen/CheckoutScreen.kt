package com.example.fastfoodpos.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutScreen(totalAmount: Double) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Checkout", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Total: \$${"%.2f".format(totalAmount)}")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Payment Method:")

        Button(onClick = {
            println("Card Payment Selected")
        }) {
            Text(text = "Pay with Card")
        }

        Button(
            onClick = {
                println("Cash Payment Selected")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Pay with Cash")
        }
    }
}
