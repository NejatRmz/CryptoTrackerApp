package com.example.cryptotrackerapp.common.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.cryptotrackerapp.data.remote.dto.CoinDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "my_preference"

class DataStoreRepository(context: Context) {

    private object PreferenceKeys {
        val max_value = preferencesKey<String>("max_value")
        val min_value = preferencesKey<String>("min_value")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveToDataStore(max_value: String, min_value: String){
        dataStore.edit { preference ->
            preference[PreferenceKeys.max_value] = max_value
            preference[PreferenceKeys.min_value] = min_value
        }
    }

    val arr: ArrayList<String> = arrayListOf()

    val readFromDataStore: Flow<ArrayList<String>> = dataStore.data
        .map { preference ->
            arr.clear()
            arr.add(preference[PreferenceKeys.max_value].toString())
            arr.add(preference[PreferenceKeys.min_value].toString())
            val models: ArrayList<String> = arr
            models
        }

}