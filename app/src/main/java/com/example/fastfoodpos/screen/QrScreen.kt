package com.example.fastfoodpos.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fastfoodpos.R

@Composable
fun QrScreen(onNavigateToMenu: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Use background modifier
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Scan the QR code to pay!", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.png_transparent_qr_code_information_qr_code_android_qrcode_text_rectangle_monochrome_thumbnail),
            contentDescription = "QR Code",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateToMenu) {
            Text(text = "Paid",
                color = MaterialTheme.colorScheme.background)
        }
    }
}