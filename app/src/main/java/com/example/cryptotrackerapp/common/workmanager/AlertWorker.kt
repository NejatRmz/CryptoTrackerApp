package com.example.cryptotrackerapp.common.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.cryptotrackerapp.common.Constants.COIN_NAME
import com.example.cryptotrackerapp.common.Constants.KEY_IMAGE_URI
import com.example.cryptotrackerapp.common.Constants.MAXIMUM_VALUE
import com.example.cryptotrackerapp.common.Constants.MINIMUM_VALUE
import com.example.cryptotrackerapp.data.local.AlertItem
import com.example.cryptotrackerapp.data.local.CoinItem
import com.example.cryptotrackerapp.data.remote.CoinGeckoApi
import com.example.cryptotrackerapp.data.remote.dto.SimpleCoinsList
import com.example.cryptotrackerapp.domain.repository.CoinDBRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


private const val TAG = "CoinDetailWorker"

@HiltWorker
class AlertWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val coinDBRepository: CoinDBRepository
) : CoroutineWorker(context, workerParams) {

    private lateinit var response: SimpleCoinsList
    private lateinit var coins: List<CoinItem>
    private lateinit var bitcoin: AlertItem
    private lateinit var ethereum: AlertItem
    private lateinit var ripple: AlertItem

    override suspend fun doWork(): Result {
        val appContext = applicationContext

        val coinName = inputData.getString(COIN_NAME)
        val maximumValue = inputData.getString(MAXIMUM_VALUE)
        val minimumValue = inputData.getString(MINIMUM_VALUE)

        val alertItem = AlertItem(0, coinName, maximumValue?.toDouble(), minimumValue?.toDouble())
        coinDBRepository.insertAlertItem(alertItem)

        bitcoin = coinDBRepository.getLatestAlertByName("bitcoin")
        ethereum = coinDBRepository.getLatestAlertByName("ethereum")
        ripple = coinDBRepository.getLatestAlertByName("xrp")

//        Log.e(TAG, "Bitcoin retrieved from DB: ID: ${bitcoin.id} Name: ${bitcoin.name} MAX: ${bitcoin.maximum} MIN: ${bitcoin.minimum}")
//        Log.e(TAG, "Ethereum retrieved from DB: ID: ${ethereum.id} Name: ${ethereum.name} MAX: ${ethereum.maximum} MIN: ${ethereum.minimum}")
//        Log.e(TAG, "Ripple retrieved from DB: ID: ${ripple.id} Name: ${ripple.name} MAX: ${ripple.maximum} MIN: ${ripple.minimum}")

        response = CoinGeckoApi.instance.getSimpleCoinsListPrice("bitcoin,ethereum,ripple", "usd")

        Log.e(TAG, "Response: $response")
        return try {

            val result = response.bitcoin.usd.toString()
            val btc = response.bitcoin.usd.toString()
            val eth = response.ethereum.usd.toString()
            val xrp = response.ripple.usd.toString()


//            Log.e(TAG, "Bitcoin current price: $btc")
//            Log.e(TAG, "Ethereum current price: $eth")
//            Log.e(TAG, "Ripple current price: $xrp")

            if (response.bitcoin.usd.toDouble() > bitcoin.maximum!!.toDouble()) {
                makeStatusNotification("${bitcoin.name} Yukseldi. Fiyat $btc", appContext)
                Log.i(TAG, "Area 1")
            }
            if (response.bitcoin.usd.toDouble() < bitcoin.minimum!!.toDouble()) {
                makeStatusNotification("${bitcoin.name} Dustu. Fiyat $btc", appContext)
                Log.i(TAG, "Area 2")
            }

            if (response.ethereum.usd.toDouble() > ethereum.maximum!!.toDouble()) {
                makeStatusNotification("${ethereum.name} Yukseldi. Fiyat $eth", appContext)
                Log.i(TAG, "Area 3")
            }
            if (response.ethereum.usd.toDouble() < ethereum.minimum!!.toDouble()) {
                makeStatusNotification("${ethereum.name} Dustu. Fiyat $eth", appContext)
                Log.i(TAG, "Area 4")
            }

            if (response.ripple.usd.toDouble() > ripple.maximum!!.toDouble()) {
                makeStatusNotification("${ripple.name} Yukseldi. Fiyat $xrp", appContext)
                Log.i(TAG, "Area 5")
            }
            if (response.ripple.usd.toDouble() < ripple.minimum!!.toDouble()) {
                makeStatusNotification("${ripple.name} Dustu. Fiyat $xrp", appContext)
                Log.i(TAG, "Area 6")
            }

            val outputData = createOutputData("Output Data ${result} with function")

            Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur ${throwable.printStackTrace()}")
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
