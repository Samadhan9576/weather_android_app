package com.example.Weather.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weather.presentation.navigation.Screen
import com.example.weather.presentation.navigation.Screen.Companion.getStartDestination
import com.example.weather.presentation.sreen.Demo
import com.example.weather.presentation.sreen.WelcomeScreen
import com.example.weather.viewModel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun SetupNavGraph(navController: NavHostController, weatherViewModel: WeatherViewModel) {

    NavHost(
        navController = navController,
        startDestination =   getStartDestination()

    ) {
        composable(route = Screen.WelcomeView.route) {
            WelcomeScreen(navController)
        }

        composable(route = Screen.Demo.route) {
            Demo(navController,weatherViewModel)
        }
    }
}
