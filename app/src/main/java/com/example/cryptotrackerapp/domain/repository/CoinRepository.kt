package com.example.cryptotrackerapp.domain.repository
import com.example.cryptotrackerapp.data.remote.dto.CoinDto
import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList

interface CoinRepository {

    suspend fun getCurrencyRate(currency: String, coinId: String, order: String, perPage: Int, page: Int, sparkline: Boolean, priceChangePercentage: String): List<CoinDto>
    suspend fun getSimpleCoinsListPrice(): SimpleCoinsList


}