package com.example.fastfoodpos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                title = { Text(
                    text = "Menu Items",
                    fontFamily = FontFamily.Cursive,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("adminMenuScreen") }) {
                        Icon(Icons.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
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
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onItemClick = { foodItem ->
                        navController.navigate("editItemScreen/${foodItem.id}")
                    },
                    onDelete = { foodItem ->
                        viewModel.deleteFoodItem(foodItem)
                    }
                )
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
    onItemClick: (FoodItem) -> Unit,
    onDelete: (FoodItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(foodItems) { item ->
            FoodItemCard(item = item, onItemClick = onItemClick, onDelete = onDelete)
        }
    }
}

@Composable
fun FoodItemCard(item: FoodItem, onItemClick: (FoodItem) -> Unit, onDelete: (FoodItem) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = item.imageResource,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable._157846_200)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "\$${item.price}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onItemClick(item) },
                    modifier = Modifier
                ) {
                    Text("Edit Item")
                }
                IconButton(
                    onClick = { onDelete(item) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = "Delete Item",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}