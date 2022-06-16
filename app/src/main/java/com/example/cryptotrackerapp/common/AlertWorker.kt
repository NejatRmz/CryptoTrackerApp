package com.example.cryptotrackerapp.common

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.asLiveData
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.cryptotrackerapp.R
import com.example.cryptotrackerapp.common.datastore.DataStoreRepository
import com.example.cryptotrackerapp.data.remote.CoinGeckoApi
import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList
import kotlinx.coroutines.delay
import kotlin.random.Random

@Suppress("BlockingMethodInNonBlockingContext")
class AlertWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private lateinit var response: SimpleCoinsList

    private var btcMax = 0.0
    private var btcMin = 0.0

    private var ethMax = 0.0
    private var ethMin = 0.0

    private var rippleMax = 0.0
    private var rippleMin = 0.0

    override suspend fun doWork(): Result {

        response = CoinGeckoApi.instance.getSimpleCoinsListPrice("bitcoin,ethereum,ripple", "usd")

        read()

        if (response.bitcoin.usd > btcMax) {
            startForegroundService("Bitcoin yukseldi", response.bitcoin.usd.toString())
            delay(5000L)
        }

        if (response.bitcoin.usd < btcMin) {
            startForegroundService("Bitcoin dustu", response.bitcoin.usd.toString())
            delay(5000L)
        }


        if (response.ethereum.usd > ethMax) {
            startForegroundService("Ethereum yukseldi", response.ethereum.usd.toString())
            delay(5000L)
        }

        if (response.ethereum.usd < ethMin) {
            startForegroundService("Ethereum dustu", response.ethereum.usd.toString())
            delay(5000L)
        }


        if (response.ripple.usd > rippleMax) {
            startForegroundService("Ripple yukseldi", response.ripple.usd.toString())
            delay(5000L)
        }

        if (response.ripple.usd < rippleMin) {
            startForegroundService("Ripple dustu", response.ripple.usd.toString())
            delay(5000L)
        }


        return Result.failure(
            workDataOf(WorkerKeys.ERROR_MSG to "Unknown error")
        )
    }

    private suspend fun startForegroundService(alert: String, price: String) {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "download_channel")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentText(price)
                    .setContentTitle(alert)
                    .build()
            )
        )
    }

    private suspend fun read() {
        var arr: ArrayList<String> = arrayListOf()

        val repository = DataStoreRepository(context)
        val readFromDataStore = repository.readFromDataStore.asLiveData()

    }
}