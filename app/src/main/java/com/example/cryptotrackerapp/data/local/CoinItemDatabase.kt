package com.example.cryptotrackerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CoinItem::class],
    version = 1
)
abstract class CoinItemDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}