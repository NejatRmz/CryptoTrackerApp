package com.example.cryptotrackerapp.domain.use_case

import com.example.cryptotrackerapp.common.Resource
import com.example.cryptotrackerapp.data.remote.dto.CoinDto
import com.example.cryptotrackerapp.domain.model.Coin
import com.example.cryptotrackerapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.Flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoin("bitcoin","usd")
            emit(Resource.Success(coins))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage?: "Error occurred"))
        }catch ( e: IOException){
            emit(Resource.Error(e.localizedMessage?: "Error occurred"))
        }
    }
}