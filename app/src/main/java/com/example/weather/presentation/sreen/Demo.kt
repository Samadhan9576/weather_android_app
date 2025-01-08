package com.example.weather.presentation.sreen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.weather.viewModel.WeatherViewModel

@Composable
fun Demo(navController: NavController, viewModel:WeatherViewModel) {

    Text(
        text = "Hello ",
        modifier = Modifier.clickable{
            viewModel.getCity("36fc86da7a7143d7a2c105754242905","London")
        }
    )
}
