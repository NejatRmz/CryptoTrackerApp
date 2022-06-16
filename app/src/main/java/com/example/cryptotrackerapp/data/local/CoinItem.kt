package com.example.cryptotrackerapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_items")
data class CoinItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    var price: Double
)
