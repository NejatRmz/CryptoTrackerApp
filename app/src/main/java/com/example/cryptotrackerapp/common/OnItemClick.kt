package com.example.cryptotrackerapp.common

interface OnItemClick {
    fun onItemClicked(currency: String, price_change_percentage: String)
}