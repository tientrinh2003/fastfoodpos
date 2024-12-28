package com.example.fastfoodpos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.viewmodel.MenuViewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    onCartClicked: () -> Unit
) {
    val foodItems by viewModel.foodItems.collectAsState(initial = emptyList()) // Changed from menuItems to foodItems
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onCartClicked = onCartClicked
        )

        FoodList(menuItems = foodItems, searchQuery = searchQuery) // Changed from menuItems.value to foodItems
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCartClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Search") },
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onCartClicked) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Filter")
        }
    }
}

@Composable
fun FoodList(menuItems: List<FoodItem>, searchQuery: String) {
    val filteredItems = if (searchQuery.isBlank()) {
        menuItems
    } else {
        menuItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
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
            Image(
                painter = painterResource(id = item.imageResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "\$${item.price}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}