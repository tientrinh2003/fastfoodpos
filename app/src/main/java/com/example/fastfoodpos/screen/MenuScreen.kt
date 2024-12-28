package com.example.fastfoodpos.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastfoodpos.data.network.DTO.FoodItemDTO

@Composable
fun MenuScreen(viewModel: MenuViewModel = hiltViewModel()) {
    val menuItems = viewModel.menuItems.observeAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Menu", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(16.dp))

        menuItems.value.forEach { foodItem ->
            MenuItemCard(foodItem = foodItem)
        }
    }
}

@Composable
fun MenuItemCard(foodItem: FoodItemDTO) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = foodItem.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text(text = "\$${foodItem.price}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
