package com.example.weather.presentation.sreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weather.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(navController: NavController) {
    LunchScreenContent(navController)
}

@SuppressLint("RememberReturnType", "CoroutineCreationDuringComposition")
@Composable
fun LunchScreenContent(navController: NavController) {

        Box(modifier = Modifier) {


            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                ) {

                }

                Spacer(modifier = Modifier.size(152.dp))


            }

        }
    val scope = rememberCoroutineScope()
    scope.launch {
        delay(3000L)
        navController.navigate(Screen.Demo.route)
    }



}
