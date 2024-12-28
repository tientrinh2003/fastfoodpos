package com.example.fastfoodpos.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CustomerScreen(viewModel: CustomerViewModel = hiltViewModel()) {
    // Observe LiveData using observeAsState
    val customer = viewModel.customer.observeAsState(initial = null)
    val isLoading = viewModel.isLoading.observeAsState(initial = false)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading.value) {
            // Show loading spinner while customer data is being fetched
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            customer.value?.let { customer ->
                // Display customer details
                Text(text = "Customer: ${customer.name}", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Email: ${customer.email}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Order History:")
                customer.orderHistory.forEach { order ->
                    Text(text = order, style = MaterialTheme.typography.bodySmall)
                }

                // Button to view order history or perform another action
                Button(
                    onClick = { /* Handle view order history or other actions */ },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text(text = "View Order History")
                }
            }
        }
    }
}
