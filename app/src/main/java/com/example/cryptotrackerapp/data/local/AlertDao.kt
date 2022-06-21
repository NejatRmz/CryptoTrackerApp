package com.example.cryptotrackerapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlertDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlertItem(alertItem: AlertItem)

    @Delete
    suspend fun deleteAlertItem(alertItem: AlertItem)

    @Query("SELECT * FROM alert_items")
    fun observeAllAlertItems(): LiveData<List<AlertItem>>

    @Query("SELECT * FROM alert_items")
    suspend fun getAllAlerts(): List<AlertItem>

    @Query("SELECT * FROM alert_items where  name LIKE :coinName order by id DESC LIMIT 1")
    suspend fun getLatestAlertByName(coinName: String): AlertItem

}