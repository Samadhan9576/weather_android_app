package com.example.weather.data.repository

import com.example.weather.model.ResponseModel

interface WeatherRepository {
    suspend fun getWeather(apiKey : String, city : String) : ResponseModel
}