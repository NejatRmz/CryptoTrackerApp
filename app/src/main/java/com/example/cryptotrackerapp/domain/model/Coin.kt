package com.example.cryptotrackerapp.domain.model

import com.example.cryptotrackerapp.data.remote.dto.SparklineIn7d

data class Coin(
    var id: String,
    var name: String,
    var image: String,
    var symbol: String,
    var current_price: Double,
    var sparkline: SparklineIn7d,
    var price_change_percentage_24h: Double
)
