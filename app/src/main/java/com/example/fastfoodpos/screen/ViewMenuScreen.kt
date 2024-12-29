package com.example.fastfoodpos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fastfoodpos.R
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.viewmodel.MenuUiState
import com.example.fastfoodpos.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewMenuScreen(navController: NavController, viewModel: MenuViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menu Items") },
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
        val uiState by viewModel.foodItems.collectAsState()

        when (uiState) {
            is MenuUiState.Loading -> {
                Text(
                    text = "Loading...",
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is MenuUiState.Success -> {
                val foodItems = (uiState as MenuUiState.Success).foodItems
                FoodItemList(
                    foodItems = foodItems,
                    modifier = Modifier.padding(paddingValues)
                ) { foodItem ->
                    navController.navigate("editItemScreen/${foodItem.id}")
                }
            }
            is MenuUiState.Error -> {
                Text(
                    text = "Error: ${(uiState as MenuUiState.Error).message}",
                    modifier = Modifier.padding(paddingValues)
                )
            }
            else -> {
                Text(
                    text = "Unknown State",
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun FoodItemList(
    foodItems: List<FoodItem>,
    modifier: Modifier = Modifier,
    onItemClick: (FoodItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(foodItems) { item ->
            FoodItemCard(item = item, onItemClick = onItemClick)
        }
    }
}

@Composable
fun FoodItemCard(item: FoodItem, onItemClick: (FoodItem) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Ensures a square aspect ratio
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = item.imageResource,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(), // Matches the parent Box size
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable._157846_200)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "\$${item.price}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)

        }
    }
}