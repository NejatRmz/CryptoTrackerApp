package com.example.cryptotrackerapp.domain.use_case

import com.example.cryptotrackerapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
){
}