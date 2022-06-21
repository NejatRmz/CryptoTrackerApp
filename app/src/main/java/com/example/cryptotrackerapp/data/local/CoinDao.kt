package com.example.cryptotrackerapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinItem(coinItem: CoinItem)

    @Delete
    suspend fun deleteCoinItem(coinItem: CoinItem)

    @Query("SELECT * FROM coin_items")
    fun observeAllCoinItems(): LiveData<List<CoinItem>>

    @Query("SELECT * FROM coin_items")
    suspend fun getAllCoins(): List<CoinItem>

    @Query("SELECT * FROM coin_items where  name LIKE :coinName order by id DESC LIMIT 1")
    suspend fun getLatestCoinByName(coinName: String): CoinItem


}