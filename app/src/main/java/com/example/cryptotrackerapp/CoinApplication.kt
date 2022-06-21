package com.example.cryptotrackerapp

import android.app.Application
import androidx.work.Configuration
import com.example.cryptotrackerapp.common.workmanager.AlertWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CoinApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var alertWorkerFactory: AlertWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .setWorkerFactory(alertWorkerFactory)
        .build()
}
