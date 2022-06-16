package com.example.cryptotrackerapp.presentation.coin_detail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptotrackerapp.common.datastore.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    val readFromDataStore = repository.readFromDataStore.asLiveData()

    fun saveToDataStore(maxValue: String, minValue: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToDataStore(maxValue, minValue)
    }
}