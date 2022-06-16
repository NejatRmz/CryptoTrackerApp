package com.example.cryptotrackerapp.presentation.coin_history.viewmodel

import androidx.lifecycle.*
import com.example.cryptotrackerapp.domain.use_case.get_coin_detail.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinHistoryViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "This Is Coin History Fragment"
    }
    val text: LiveData<String> = _text

}