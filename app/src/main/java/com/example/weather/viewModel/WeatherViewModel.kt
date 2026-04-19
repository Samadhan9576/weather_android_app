package com.example.weather.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Resource
import com.example.weather.model.WeatherResponse
import com.example.weather.model.WeatherState
import com.example.weather.useCases.CityUseCase
import com.example.weather.useCases.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val cityUseCase: CityUseCase
) : ViewModel() {

    val city_Temp = mutableStateOf("")
    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val _weatherEvent = MutableStateFlow(WeatherState())
    val weatherEvent: StateFlow<WeatherState> = _weatherEvent

    fun getCurrentData(
        latitude: Double, apiKey: String, longitude: Double
    ) {
        viewModelScope.launch {
            weatherUseCase(
                latitude, apiKey, longitude
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _weatherState.value = result.data
                        Log.e("TAG", "getCurrentData: ${result.data}")
                        city_Temp.value = result.data?.name.toString()
                        _weatherEvent.value = _weatherEvent.value.copy(isSuccess = true)
                    }

                    is Resource.Error -> {
                        _weatherEvent.value = _weatherEvent.value.copy(isError = true)
                    }

                    is Resource.Loading -> {
                        _weatherEvent.value = _weatherEvent.value.copy(isLoading = true)
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    fun getCityData(
        apiKey: String, city: String
    ) {
        viewModelScope.launch {
            cityUseCase(
                apiKey, city
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _weatherState.value = result.data
                        Log.e("TAG", "getCurrentData: ${result.data}")
                        city_Temp.value = result.data?.name.toString()
                        _weatherEvent.value = _weatherEvent.value.copy(isSuccess = true)
                    }

                    is Resource.Error -> {
                        _weatherEvent.value = _weatherEvent.value.copy(isError = true)
                    }

                    is Resource.Loading -> {
                        _weatherEvent.value = _weatherEvent.value.copy(isLoading = true)
                    }
                }

            }.launchIn(viewModelScope)
        }
    }
}