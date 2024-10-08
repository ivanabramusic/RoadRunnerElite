package com.example.roadrunnerelite

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roadrunnerelite.ui.theme.RoadRunnerEliteTheme
import com.example.roadrunnerelite.view.CarDetailScreen
import com.example.roadrunnerelite.view.CarSpottingScreen
import com.example.roadrunnerelite.view.ContactScreen
import com.example.roadrunnerelite.view.HomeScreen
import com.example.roadrunnerelite.view.PhotographyScreen
import com.example.roadrunnerelite.view.ProfessionalPhotographyScreen
import com.example.roadrunnerelite.viewmodel.MyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application
        setContent {
            val navController = rememberNavController() // Sluzi za navigaciju kroz screenove
            NavHost(navController = navController, startDestination = "homescreen"){
                composable("homescreen"){
                    val viewModel: MyViewModel = viewModel()
                    HomeScreen(navController, viewModel)
                }
                composable("contactscreen"){
                    ContactScreen(navController)
                }
                composable("photographyscreen"){
                    PhotographyScreen(navController)
                }
                composable("carspottingscreen"){
                    val viewModel: MyViewModel = viewModel()
                    CarSpottingScreen(navController, viewModel)
                }
                composable("professionalphotographyscreen"){
                    val viewModel: MyViewModel = viewModel()
                    ProfessionalPhotographyScreen(navController, viewModel)
                }
                composable("cardetailscreen/{carId}/{screen}", // kroz navigaciju predajes carId kako bi se znalo na koji auto si kliknuo, i screen da se zna na kojem si ekranu bio kad si kliknuo
                    arguments = listOf(navArgument("carId") { type = NavType.StringType },
                        navArgument("screen"){type = NavType.StringType})
                ) { backStackEntry ->
                    val carId = backStackEntry.arguments?.getString("carId") ?: ""
                    val screen = backStackEntry.arguments?.getString("screen") ?:""
                    val viewModel: MyViewModel = viewModel()
                    CarDetailScreen(navController = navController, carId = carId, screen = screen, viewModel = viewModel)
                }
            }

        }
    }
}

