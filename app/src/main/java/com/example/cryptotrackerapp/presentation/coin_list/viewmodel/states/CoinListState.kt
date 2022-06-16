package com.example.cryptotrackerapp.presentation.coin_list.viewmodel.states

import com.example.cryptotrackerapp.data.remote.dto.CoinDto

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinDto> = emptyList(),
    val error: String = ""
)
