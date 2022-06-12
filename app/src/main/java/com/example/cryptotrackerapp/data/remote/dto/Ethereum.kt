package com.example.cryptotrackerapp.data.remote.dto

data class Ethereum(
    val last_updated_at: Int,
    val usd: Double,
    val usd_24h_change: Double,
    val usd_24h_vol: Double,
    val usd_market_cap: Double
)