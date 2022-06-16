package com.example.cryptotrackerapp.presentation.coin_list.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotrackerapp.common.Constants
import com.example.cryptotrackerapp.common.Resource
import com.example.cryptotrackerapp.domain.use_case.get_coin.GetCoinUseCase
import com.example.cryptotrackerapp.presentation.coin_list.viewmodel.states.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _state = MutableStateFlow<CoinListState>(CoinListState())
    val state: StateFlow<CoinListState> = _state


    init {
        getCurrencyRate("usd", "bitcoin,ethereum,ripple", "market_cap_desc", 100, 1, true, "7d")
    }

    fun getCurrencyRate(currency: String, coinId: String,order: String, perPage: Int, page: Int, sparkline: Boolean, priceChangePercentage: String){
        getCoinUseCase(currency, coinId, order, perPage, page, sparkline, priceChangePercentage).onEach { result->
            when(result){
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = CoinListState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value= CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        private const val TAG = "CoinList"
    }

}