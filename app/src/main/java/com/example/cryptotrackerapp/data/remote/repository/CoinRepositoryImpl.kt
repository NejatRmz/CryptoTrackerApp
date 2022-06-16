package com.example.cryptotrackerapp.data.remote.repository

import com.example.cryptotrackerapp.data.remote.CoinGeckoApi
import com.example.cryptotrackerapp.data.remote.dto.CoinDto
import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList
import com.example.cryptotrackerapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinGeckoApi: CoinGeckoApi
) : CoinRepository {

    override suspend fun getCurrencyRate(
        currency: String,
        coinId: String,
        order: String,
        perPage: Int,
        page: Int,
        sparkline: Boolean,
        priceChangePercentage: String
    ): List<CoinDto> {
        return coinGeckoApi.getCurrencyRate(
            currency,
            coinId,
            order,
            perPage,
            page,
            sparkline,
            priceChangePercentage
        )
    }


    override suspend fun getSimpleCoinsListPrice(): SimpleCoinsList {
        return coinGeckoApi.getSimpleCoinsListPrice(
            coinIds = "bitcoin,ethereum,ripple",
            currency = "usd"
        )
    }
}