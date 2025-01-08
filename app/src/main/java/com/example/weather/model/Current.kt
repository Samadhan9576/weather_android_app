package com.example.weather.model

data class Current(
    val cloud: Int,
    val condition: Condition,
    val dewpoint_c: String,
    val dewpoint_f: String,
    val feelslike_c: String,
    val feelslike_f: String,
    val gust_kph: String,
    val gust_mph: String,
    val heatindex_c: String,
    val heatindex_f: String,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: String,
    val precip_mm: String,
    val pressure_in: String,
    val pressure_mb: String,
    val temp_c: String,
    val temp_f: String,
    val uv: String,
    val vis_km: String,
    val vis_miles: String,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: String,
    val wind_mph: String,
    val windchill_c: String,
    val windchill_f: String
)