package com.example.orderapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fastfoodpos.R
import com.example.fastfoodpos.domain.model.CartItem
import com.example.fastfoodpos.screen.OrderViewModel
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    cartItems: List<CartItem>,
    onPaymentSuccess: () -> Unit,
) {
    var selectedPaymentMethod by remember { mutableStateOf("Cash") }
    val paymentOptions = listOf("Credit card", "Debit card", "E-Wallet")
    var selectedOption by remember { mutableStateOf(paymentOptions[0]) }
    val viewModel: OrderViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu Items",
                        fontFamily = FontFamily.Cursive,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                // Title
                Text(
                    text = "Checkout",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Cart Items List
            items(cartItems) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${item.name} x ${item.quantity}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "\$${item.price * item.quantity}", style = MaterialTheme.typography.bodyLarge)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Total Price
                val totalPrice = cartItems.sumOf { it.price * it.quantity }
                val formattedPrice = BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Normal,
                        fontSize = 34.sp
                    )
                    Text(
                        text = "\$${formattedPrice}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Red,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Normal,
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Options
                paymentOptions.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (selectedOption == option) Color(0xFFE0E0E0) else Color.Transparent
                            )
                            .selectable(
                                selected = (selectedOption == option),
                                onClick = { selectedOption = option }
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option },
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        when (option) {
                            "Credit card" -> {
                                Image(
                                    painter = painterResource(id = R.drawable.pngwing_28),
                                    contentDescription = "MasterCard",
                                    modifier = Modifier.size(40.dp)
                                )
                                PaymentOptionDetails(
                                    title = "Credit card",
                                    subtitle = "5105 **** **** 0505",
                                    textColor = MaterialTheme.colorScheme.scrim,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }

                            "Debit card" -> {
                                Image(
                                    painter = painterResource(id = R.drawable.pngwing_27__1_),
                                    contentDescription = "Visa",
                                    modifier = Modifier.size(40.dp)
                                )
                                PaymentOptionDetails(
                                    title = "Debit card",
                                    subtitle = "3566 **** **** 0505",
                                    textColor = MaterialTheme.colorScheme.scrim,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }

                            "E-Wallet" -> {
                                Image(
                                    painter = painterResource(id = R.drawable.pngwing_27),
                                    contentDescription = "MOMO",
                                    modifier = Modifier.size(40.dp)
                                )
                                PaymentOptionDetails(
                                    title = "E-Wallet",
                                    subtitle = "038972***",
                                    textColor = MaterialTheme.colorScheme.scrim,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Pay Now Button
                Button(
                    onClick = {
                        viewModel.submitOrder()
                        onPaymentSuccess()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Pay Now",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentOptionDetails(title: String, subtitle: String, textColor: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subtitle,
            color = textColor.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
    }
}