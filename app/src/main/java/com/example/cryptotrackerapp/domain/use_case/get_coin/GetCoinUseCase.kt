package com.example.cryptotrackerapp.domain.use_case.get_coin

import com.example.cryptotrackerapp.common.Resource
import com.example.cryptotrackerapp.data.remote.dto.CoinDto
import com.example.cryptotrackerapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(currency: String, coinId: String,order: String, perPage: Int, page: Int, sparkline: Boolean, priceChangePercentage: String): Flow<Resource<List<CoinDto>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCurrencyRate(currency, coinId, order, perPage, page, sparkline, priceChangePercentage)
            emit(Resource.Success(coins))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}