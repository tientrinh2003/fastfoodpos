package com.example.fastfoodpos.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.fastfoodpos.R
import com.example.fastfoodpos.viewmodel.AddItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(onNavigateBack: () -> Unit, viewModel: AddItemViewModel = hiltViewModel()) {
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var itemImageUri by remember { mutableStateOf<Uri?>(null) }
    var itemNameError by remember { mutableStateOf<String?>(null) }
    var itemPriceError by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        itemImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Top Bar with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add Item",
                fontFamily = FontFamily.Cursive,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }

        // Item Name Input Field
        OutlinedTextField(
            value = itemName,
            onValueChange = {
                itemName = it
                itemNameError = null // Clear error when input changes
            },
            label = { Text("Item Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            ),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = itemNameError != null,
            supportingText = {
                if (itemNameError != null) {
                    Text(text = itemNameError!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Item Price Input Field
        OutlinedTextField(
            value = itemPrice,
            onValueChange = {
                itemPrice = it
                itemPriceError = null // Clear error when input changes
            },
            label = { Text("Item Price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            ),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = itemPriceError != null,
            supportingText = {
                if (itemPriceError != null) {
                    Text(text = itemPriceError!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Image Selection
        Text(
            text = "Select Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
            color = MaterialTheme.colorScheme.onSurface,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )

        // Display Selected Image
        Card(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(200.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            if (itemImageUri != null) {
                AsyncImage(
                    model = itemImageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable._157846_200), // Placeholder image
                    contentDescription = "Placeholder Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

// Add Item Button
        Button(
            onClick = {
                // Validate inputs
                var isValid = true

                if (itemName.isBlank()) {
                    itemNameError = "Item name cannot be empty"
                    isValid = false
                }
                if (itemPrice.isBlank()) {
                    itemPriceError = "Item price cannot be empty"
                    isValid = false
                } else {
                    val price = itemPrice.toDoubleOrNull()
                    if (price == null) {
                        itemPriceError = "Invalid price format"
                        isValid = false
                    }
                    if (price != null && price <= 0) {
                        itemPriceError = "Price must be greater than zero"
                        isValid = false
                    }
                }
                if (isValid) {
                    val price = itemPrice.toDouble()
                    val imageUriString = itemImageUri?.toString() ?: ""
                    viewModel.addFoodItem(itemName, price, imageUriString)
                    // Clear input fields after successful add
                    itemName = ""
                    itemPrice = ""
                    itemImageUri = null
                    itemNameError = null
                    itemPriceError = null
                    onNavigateBack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Add Item",
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold
            )
        }
    }
}