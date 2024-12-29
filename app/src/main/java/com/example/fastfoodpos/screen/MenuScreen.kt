package com.example.fastfoodpos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.viewmodel.MenuUiState
import com.example.fastfoodpos.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    onCartClicked: () -> Unit
) {
    val uiState by viewModel.foodItems.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (foodList, titleText, subtitleText, cartButton, searchBar) = createRefs()

        Text(
            text = "POSVN",
            fontFamily = FontFamily.Cursive,
            color = Color.Red,
            fontSize = 45.sp,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 10.dp)
                }
        )

        Text(
            text = "ORDER YOUR FAVOURITE FOOD!",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            fontSize = 18.sp,
            modifier = Modifier
                .constrainAs(subtitleText) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(titleText.start)
                }
        )

        IconButton(
            onClick = onCartClicked,
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFFF0F0F0),
                    shape = RoundedCornerShape(8.dp)
                )
                .constrainAs(cartButton) {
                    top.linkTo(searchBar.top)
                    bottom.linkTo(searchBar.bottom)
                    end.linkTo(parent.end, margin = 20.dp)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.fi_br_cart),
                contentDescription = "Cart",
                modifier = Modifier.size(30.dp)
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier
                .background(
                    color = Color(0xFFF0F0F0),
                    shape = RoundedCornerShape(8.dp)
                )
                .constrainAs(searchBar) {
                    top.linkTo(subtitleText.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(cartButton.start, margin = 10.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )

        // Handle UI states
        when (uiState) {
            is MenuUiState.Loading -> {
                Text(
                    text = "Loading...",
                    modifier = Modifier.constrainAs(foodList) {
                        top.linkTo(searchBar.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
            is MenuUiState.Success -> {
                FoodList(
                    menuItems = (uiState as MenuUiState.Success).foodItems,
                    searchQuery = searchQuery,
                    modifier = Modifier.constrainAs(foodList) {
                        top.linkTo(searchBar.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = androidx.constraintlayout.compose.Dimension.fillToConstraints // Ensure height matches constraints
                    }
                )
            }
            is MenuUiState.Error -> {
                Text(
                    text = "Error: ${(uiState as MenuUiState.Error).message}",
                    modifier = Modifier.constrainAs(foodList) {
                        top.linkTo(searchBar.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
            else -> {
                // Handle any unexpected state
                Text(
                    text = "Unknown State",
                    modifier = Modifier.constrainAs(foodList) {
                        top.linkTo(searchBar.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
        }
    }
}


@Composable
fun FoodList(menuItems: List<FoodItem>, searchQuery: String, modifier: Modifier = Modifier) {
    val filteredItems = if (searchQuery.isBlank()) {
        menuItems
    } else {
        menuItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(filteredItems) { item ->
            FoodItemCard(item = item)
        }
    }
}

@Composable
fun FoodItemCard(item: FoodItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Wrapping the Image inside a Box to control height dynamically
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Ensures a square aspect ratio
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Image(
                    painter = painterResource(id = item.imageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(), // Matches the parent Box size
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "\$${item.price}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            
        }
    }
}