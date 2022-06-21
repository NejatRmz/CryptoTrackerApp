package com.example.cryptotrackerapp.common

object Constants {
    const val BASE_URL = "https://api.coingecko.com/"

    @JvmField
    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    @JvmField
    val NOTIFICATION_TITLE: CharSequence = "Crypto Tracker App"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    const val TAG_OUTPUT = "OUTPUT"

    const val DELAY_TIME_MILLIS: Long = 3000

    const val MAXIMUM_VALUE = "MAXIMUM_VALUE"
    const val MINIMUM_VALUE = "MINIMUM_VALUE"
    const val COIN_NAME = "COIN_NAME"
}