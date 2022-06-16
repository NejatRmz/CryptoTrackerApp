package com.example.cryptotrackerapp.data.remote

import com.example.cryptotrackerapp.common.Constants
import com.example.cryptotrackerapp.data.remote.dto.CoinDto
import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("/api/v3/coins/markets")
    suspend fun getCurrencyRate(
        @Query("vs_currency") currency: String,
        @Query("ids") coinId: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sparkline") sparkline: Boolean,
        @Query("price_change_percentage") priceChangePercentage: String
    ): List<CoinDto>

    @GET("/api/v3/simple/price")
    suspend fun getSimpleCoinsListPrice(
        @Query("ids") coinIds: String,
        @Query("vs_currencies") currency: String
    ): SimpleCoinsList


    @GET("/wp-content/uploads/2022/02/220849-scaled.jpg")
    suspend fun downloadImage(): retrofit2.Response<ResponseBody>

    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinGeckoApi::class.java)
        }
    }
}