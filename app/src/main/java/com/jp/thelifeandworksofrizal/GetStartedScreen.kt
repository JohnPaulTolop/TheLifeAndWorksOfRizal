package com.example.yourapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jp.thelifeandworksofrizal.R

@Composable
fun GetStartedScreen(navController: androidx.navigation.NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.pixel_art_rizal), // make sure you renamed the PNG to pixel_art_rizal.png in res/drawable
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.None
        )

        // Content over the image
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Maayong buntag!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("dashboard") },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.brown)
                )
            ) {
                Text(
                    text = "Get Started",
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}