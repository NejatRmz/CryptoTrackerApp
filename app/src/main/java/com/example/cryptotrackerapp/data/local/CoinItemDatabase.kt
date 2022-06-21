package com.example.cryptotrackerapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CoinItem::class, AlertItem::class],
    version = 1,
    exportSchema = false
)
abstract class CoinItemDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
    abstract fun alertDao(): AlertDao
}