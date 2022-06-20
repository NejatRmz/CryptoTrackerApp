package com.example.cryptotrackerapp.common

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.cryptotrackerapp.common.Constants.COIN_NAME
import com.example.cryptotrackerapp.common.Constants.KEY_IMAGE_URI
import com.example.cryptotrackerapp.common.Constants.MAXIMUM_VALUE
import com.example.cryptotrackerapp.common.Constants.MINIMUM_VALUE
import com.example.cryptotrackerapp.data.remote.CoinGeckoApi
import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList

private const val TAG = "CoinDetailWorker"

class MyWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private lateinit var response: SimpleCoinsList

    override suspend fun doWork(): Result {


        val appContext = applicationContext
        val coinName = inputData.getString(COIN_NAME)
        val maximumValue = inputData.getString(MAXIMUM_VALUE)
        val minimumValue = inputData.getString(MINIMUM_VALUE)
        Log.e(TAG, coinName.toString())
        Log.e(TAG, maximumValue.toString())
        Log.e(TAG, minimumValue.toString())

        response = CoinGeckoApi.instance.getSimpleCoinsListPrice("bitcoin,ethereum,ripple", "usd")

        return try {
            if (TextUtils.isEmpty(coinName)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            if (TextUtils.isEmpty(maximumValue)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            if (TextUtils.isEmpty(minimumValue)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val result = response.bitcoin.usd.toString()

            if (maximumValue != null) {
                if (response.bitcoin.usd.toDouble() > maximumValue.toDouble()) {
                    makeStatusNotification("Bitcoin Yukseldi. Fiyat ${result}", appContext)
                }
            }

            if (minimumValue != null) {
                if (response.bitcoin.usd.toDouble() < minimumValue.toDouble()) {
                    makeStatusNotification("Bitcoin Dustu. Fiyat ${result}", appContext)
                }
            }

            val outputData = createOutputData("Output Data ${result} with function")

            Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            throwable.printStackTrace()
            Result.failure()
        }

    }

    private fun createOutputData(data: String): Data {
        return Data.Builder()
            .putString(KEY_IMAGE_URI, data)
            .build()
    }
}
