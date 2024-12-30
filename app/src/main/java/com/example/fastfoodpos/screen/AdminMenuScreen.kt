package com.example.fastfoodpos.ui

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fastfoodpos.R
import com.example.fastfoodpos.screen.OrderHistoryViewModel

@Composable
fun AdminMenuScreen(
    navController: NavController,
    onLogout: () -> Unit,
    viewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val orderHistory = viewModel.orderHistory.collectAsState(initial = emptyList())
    val scrollState = rememberScrollState()

    // Wrap the entire layout with verticalScroll to make it scrollable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Make the content scrollable
            .background(Color.Red) // Background color
    ) {
        // Title Text
        Text(
            text = "POSVN",
            fontFamily = FontFamily.Cursive,
            color = Color.White,
            fontSize = 60.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 16.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            AdminSummaryCardContent(
                completedOrders = orderHistory.value.size,
                totalEarnings = orderHistory.value.sumOf { it.totalPrice }
            )
        }

        // Add Menu Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)
        ) {
            AdminCardWithClick(
                imageRes = R.drawable.icon_plus,
                text = "Add Menu",
                onClick = { navController.navigate("addItemScreen") }
            )
            AdminCardWithClick(
                imageRes = R.drawable.eye,
                text = "View Menu",
                onClick = { navController.navigate("viewMenuScreen") }
            )
        }

        // History Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)
        ) {
            AdminCardWithClick(
                imageRes = R.drawable.frame_85,
                text = "History",
                onClick = { navController.navigate("historyOrder") }
            )
            AdminCardWithClick(
                imageRes = R.drawable.logout,
                text = "Logout",
                onClick = { onLogout() }
            )
        }
    }
}

@Composable
fun AdminCardWithClick(imageRes: Int, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .clickable(onClick = onClick), // Make card clickable
        shape = RoundedCornerShape(20.dp)
    ) {
        AdminCardContent(
            imageRes = imageRes,
            text = text
        )
    }
}

@Composable
fun AdminCardContent(imageRes: Int, text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = text,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AdminSummaryCardContent(completedOrders: Int, totalEarnings: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SummaryColumn(title = "Completed Order", value = completedOrders.toString(), color = Color.Black)
            SummaryColumn(title = "Earning", value = "${"%.2f".format(totalEarnings)} $", color = Color.Green)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SummaryColumn(title: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
            )
    }
}
