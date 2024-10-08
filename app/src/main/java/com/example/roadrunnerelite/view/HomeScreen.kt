package com.example.roadrunnerelite.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Picture
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
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
import com.example.roadrunnerelite.viewmodel.MyViewModel
import java.io.IOException

@Composable

fun HomeScreen(navController: NavController, viewModel: MyViewModel) {
    val reviews = listOf(
        "Review 1: This car is amazing! It handles well and has great fuel efficiency.",
        "Review 2: I love the design and the performance is top-notch.",
        "Review 3: Not satisfied with the interior quality, but the engine is powerful.",
        "Review 4: Best car I've owned so far. Highly recommend it.",
        "Review 5: Affordable and reliable. Perfect for city driving.",
        "Review 6: The car has a smooth ride but lacks some modern features.",
        "Review 7: Excellent customer service and a great vehicle overall.",
        "Review 8: Spacious and comfortable. Great for long trips.",
        "Review 9: The technology integration is superb. Very user-friendly.",
        "Review 10: Good value for money. Could improve on safety features."
    ) // lista reviewa na home screnu

    fun loadImageBitmapFromAssets(context: Context, path: String): ImageBitmap? {
        return try {
            val inputStream = context.assets.open(path)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap.asImageBitmap()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    } // funkcija za ucitavanje slike iz foldera assets

    val context = LocalContext.current

    val carImages = listOf(
        "HomeScreen/Audi_RS6R_ABT_18.jpg",
        "HomeScreen/BMW_G80_M3_Competition_07.jpg",
        "HomeScreen/Brabus_G800_23.jpg",
        "HomeScreen/Ferrari_330_GT_09.jpg",
        "HomeScreen/Ferrari_612_Scaglietti_09.jpg",
        "HomeScreen/Lamborghini_Aventador_LP_700_4_10.jpg",
        "HomeScreen/Lamborghini_Urus_OS_07.jpg",
        "HomeScreen/Porsche_911_992_Carrera_4S_ZG_plava_07.jpg",
        "HomeScreen/Porsche_911_992_GT3_11.jpg",
        "HomeScreen/Rimac_Nevera_18.jpg"
    ) // slike

    val carNames = listOf(
        "Audi RS6R ABT",
        "BMW G80 M3 Competition",
        "Brabus G800",
        "Ferrari 330GT",
        "Ferrari 612 Scaglietti",
        "Lamborghini Aventador LP 700 4",
        "Lamborghini Urus",
        "Porsche 911 992 Carrera 4S",
        "Porsche 911 992 GT3",
        "Rimac Nevera"

    ) // imena
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } // postavljam bottom navigaciju
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ROAD RUNNER ELITE",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    onClick = {viewModel.fetchCarData(1) // na klik gumba se pokreće funkcija fetchCarData
                        navController.navigate("carspottingscreen") }, // te se prebacuje na carspottingscreen
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),

                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "\uD83D\uDE97", // Car emoji
                            fontSize = 32.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Car Spotting",
                            fontSize = 13.sp
                        )
                    }
                }

                Button(
                    onClick = { viewModel.fetchCarData(2) // na klik se poziva funkcija fetchCarData
                        navController.navigate("professionalphotographyscreen")  }, // i prebacuje se na professional screen
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "\uD83D\uDCF7", // Photo emoji
                            fontSize = 32.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Professional Photography",
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Text(
                text = "Popular Cars",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Start)
            )

            LazyRow {
                items(carImages.zip(carNames)) { (imagePath, carName) -> // itemsi sa slikom i imenom auta, lazy row je da se slike ne ucitaju dok ne budu na ekranu
                    val imageBitmap = remember { loadImageBitmapFromAssets(context, imagePath) }
                    imageBitmap?.let {
                        Card( // card je kao jedan item i sadzi ovo ispod
                            modifier = Modifier
                                .padding(8.dp)
                                .size(160.dp, 180.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    bitmap = it,
                                    contentDescription = "Car Image",
                                    modifier = Modifier
                                        .size(120.dp)

                                )
                                Text(
                                    text = carName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            Text(
                text = "Reviews from car owners",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Start)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Take available vertical space
            ) {
                items(reviews.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = reviews[index],
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) { // funkcija koja pravi bottom navigation bar
    BottomNavigation (backgroundColor = Color.Gray){
        BottomNavigationItem(
            icon = { Text(text = "\uD83C\uDFE0", fontSize = 24.sp) }, // Home emoji 🏠
            label = { Text("Home") },
            selected = navController.currentDestination?.route == "homescreen",
            onClick = {
                navController.navigate("homescreen"){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Text(text = "\uD83D\uDCF7", fontSize = 24.sp) }, // Camera emoji 📷
            label = { Text("Photography") },
            selected = navController.currentDestination?.route == "photographyscreen", // Implement selected state logic
            onClick = {
                navController.navigate("photographyscreen"){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Text(text = "\uD83D\uDCDE", fontSize = 24.sp) }, // Telephone emoji 📞
            label = { Text("Contact") },
            selected = navController.currentDestination?.route == "contactscreen", // Implement selected state logic
            onClick = {
                navController.navigate("contactscreen"){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}