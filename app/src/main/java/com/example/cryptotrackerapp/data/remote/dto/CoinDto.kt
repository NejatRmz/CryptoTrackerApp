package com.example.cryptotrackerapp.data.remote.dto

import android.os.DeadObjectException
import com.example.cryptotrackerapp.domain.model.Coin

data class CoinDto(
    val ath: Double,
    val ath_change_percentage: Double,
    val ath_date: String,
    val atl: Double,
    val atl_change_percentage: Double,
    val atl_date: String,
    val circulating_supply: Double,
    val current_price: Double,
    val fully_diluted_valuation: Long,
    val high_24h: Double,
    val id: String,
    val image: String,
    val last_updated: String,
    val low_24h: Double,
    val market_cap: Long,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val market_cap_rank: Int,
    val max_supply: Double,
    val name: String,
    val price_change_24h: Double,
    val price_change_percentage_1h_in_currency: Double,
    val price_change_percentage_24h: Double,
    val roi: Roi,
    val sparkline_in_7d: SparklineIn7d,
    val symbol: String,
    val total_supply: Double,
    val total_volume: Long
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        image = image,
        symbol = symbol,
        current_price = current_price,
        sparkline = sparkline_in_7d,
        price_change_percentage_24h = price_change_percentage_24h
    )
}