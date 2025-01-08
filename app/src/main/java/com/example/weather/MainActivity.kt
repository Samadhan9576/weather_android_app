package com.example.weather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.Weather.presentation.navigation.SetupNavGraph
import com.example.weather.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SetupNavGraph(navController = navController,weatherViewModel)
        }
    }
}


/*
@Composable
fun Demo(weatherViewModel : WeatherViewModel) {
    Text(text = "fghjkl", Modifier.clickable {
        weatherViewModel.getCity("36fc86da7a7143d7a2c105754242905","London")
    }
    )
}*/
