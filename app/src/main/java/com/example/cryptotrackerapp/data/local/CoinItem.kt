package com.example.cryptotrackerapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_items")
data class CoinItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "price") var price: Double?
)
