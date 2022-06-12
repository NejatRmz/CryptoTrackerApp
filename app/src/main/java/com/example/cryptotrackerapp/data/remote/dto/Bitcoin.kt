package com.example.cryptotrackerapp.data.remote.dto

data class Bitcoin(
    val last_updated_at: Int,
    val usd: Int,
    val usd_24h_change: Double,
    val usd_24h_vol: Double,
    val usd_market_cap: Double
)