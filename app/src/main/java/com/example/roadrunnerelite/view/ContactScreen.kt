package com.example.roadrunnerelite.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.roadrunnerelite.R
import java.io.IOException
import kotlin.coroutines.CoroutineContext


@Composable
fun ContactScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Contact us",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "If you have some cool car or if you want professional photos with 4K video feel free to contact us:",
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Email:",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "os.car.spotter0@gmail.com",
                fontSize = 25.sp,
                color = Color.Blue,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable {
                        openEmail(context, "os.car.spotter0@gmail.com")
                    }
            )

            Text(
                text = "Instagram:",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)

            )

            Text(
                text = "@os.car.spotter",
                fontSize = 25.sp,
                color = Color.Blue,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable {
                        openInstagram(context)
                    }
            )

            Text(
                text = "Latest Projects",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Start) // Align to the left
            )

            val projects = listOf(
                "Project 1: Tesla Model S",
                "Project 2: BMW M3",
                "Project 3: Audi R8",
                "Project 4: Mercedes-Benz AMG GT",
                "Project 5: Porsche 911",
                "Project 6: Lamborghini Aventador",
                "Project 7: Ferrari 488",
                "Project 8: McLaren 720S",
                "Project 9: Aston Martin DB11",
                "Project 10: Chevrolet Corvette"
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Take available vertical space
            ) {
                items(projects) { project ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = project,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}



fun openInstagram(context: Context) { // funkcija za otvaranje instagrama kad se klikne na link
    val uri = Uri.parse("https://instagram.com/os.car.spotter")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}

fun openEmail(context: Context, email: String) { // funkcija za otvaranje maila kad se klkne link
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }
    context.startActivity(intent)
}

