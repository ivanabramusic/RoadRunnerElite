package com.example.roadrunnerelite.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
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
fun PhotographyScreen (navController: NavController)
{

    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                item {// svaki item se sastoji od sekcije, sekcija sadzi naslov text i slike
                    Section(
                        title = "The Art of Car Photography: A Guide to Perfect Shots",
                        text = "Car photography requires careful planning and attention to detail to make the vehicle " +
                                "look as attractive as possible. There are several key factors that contribute to a " +
                                "successful car photo: the choice of a good location, the appropriate time for " +
                                "photography, lighting, shooting angles, and many others. Each of these elements " +
                                "plays a vital role in highlighting the characteristics of the vehicle."
                    )
                }
                item {
                    Section(
                        title = "Selection of Location and Time of Photography",
                        text = "Location is key to achieving the desired aesthetic impression. Ideal locations for car " +
                                "photography include open spaces with attractive landscapes, industrial backgrounds, " +
                                "or urban environments that can emphasize the lines and shape of the vehicle. In " +
                                "addition, it is important to choose the appropriate time of day. The golden hour, " +
                                "which occurs just after sunrise and before sunset, provides a soft, warm light that " +
                                "highlights the colors and shapes of the car. In addition, photographs can be taken at " +
                                "any time of the day. Our recommendation is to avoid taking photos on cloudy days " +
                                "because then the color on the vehicle does not look as attractive as on a sunny day.",
                        images = listOf("Photography zaslon/Profesionalno_fotografiranje_vozila_27.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_29.jpg")
                    )
                }
                item {
                    Section(
                        title = "Highlighting Vehicle Details",
                        text = "Each vehicle has unique details, from model designations to specific design " +
                                "elements. It is important to photograph these details because they give the vehicle " +
                                "character and recognition. For example, features such as special badges, design " +
                                "lines or special interior options can be focal points. A well-edited photo of these " +
                                "details can significantly improve the presentation of the vehicle, whether it is " +
                                "intended for the owner or potential buyers.",
                        images = listOf("Photography zaslon/Profesionalno_fotografiranje_vozila_01.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_02.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_11.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_22.jpg")
                    )
                }
                item {
                    Section(
                        title = "Photographing Rims",
                        text = "Rims are one of the most prominent parts of a car that often attract attention. Many " +
                                "owners decide to replace the factory rims with the aim of personalizing and " +
                                "improving the appearance of the vehicle. When photographing rims, it is crucial to " +
                                "pay attention to the shooting angle and the orientation of the wheels. It is " +
                                "recommended to rotate the wheel by 45° to achieve a dynamic and professional look " +
                                "in the photo. Also, highlighting details on the rims, such as a logo or a specific " +
                                "design, can further enrich the visual impression.",
                        images = listOf("Photography zaslon/Profesionalno_fotografiranje_vozila_18.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_33.jpg")
                    )
                }
                item {
                    Section(
                        title = "Angles of Photography",
                        text = "Shooting angles play a key role in the presentation of the car. Given that there are " +
                                "countless possible angles, experimentation is key to finding the perfect one. In " +
                                "addition to the angle, the height from which the photo is taken can also significantly " +
                                "affect the final result. Lower angles can make the car look more powerful and robust, " +
                                "while higher angles can emphasize the elegance of the lines.",
                        images = listOf("Photography zaslon/Profesionalno_fotografiranje_vozila_15.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_12.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_04.jpg", "Photography zaslon/Profesionalno_fotografiranje_vozila_29.jpg")
                    )
                }
            }
        }
    }
}

@Composable
fun Section(title: String, text: String, images: List<String> = emptyList()) { // opis sekcije, koje je velicine text, naslov, te kako slike poredati
    val context = LocalContext.current

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (images.isNotEmpty()) {
            for (i in images.indices step 2) {
                Row {
                    images.getOrNull(i)?.let { imagePath ->
                        val imageBitmap = loadImageBitmapFromAssets(context, imagePath)
                        imageBitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                            )
                        }
                    }
                    images.getOrNull(i + 1)?.let { imagePath ->
                        val imageBitmap = loadImageBitmapFromAssets(context, imagePath)
                        imageBitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)

                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun loadImageBitmapFromAssets(context: Context, assetPath: String): Bitmap? {
    return try {
        val inputStream = context.assets.open(assetPath)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

