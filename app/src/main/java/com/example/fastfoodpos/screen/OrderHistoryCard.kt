package com.example.fastfoodpos.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fastfoodpos.domain.model.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    navController: NavController,
    viewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val orders = viewModel.orderHistory.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Order History",
                        fontFamily = FontFamily.Cursive,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("adminMenuScreen") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(orders.value) { order ->
                    OrderItemCard(order = order)
                }
            }
        }
    }
}

fun formatDate(date: Date): String {
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return outputFormat.format(date)
}

fun parseDate(dateString: String): Date? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Adjust the format to match your date string
    return try {
        inputFormat.parse(dateString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// OrderHistoryScreen.kt
@Composable
fun OrderItemCard(order: Order) {
    val date = parseDate(order.orderDate)
    val formattedDate = if (date != null) formatDate(date) else "Invalid Date"
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order Date:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Price:",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "\$${order.totalPrice}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Items:",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            order.items.forEach { item ->
                Text(
                    text = "- ${item.name} x ${item.quantity}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}