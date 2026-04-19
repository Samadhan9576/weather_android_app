package com.example.weather.data.repositoryImp

import com.example.weather.data.remote.WeatherApi
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.model.WeatherResponse


class WeatherRepositoryImp  constructor(
    private val  api : WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(apiKey : String, city : String): WeatherResponse {
        return api.getWeather(city,apiKey)
    }

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse {
        return api.getWeather(latitude, longitude, apiKey)
    }
}