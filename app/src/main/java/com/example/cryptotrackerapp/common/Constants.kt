package com.example.cryptotrackerapp.common

object Constants {
    const val BASE_URL = "https://api.coingecko.com/"
    const val MAX_PRICE = 0
    const val MIN_PRICE = 0
    const val RESULT = "RESULT"
    const val BTC_MAX = "BTC_MAX"
    const val BTC_MIN = "BTC_MIN"

    // Name of Notification Channel for verbose notifications of background work
    @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    @JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    // The name of the image manipulation work
    const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

    // Other keys
    const val OUTPUT_PATH = "blur_filter_outputs"
    const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    const val TAG_OUTPUT = "OUTPUT"

    const val DELAY_TIME_MILLIS: Long = 3000

    const val MAXIMUM_VALUE = "MAXIMUM_VALUE"
    const val MINIMUM_VALUE = "MINIMUM_VALUE"
    const val COIN_NAME = "COIN_NAME"
}