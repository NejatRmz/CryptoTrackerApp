package com.example.cryptotrackerapp.presentation.coin_detail.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.cryptotrackerapp.common.workmanager.AlertWorker
import com.example.cryptotrackerapp.common.Constants.COIN_NAME
import com.example.cryptotrackerapp.common.Constants.MAXIMUM_VALUE
import com.example.cryptotrackerapp.common.Constants.MINIMUM_VALUE
import com.example.cryptotrackerapp.common.Constants.TAG_OUTPUT
import com.example.cryptotrackerapp.data.local.CoinItem
import com.example.cryptotrackerapp.domain.repository.CoinDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    application: Application,
    private val repository: CoinDBRepository
) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
        getAllCoinItems()
    }

    private fun createInputDataForUri(
        maximumValue: String,
        minimumValue: String,
        coinName: String
    ): Data {
        val builder = Data.Builder()
        builder.putString(COIN_NAME, coinName.toString())
        builder.putString(MAXIMUM_VALUE, maximumValue.toString())
        builder.putString(MINIMUM_VALUE, minimumValue.toString())
        return builder.build()
    }

    internal fun applyBlur(maximumValue: String, minimumValue: String, coinName: String) {
        val blurRequest = PeriodicWorkRequestBuilder<AlertWorker>(15, TimeUnit.MINUTES)
            .setInputData(createInputDataForUri(maximumValue, minimumValue, coinName))
            .addTag(TAG_OUTPUT)
            .build()
        workManager.enqueue(blurRequest)
    }

    fun insertCoinItem(coinItem: CoinItem) = viewModelScope.launch {
        repository.insertCoinItem(coinItem)
    }

    fun getAllCoinItems(): LiveData<List<CoinItem>> {
        Log.e("DEBUG", "View model getallnotes")
        return repository.observeAllCoinItems()
    }

    fun getAllCoins() = viewModelScope.launch {
        repository.getAllCoins()
    }

    fun getLatestCoinByName(name:String) = viewModelScope.launch {
        repository.getLatestCoinByName(name)
    }

}