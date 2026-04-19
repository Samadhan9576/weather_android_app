package com.example.weather.presentation.sreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.Weather.R
import com.example.weather.model.WeatherResponse
import com.example.weather.model.WeatherState
import com.example.weather.viewModel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeatherScreen(navController: NavController, viewModel: WeatherViewModel) {
    val context = LocalContext.current
    val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var latitude = remember { mutableStateOf<Double>(0.0) }
    var longitude = remember { mutableStateOf<Double>(0.0) }
    var permissionGranted = remember { mutableStateOf(false) }
    val state = viewModel.weatherState.collectAsState()
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted.value = isGranted
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            permissionGranted.value = true
        }
    }

    if (permissionGranted.value) {
        LaunchedEffect(Unit) {
            getLastLocation(fusedLocationProviderClient) { location ->
                if (location != null) {
                    latitude.value = location.latitude
                    longitude.value = location.longitude
                }

            }
            viewModel.getCurrentData(latitude.value,"06a17ed8fb89bfa6918998d78c66245e",longitude.value)
        }
    }
    WeatherMainContent(state,viewModel,longitude,latitude)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherMainContent(
    state: State<WeatherResponse?>,
    viewModel: WeatherViewModel,
    longitude: MutableState<Double>,
    latitude: MutableState<Double>
) {
    val cityName = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val isLoading = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        viewModel.weatherEvent.collectLatest { event ->
            Log.e("TAG", "WeatherMainContent: ${event.isLoading}", )
            when{
                event.isLoading ->{
                    isLoading.value = true
                }
                event.isSuccess->{
                    isLoading.value = false
                }
                event.isError->{
                    isLoading.value = false
                }
            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00FFAB),
                        Color(0xFF0084FF)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = cityName.value,
                    onValueChange = {
                        cityName.value = it
                    },
                    placeholder = { Text("Enter city name") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
                Icon(
                    imageVector = Icons.Default.Search, // Replace with your search icon
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            viewModel.getCityData(
                                "06a17ed8fb89bfa6918998d78c66245e",
                                cityName.value
                            )
                            focusManager.clearFocus()
                        }
                )
            }

            // Weather Info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        viewModel.getCurrentData(latitude.value,"06a17ed8fb89bfa6918998d78c66245e",longitude.value)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBFA048)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Current Weather", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    painter = painterResource(id = R.drawable.partly_cloudy_day), // Replace with your weather icon
                    contentDescription = "Weather Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text =state.value?.main?.temp.toString(),
                )

                Text(
                    text = state.value?.name?:"",
                )
            }

            // Additional Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons_humidity),
                        contentDescription = "Humidity",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = "${state.value?.main?.humidity.toString()} Humidity",
                        color = Color.White,
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.windy_weather), // Replace with your wind icon
                        contentDescription = "Wind Speed",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "${state.value?.wind?.speed.toString()} km/h Wind Speed",
                        color = Color.White,
                    )
                }
            }
        }
        if (isLoading.value)
            CircularProgressIndicator()
    }
}

@SuppressLint("MissingPermission")
private fun getLastLocation(
    fusedLocationProviderClient: FusedLocationProviderClient,
    onLocationRetrieved: (Location?) -> Unit
) {
    fusedLocationProviderClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            onLocationRetrieved(location)
        }
        .addOnFailureListener {
            onLocationRetrieved(null)
        }
}


