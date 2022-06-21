package com.example.cryptotrackerapp.common.workmanager

interface OnItemClick {
    fun onItemClicked(currency: String, price_change_percentage: String)
}