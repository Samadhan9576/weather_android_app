package com.example.weather.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Resource
import com.example.weather.useCases.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    fun getCity(
        apiKet:String,
        city: String
    ) {
        viewModelScope.launch {
            weatherUseCase(
                apiKet,city
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {

                    }

                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                    }
                }

            }.launchIn(viewModelScope)
        }
    }
}