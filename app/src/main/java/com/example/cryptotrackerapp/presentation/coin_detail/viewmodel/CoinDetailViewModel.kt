package com.example.cryptotrackerapp.presentation.coin_detail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.cryptotrackerapp.common.Constants.COIN_NAME
import com.example.cryptotrackerapp.common.Constants.MAXIMUM_VALUE
import com.example.cryptotrackerapp.common.Constants.MINIMUM_VALUE
import com.example.cryptotrackerapp.common.Constants.TAG_OUTPUT
import com.example.cryptotrackerapp.common.MyWorker
import com.example.cryptotrackerapp.common.datastore.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CoinDetailViewModel(application: Application) : AndroidViewModel(application) {


    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>>


    private val repository = DataStoreRepository(application)

    val readFromDataStore = repository.readFromDataStore.asLiveData()

    fun saveToDataStore(maxValue: String, minValue: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToDataStore(maxValue, minValue)
    }

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    private fun createInputDataForUri(maximumValue: String, minimumValue: String, coinName: String): Data {
        val builder = Data.Builder()
        builder.putString(COIN_NAME, coinName.toString())
        builder.putString(MAXIMUM_VALUE, maximumValue.toString())
        builder.putString(MINIMUM_VALUE, minimumValue.toString())
        return builder.build()
    }

    internal fun applyBlur(maximumValue: String, minimumValue: String, coinName: String) {
        val blurRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setInputData(createInputDataForUri(maximumValue, minimumValue, coinName))
            .addTag(TAG_OUTPUT)
            .build()
        workManager.enqueue(blurRequest)
    }

}