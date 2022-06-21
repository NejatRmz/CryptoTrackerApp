package com.example.cryptotrackerapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_items")
data class AlertItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "maximum") var maximum: Double?,
    @ColumnInfo(name = "minimum") var minimum: Double?
)
