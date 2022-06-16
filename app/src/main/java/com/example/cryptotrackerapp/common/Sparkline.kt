package com.example.cryptotrackerapp.common

enum class Sparkline(var sparkline: String) {
    HOUR("1h"),
    DAY("24h"),
    WEEK("7d"),
    TWO_WEEK("14d"),
    MONTH("30d"),
    HALF_YEAR("200d"),
    YEAR("1y")
}