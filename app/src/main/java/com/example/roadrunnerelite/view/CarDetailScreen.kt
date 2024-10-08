package com.example.roadrunnerelite.view
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.roadrunnerelite.R
import com.example.roadrunnerelite.model.Car
import com.example.roadrunnerelite.viewmodel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import kotlin.coroutines.CoroutineContext
import coil.compose.rememberImagePainter


@Composable
fun CarDetailScreen(navController: NavController, carId: String, screen: String, viewModel: MyViewModel) {
    var car: Car? by remember { mutableStateOf(null) }
    var reviewerName by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    LaunchedEffect(carId) {
        viewModel.fetchCarDetails(screen, carId) { result -> // poziva se fukcija fetchCarDetails koja vraća detaljne podatke o autu ovisno o ekranu i id auta
            car = result
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = car?.name ?: "Car Details",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 26.sp
                    )
                },
                backgroundColor = Color.Gray,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        car?.let {
            val context = LocalContext.current
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val imagePainter = rememberImagePainter(data = it.imageUrl)
                    Image(
                        painter = imagePainter,
                        contentDescription = "Car Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        contentScale = ContentScale.Crop
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "Details",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth() // Use fillMaxWidth to align text within the Column
                    )
                }
                item {
                    Text(
                        text = it.text,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = "More photos on vehicle:",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth() // Use fillMaxWidth to align text within the Column
                    )
                }
                item {
                    Text(
                        text = it.link,
                        fontSize = 20.sp,
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable {
                                val uri = Uri.parse(it.link)
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            }
                            .fillMaxWidth() // Use fillMaxWidth to align text within the Column
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "Leave your review",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth() // Use fillMaxWidth to align text within the Column
                    )
                }
                item {
                    OutlinedTextField(
                        value = reviewerName,
                        onValueChange = { reviewerName = it },
                        label = { Text("Your Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        label = { Text("Your Review") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Button(
                        onClick = { // na klik buttona se poziva funckija ako su sva polja popunjena
                            if (reviewerName.isNotBlank() && reviewText.isNotBlank()) {
                                viewModel.submitReview(screen, carId, reviewerName, reviewText)
                                reviewerName = ""
                                reviewText = ""
                                Toast.makeText(context, "Review submitted", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth() // Use fillMaxWidth to align button within the Column
                    ) {
                        Text("Submit Review")
                    }
                }
            }
        }
    }
}




