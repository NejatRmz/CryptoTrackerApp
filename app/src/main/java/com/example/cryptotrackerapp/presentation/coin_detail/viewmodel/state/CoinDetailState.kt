package com.example.cryptotrackerapp.presentation.coin_detail.viewmodel.state

import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coins: SimpleCoinsList? = null,
    val error: String = ""
)
