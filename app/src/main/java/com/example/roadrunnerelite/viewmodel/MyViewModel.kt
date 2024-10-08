package com.example.roadrunnerelite.viewmodel
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.roadrunnerelite.model.Car
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.URL


class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val firestore = FirebaseFirestore.getInstance() // inicijalizacija firestore
    private val _carList = MutableStateFlow<List<Car>>(emptyList())
    val carList: StateFlow<List<Car>> = _carList // sluzi kako bi se data koja se uzme u ovim funkcijama ovdje mogla proslijediti na drugi ekran
    private val _carDetails = MutableStateFlow<Car?>(null)
    val carDetails: StateFlow<Car?> = _carDetails

    var reviewText by mutableStateOf("") // sluzi za spremanje reviewa

    fun fetchCarData(screen: Int) { // funkcija koja na osnovu poslanog screena kao broj uzima aute te sve njihove podatke i sprema u car list, a taj car list se onda moze koristiti na drugom zaslonu
        viewModelScope.launch {
            try {
                // Clear the current list
                _carList.value = emptyList()
                if(screen == 1) {
                    // Get all car documents
                    val carDocuments = firestore.collection("CarSpotting").get().await()
                    val cars = carDocuments.documents.map { document ->
                        val carDetails = document.reference.collection("Details").document("Details").get().await()
                        val name = carDetails.getString("name") ?: ""
                        val picture = carDetails.getString("image") ?: ""
                        val text = carDetails.getString("text")?: ""
                        val link = carDetails.getString("link")?: ""
                        val id = carDetails.getString("id")?: ""
                        Car(name, picture, link, text, id)
                    }
                    _carList.value = cars

                }
                else
                {
                    val carDocuments = firestore.collection("ProfessionalPhotography").get().await()
                    val cars = carDocuments.documents.map { document ->
                        val carDetails = document.reference.collection("Details").document("Details").get().await()
                        val name = carDetails.getString("name") ?: ""
                        val picture = carDetails.getString("image") ?: ""
                        val text = carDetails.getString("text")?: ""
                        val link = carDetails.getString("link")?: ""
                        val id = carDetails.getString("id")?: ""
                        Car(name, picture, link, text, id)

                    }
                    _carList.value = cars
                }
                // Fetch details for each car



            } catch (e: Exception) {
                Log.e("CarSpottingViewModel", "Error fetching car data", e)
            }


        }
    }

    fun fetchCarDetails(screen: String, carId: String, onResult: (Car?) -> Unit) { // funckija vraća detaljne podatke o autu ovisno koji je id i koji je ekran
        viewModelScope.launch {
            try {
                if(screen == "1")
                {
                    val carDocument = firestore.collection("CarSpotting").document(carId).collection("Details").document("Details").get().await()
                    val name = carDocument.getString("name") ?: ""
                    val picture = carDocument.getString("image") ?: ""
                    val text = carDocument.getString("text") ?: ""
                    val link = carDocument.getString("link") ?: ""
                    val id = carDocument.getString("id") ?: ""
                    val car = Car(name, picture, link, text, id)
                    onResult(car)
                }
                else{
                    val carDocument = firestore.collection("ProfessionalPhotography").document(carId).collection("Details").document("Details").get().await()
                    val name = carDocument.getString("name") ?: ""
                    val picture = carDocument.getString("image") ?: ""
                    val text = carDocument.getString("text") ?: ""
                    val link = carDocument.getString("link") ?: ""
                    val id = carDocument.getString("id") ?: ""
                    val car = Car(name, picture, link, text, id)
                    onResult(car)
                }

            } catch (e: Exception) {
                Log.e("MyViewModel", "Error fetching car details", e)
                onResult(null)
            }
        }
    }

    fun submitReview(screen: String, carId: String, reviewerName: String, reviewText: String) { // funckija koja sprema review i ime koje je upisano
        viewModelScope.launch {
            try {
                val reviewData = mapOf(
                    "name" to reviewerName,
                    "review" to reviewText
                )
                if(screen == "1")
                {
                    firestore.collection("CarSpotting")
                        .document(carId)
                        .collection("Reviews")
                        .document(reviewerName)
                        .set(reviewData)
                        .await()
                    Log.d("MyViewModel", "Review submitted successfully")
                }
                else{
                    firestore.collection("ProfessionalPhotography")
                        .document(carId)
                        .collection("Reviews")
                        .document(reviewerName)
                        .set(reviewData)
                        .await()
                    Log.d("MyViewModel", "Review submitted successfully")
                }

            } catch (e: Exception) {
                Log.e("MyViewModel", "Error submitting review", e)
            }
        }
    }
}

