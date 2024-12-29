package com.example.fastfoodpos.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.constraintlayout.compose.ConstraintLayout
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red) // Background color
    ) {
        val (titleText, cardViewHistory, cardViewLogout, cardViewSummary, cardViewAddMenu, cardViewViewMenu) = createRefs()

        // Title Text
        Text(
            text = "POSVN",
            fontFamily = FontFamily.Cursive,
            color = Color.White,
            fontSize = 60.sp,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(cardViewSummary) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(parent.start, margin = 30.dp)
                    end.linkTo(parent.end, margin = 30.dp)
                },
            shape = RoundedCornerShape(20.dp)
        ) {
            AdminSummaryCardContent(
                completedOrders = orderHistory.value.size,
                totalEarnings = orderHistory.value.sumOf { it.totalPrice }
            )
        }

        // Other Cards...
        // Add Menu Card
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .constrainAs(cardViewAddMenu) {
                    top.linkTo(cardViewSummary.bottom, margin = 28.dp)
                    start.linkTo(parent.start, margin = 30.dp)
                    end.linkTo(cardViewViewMenu.start)
                }
                .clickable { navController.navigate("addItemScreen") }, // Make card clickable
            shape = RoundedCornerShape(20.dp)
        ) {
            AdminCardContent(
                imageRes = R.drawable.icon_plus,
                text = "Add Menu"
            )
        }

        // View Menu Card
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .constrainAs(cardViewViewMenu) {
                    top.linkTo(cardViewSummary.bottom, margin = 28.dp)
                    start.linkTo(cardViewAddMenu.end)
                    end.linkTo(parent.end, margin = 30.dp)
                }
                .clickable { navController.navigate("viewMenuScreen") },
            shape = RoundedCornerShape(20.dp)
        ) {
            AdminCardContent(
                imageRes = R.drawable.eye,
                text = "View Menu"
            )
        }

        // History Card
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .constrainAs(cardViewHistory) {
                    top.linkTo(cardViewAddMenu.bottom, margin = 30.dp)
                    start.linkTo(parent.start, margin = 30.dp)
                    end.linkTo(cardViewLogout.start)
                }
                .clickable { navController.navigate("historyOrder") },
            shape = RoundedCornerShape(20.dp)
        ) {
            AdminCardContent(
                imageRes = R.drawable.frame_85,
                text = "History"
            )
        }

        // Logout Card
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .constrainAs(cardViewLogout) {
                    top.linkTo(cardViewViewMenu.bottom, margin = 30.dp)
                    start.linkTo(cardViewHistory.end)
                    end.linkTo(parent.end, margin = 30.dp)
                }
                .clickable { onLogout() }, // Make card clickable
            shape = RoundedCornerShape(20.dp)
        ) {
            AdminCardContent(
                imageRes = R.drawable.logout,
                text = "Logout"
            )
        }
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
            SummaryColumn(title = "Completed\nOrder", value = completedOrders.toString(), color = Color.Black)
            SummaryColumn(title = "Earning\n", value = "$${"%.2f".format(totalEarnings)}", color = Color.Green)
        }
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
            color = color,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
