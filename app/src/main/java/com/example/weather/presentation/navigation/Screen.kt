package com.example.weather.presentation.navigation

sealed class Screen(val route:String){
    object WelcomeView : Screen("welcome_screen_for_splash_screen")
    object Demo: Screen("demo")
    companion object {
        fun getStartDestination() = WelcomeView.route
    }
}