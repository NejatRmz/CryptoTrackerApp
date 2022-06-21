package com.example.cryptotrackerapp.common.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptotrackerapp.domain.repository.CoinDBRepository
import javax.inject.Inject

class AlertWorkerFactory @Inject constructor(private val repository: CoinDBRepository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = AlertWorker(appContext, workerParameters, repository)
}