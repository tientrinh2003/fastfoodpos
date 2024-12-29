package com.example.fastfoodpos.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fastfoodpos.R
import com.example.fastfoodpos.domain.model.CartItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel(),
    onCheckoutClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        val cartItems = viewModel.cartState.collectAsState(initial = emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(cartItems.value) { item ->
                    CartItemCard(
                        item = item,
                        onIncreaseQuantity = { viewModel.increaseQuantity(it) },
                        onDecreaseQuantity = { viewModel.decreaseQuantity(it) },
                        onDelete = { viewModel.deleteFromCart(it) }
                    )
                }
            }

            Button(
                onClick = onCheckoutClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Proceed to Checkout")
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onIncreaseQuantity: (CartItem) -> Unit,
    onDecreaseQuantity: (CartItem) -> Unit,
    onDelete: (CartItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Item Image
            AsyncImage(
                model = item.imageResource,
                contentDescription = "Cart Item Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Item Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp)
            ) {
                // Item Name
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    )
                )
                // Item Price
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 18.sp
                    )
                )
            }

            // Quantity Control
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrease Button (Image with clickable modifier)
                Image(
                    painter = painterResource(id = R.drawable.icon_minus), // Replace with your drawable
                    contentDescription = "Decrease Quantity",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .clickable { onDecreaseQuantity(item) } // Apply clickable modifier here
                )

                // Quantity
                Text(
                    text = "${item.quantity}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Increase Button (Image with clickable modifier)
                Image(
                    painter = painterResource(id = R.drawable.icon_plus), // Replace with your drawable
                    contentDescription = "Increase Quantity",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .clickable { onIncreaseQuantity(item) } // Apply clickable modifier here
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Delete Button (Icon remains unchanged, but you can replace it with Image if needed)
            IconButton(
                onClick = { onDelete(item) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon), // Replace with your drawable
                    contentDescription = "Delete Item",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
