package com.example.cryptotrackerapp.data.remote.repository

import androidx.lifecycle.LiveData
import com.example.cryptotrackerapp.data.local.AlertDao
import com.example.cryptotrackerapp.data.local.AlertItem
import com.example.cryptotrackerapp.data.local.CoinDao
import com.example.cryptotrackerapp.data.local.CoinItem
import com.example.cryptotrackerapp.domain.repository.CoinDBRepository
import javax.inject.Inject

class CoinDBRepositoryImpl @Inject constructor(private val coinDao: CoinDao, private val alertDao: AlertDao) : CoinDBRepository {

    override suspend fun insertCoinItem(coinItem: CoinItem) {
        coinDao.insertCoinItem(coinItem)
    }

    override suspend fun deleteCoinItem(coinItem: CoinItem) {
        coinDao.deleteCoinItem(coinItem)
    }

    override fun observeAllCoinItems(): LiveData<List<CoinItem>> {
        return coinDao.observeAllCoinItems()
    }

    override suspend fun getAllCoins(): List<CoinItem> {
        return coinDao.getAllCoins()
    }

    override suspend fun getLatestCoinByName(name:String): CoinItem {
        return coinDao.getLatestCoinByName(name)
    }

    override suspend fun insertAlertItem(alertItem: AlertItem) {
        alertDao.insertAlertItem(alertItem)
    }

    override suspend fun deleteAlertItem(alertItem: AlertItem) {
        alertDao.deleteAlertItem(alertItem)
    }

    override fun observeAllAlertItems(): LiveData<List<AlertItem>> {
        return alertDao.observeAllAlertItems()
    }

    override suspend fun getAllAlerts(): List<AlertItem> {
        return alertDao.getAllAlerts()
    }

    override suspend fun getLatestAlertByName(name:String): AlertItem {
        return alertDao.getLatestAlertByName(name)
    }

}