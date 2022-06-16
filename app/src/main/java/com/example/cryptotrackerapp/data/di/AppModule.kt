package com.example.cryptotrackerapp.data.di

import com.example.cryptotrackerapp.common.Constants
import com.example.cryptotrackerapp.data.remote.CoinGeckoApi
import com.example.cryptotrackerapp.data.remote.repository.CoinRepositoryImpl
import com.example.cryptotrackerapp.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGeckoApi(): CoinGeckoApi{

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
    fun provideCoinRepository(api: CoinGeckoApi):CoinRepository{
        return CoinRepositoryImpl(api)
    }
}