package com.example.cryptotrackerapp.domain.use_case.get_coin_detail

import com.example.cryptotrackerapp.common.Resource
import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList
import com.example.cryptotrackerapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<SimpleCoinsList>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getSimpleCoinsListPrice()
            emit(Resource.Success(coin))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}