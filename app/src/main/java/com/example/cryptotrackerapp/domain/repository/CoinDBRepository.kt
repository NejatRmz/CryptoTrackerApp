package com.example.cryptotrackerapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptotrackerapp.data.local.AlertItem
import com.example.cryptotrackerapp.data.local.CoinItem

interface CoinDBRepository {
    suspend fun insertCoinItem(coinItem: CoinItem)

    suspend fun deleteCoinItem(coinItem: CoinItem)

    fun observeAllCoinItems(): LiveData<List<CoinItem>>

    suspend fun getAllCoins(): List<CoinItem>

    suspend fun getLatestCoinByName(name:String): CoinItem

    suspend fun insertAlertItem(alertItem: AlertItem)

    suspend fun deleteAlertItem(alertItem: AlertItem)

    fun observeAllAlertItems(): LiveData<List<AlertItem>>

    suspend fun getAllAlerts(): List<AlertItem>

    suspend fun getLatestAlertByName(name:String): AlertItem
}