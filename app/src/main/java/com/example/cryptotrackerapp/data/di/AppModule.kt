package com.example.cryptotrackerapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.cryptotrackerapp.common.Constants
import com.example.cryptotrackerapp.data.local.AlertDao
import com.example.cryptotrackerapp.data.local.CoinDao
import com.example.cryptotrackerapp.data.local.CoinItemDatabase
import com.example.cryptotrackerapp.data.remote.CoinGeckoApi
import com.example.cryptotrackerapp.data.remote.repository.CoinDBRepositoryImpl
import com.example.cryptotrackerapp.data.remote.repository.CoinRepositoryImpl
import com.example.cryptotrackerapp.domain.repository.CoinDBRepository
import com.example.cryptotrackerapp.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGeckoApi(): CoinGeckoApi {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request()
                    .newBuilder()
                    .build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(CoinGeckoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinGeckoApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: CoinDao, alertDao: AlertDao) =
        CoinDBRepositoryImpl(dao, alertDao) as CoinDBRepository


    @Singleton
    @Provides
    fun injectCoinRoomDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CoinItemDatabase::class.java, "CoinDB")
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun injectCoinDao(database: CoinItemDatabase) = database.coinDao()

    @Singleton
    @Provides
    fun injectAlertDao(database: CoinItemDatabase) = database.alertDao()
}